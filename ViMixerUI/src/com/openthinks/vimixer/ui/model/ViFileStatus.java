package com.openthinks.vimixer.ui.model;

import com.openthinks.libs.i18n.I18n;
import com.openthinks.vimixer.resources.bundles.ViMixerBundles;
import com.openthinks.vimixer.ui.controller.biz.figure.DynamicPaintType;

public enum ViFileStatus {
	NOT_START(DynamicPaintType.INITIALIZED_ALL), IN_PROCESSING(DynamicPaintType.INITIALIZED_ALL), COMPLETED(
			DynamicPaintType.PROCESSED_ALL);

	private DynamicPaintType painType;

	private ViFileStatus(DynamicPaintType painType) {
		this.painType = painType;
	}

	@Override
	public String toString() {
		return I18n.getMessage(ViMixerBundles.MODEL, this.name());
	};

	public DynamicPaintType paintType() {
		return this.painType;
	}

}