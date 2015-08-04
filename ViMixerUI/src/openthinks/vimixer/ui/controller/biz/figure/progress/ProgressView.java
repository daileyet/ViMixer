package openthinks.vimixer.ui.controller.biz.figure.progress;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import openthinks.vimixer.ui.controller.BaseController;
import openthinks.vimixer.ui.controller.MainFrameController;
import openthinks.vimixer.ui.controller.biz.figure.Figureable;
import openthinks.vimixer.ui.model.ViFile;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * The view include progress bar and label
 * @author Dailey
 *
 */
public class ProgressView extends HBox implements Figureable {
	private ProgressBar progressBar;
	private Label progressLabel;
	private AtomicBoolean initailized = new AtomicBoolean(false);
	private Lock lock = new ReentrantLock();

	public boolean isInitialized() {
		return initailized.get();
	}

	public void initial(ViFile observable, BaseController controller) {
		lock.lock();
		try {
			progressBar = new ProgressBar(observable.infoProperty().get().computeProgress());
			progressLabel = new Label(" " + observable.infoProperty().get().computeProgressPercent());
			progressLabel.setAlignment(Pos.CENTER_RIGHT);
			//			progressLabel.textProperty().bind(Bindings.convert(progressBar.progressProperty()));
			this.getChildren().add(progressBar);
			this.getChildren().add(progressLabel);

			progressBar.progressProperty().addListener((obser, oldvalue, newvalue) -> {
				Double percent = newvalue.doubleValue() * 100;
				progressLabel.setText(" " + percent.intValue() + "%");
			});

			initailized.set(true);
			this.setCache(true);
			//bind this overview pane width to the right width property in MainFrame
			this.progressBar.prefWidthProperty().bind(
					((MainFrameController) controller).getBlockPaneWidthProperty().add(-35));
		} finally {
			lock.unlock();
		}
	}

	public void updateProgress(double computeProgress) {
		this.progressBar.setProgress(computeProgress);
	}

	@Override
	public Node getView() {
		return this;
	}

	@Override
	public void destory() {
		if (this.getParent() != null) {
			((Pane) this.getParent()).getChildren().remove(this);
		}
	}

}
