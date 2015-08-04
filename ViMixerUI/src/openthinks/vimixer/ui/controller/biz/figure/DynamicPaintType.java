package openthinks.vimixer.ui.controller.biz.figure;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum DynamicPaintType {
	INITIALIZED_ALL(Color.ORANGE, 0D), PROCESSED_ALL(Color.GREEN, 1D), PROCESSED_PARTIAL(Color.GREEN, .5D), SELECTED(
			Color.CORNFLOWERBLUE, null);

	private final Paint paint;
	private final Double progress;

	private DynamicPaintType(final Paint paint, final Double progress) {
		this.paint = paint;
		this.progress = progress;
	}

	public final Paint color() {
		return this.paint;
	}

	public final Double progress() {
		return this.progress;
	}
}
