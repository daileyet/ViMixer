package xyz.openthinks.vimixer.ui.model;

import openthinks.libs.i18n.I18n;
import xyz.openthinks.vimixer.resources.bundles.ViMixerBundles;
import xyz.openthinks.vimixer.ui.controller.biz.figure.DynamicPaintType;

public enum ViFileStatus {
	NOT_START(DynamicPaintType.INITIALIZED_ALL), IN_PROCESSING(DynamicPaintType.INITIALIZED_ALL), COMPLETED(
			DynamicPaintType.PROCESSED_ALL);

	private DynamicPaintType painType;

	private ViFileStatus(DynamicPaintType painType) {
		this.painType = painType;
	}

	public String toString() {
		return I18n.getMessage(ViMixerBundles.MODEL, this.name());
	};

	public DynamicPaintType paintType() {
		return this.painType;
	}

}