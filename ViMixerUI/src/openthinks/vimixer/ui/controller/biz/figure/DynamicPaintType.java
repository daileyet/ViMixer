package openthinks.vimixer.ui.controller.biz.figure;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import openthinks.vimixer.ui.controller.biz.figure.block.BlocksView;
import openthinks.vimixer.ui.controller.biz.figure.progress.ProgressView;

/**
 * The paint color and default percent progress for which implementaion {@link Dynamically}
 * @author dailey.yet@outlook.com
 * @since v1.0
 */
public enum DynamicPaintType {
	INITIALIZED_ALL(Color.ORANGE, 0D), PROCESSED_ALL(Color.GREEN, 1D), PROCESSED_PARTIAL(Color.GREEN, .5D), SELECTED(
			Color.CORNFLOWERBLUE, null);

	private final Paint paint;
	private final Double progress;

	private DynamicPaintType(final Paint paint, final Double progress) {
		this.paint = paint;
		this.progress = progress;
	}

	/**
	 * used for {@link BlocksView}
	 * @return Paint
	 */
	public final Paint color() {
		return this.paint;
	}

	/**
	 * used for {@link ProgressView}
	 * @return Double
	 */
	public final Double progress() {
		return this.progress;
	}
}
