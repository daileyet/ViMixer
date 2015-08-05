package openthinks.vimixer.ui.controller.biz.figure.block;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import openthinks.crypto.mix.MixBlock;
import openthinks.vimixer.ui.controller.biz.figure.DynamicPaintType;
import openthinks.vimixer.ui.controller.biz.figure.FigureOverview;
import openthinks.vimixer.ui.controller.biz.figure.FigureOverviewPool;
import openthinks.vimixer.ui.model.ViFile;
import openthinks.vimixer.ui.model.ViFileInfo;

/**
 * Show blocks overview and dynamic figure blocks mixed process
 * @author minjdai
 * @since v1.0
 */
public class BlockOverViewFigure extends FigureOverview<BlocksView> {
	public BlockOverViewFigure(ViFile observableItem) {
		super(observableItem);
	}

	private void change(DynamicPaintType paintType, MixBlock... blocks) {
		if (figureView == null || !figureView.isInitialized())
			return;
		switch (paintType) {
		case INITIALIZED_ALL:
		case PROCESSED_ALL:
			for (Node node : figureView.getChildren()) {
				((Shape) node).setFill(paintType.color());
			}
			break;
		case PROCESSED_PARTIAL:
			if (blocks != null) {
				for (MixBlock block : blocks) {
					figureView.find(block).setFill(paintType.color());
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Dynamic change block unit which is completed or not start
	 * 
	 * @param paintType
	 * @param infos
	 */
	@Override
	public void dynamic(DynamicPaintType paintType, ViFileInfo... infos) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (infos == null || infos.length == 0) {
					change(paintType);
				} else {
					change(paintType, infos[0].getCurrentProcessedBlock());
				}
			}
		});

	}

	/**
	 * static block to register mapping
	 */
	static {
		FigureOverviewPool.logFigureClass(BlockOverViewFigure.class, BlocksView.class);
	}
}
