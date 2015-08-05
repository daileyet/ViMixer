/**
 * Business package
 */
package openthinks.vimixer.ui.controller.biz;

import java.util.Date;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import openthinks.crypto.mix.MixBlock;
import openthinks.crypto.mix.MixTarget;
import openthinks.crypto.mix.Mixer;
import openthinks.crypto.mix.impl.DefaultMixProcesser;
import openthinks.crypto.mix.impl.DefaultMixStrategy;
import openthinks.crypto.mix.impl.FileMixer;
import openthinks.crypto.mix.impl.MixFile;
import openthinks.libs.i18n.I18n;
import openthinks.vimixer.resources.bundles.ViMixerBundles;
import openthinks.vimixer.ui.controller.BaseController;
import openthinks.vimixer.ui.controller.biz.figure.DynamicPaintType;
import openthinks.vimixer.ui.model.ViFile;
import openthinks.vimixer.ui.model.ViFileStatus;
import openthinks.vimixer.ui.model.configure.Segmentor;

/**
 * a new thread to process mixing file, when start run button clicked<BR>
 * it will invoke the business code to mixer files
 * @author minjdai
 * @since v1.0
 */
public class ProcessMixBizThread extends Thread {
	private final ObservableList<ViFile> viFiles;
	private final BaseController controller;

	public ProcessMixBizThread(final BaseController controller, final ObservableList<ViFile> filteredList) {
		this.controller = controller;
		this.viFiles = filteredList;
	}

	@Override
	public void run() {
		processBusiness();
	}

	private void processBusiness() {
		String secretKey = controller.configure().getSecretKey();
		if (secretKey == null || "".equals(secretKey.trim())) {
			Alert alert = new Alert(AlertType.ERROR);
			// alert.setHeaderText("");
			alert.setContentText(I18n.getMessage(ViMixerBundles.UI, "alert.error.secret.content"));
			alert.initOwner(controller.stage());
			alert.show();
			return;
		}
		Segmentor segmentor = controller.configure().getSegmentor();
		if (segmentor == null) {
			Alert alert = new Alert(AlertType.ERROR);
			// alert.setHeaderText("");
			alert.setContentText(I18n.getMessage(ViMixerBundles.UI, "alert.error.segmentor.content"));
			alert.initOwner(controller.stage());
			alert.show();
			return;
		}

		for (ViFile viFile : this.viFiles) {
			MixTarget mixTarget = new MixFile(viFile.getFile(), segmentor.mixSegmentor());
			viFile.infoProperty().get().setBlockLength(mixTarget.blocks().size());
			Mixer mixer = new FileMixer(mixTarget, DefaultMixStrategy.get(secretKey), new ViMixProcesser(viFile));
			try {
				mixer.mix();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				mixTarget.free();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @author minjdai
	 *
	 */
	class ViMixProcesser extends DefaultMixProcesser {

		private final ViFile viFile;

		public ViMixProcesser(ViFile viFile) {
			super();
			this.viFile = viFile;
		}

		@Override
		public void start() {
			try {
				super.start();
				this.viFile.statusProperty().set(ViFileStatus.IN_PROCESSING);
				this.viFile.infoProperty().get().setStartTime(startTime);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		@Override
		public void processed(MixBlock mixBlock) {
			// super.processed(mixBlock);
			try {
				mixBlock.markProcessed();
				viFile.infoProperty().get().setCurrentProcessedBlock(mixBlock).increase();
				controller.configure().getSegmentor().valueOf(viFile).with(controller)
						.dynamic(DynamicPaintType.PROCESSED_PARTIAL, viFile.getInfo());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		@Override
		public void completed() {
			try {
				completeTime = new Date().getTime();
				controller.configure().getSegmentor().valueOf(viFile).with(controller)
						.dynamic(DynamicPaintType.PROCESSED_ALL);
				this.viFile.statusProperty().set(ViFileStatus.COMPLETED);
				this.viFile.infoProperty().get().setEndTime(completeTime);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
