package openthinks.vimixer.ui.controller.biz.figure.progress;

import openthinks.vimixer.ui.controller.biz.figure.DynamicPaintType;
import openthinks.vimixer.ui.controller.biz.figure.FigureOverview;
import openthinks.vimixer.ui.controller.biz.figure.FigureOverviewPool;
import openthinks.vimixer.ui.model.ViFile;
import openthinks.vimixer.ui.model.ViFileInfo;
import javafx.application.Platform;

/**
 * Show progress overview and dynamic figure progress mixed process
 * 
 * @author minjdai
 *
 */
public class ProgressOverViewFigure extends FigureOverview<ProgressView> {

	public ProgressOverViewFigure(ViFile observableItem) {
		super(observableItem);
	}

	private void change(DynamicPaintType paintType, final Double... percents) {
		if (figureView == null || !figureView.isInitialized())
			return;
		switch (paintType) {
		case INITIALIZED_ALL:
		case PROCESSED_ALL:
			figureView.updateProgress(paintType.progress());
			break;
		case PROCESSED_PARTIAL:
			if (percents != null && percents.length > 0) {
				figureView.updateProgress(percents[0]);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void dynamic(DynamicPaintType paintType, ViFileInfo... infos) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (infos == null || infos.length == 0) {
					change(paintType);
				} else {
					change(paintType, infos[0].computeProgress());
				}
			}
		});

	}

	/**
	 * static block to register mapping
	 */
	static{
		FigureOverviewPool.logFigureClass(ProgressOverViewFigure.class, ProgressView.class);
	}
}
