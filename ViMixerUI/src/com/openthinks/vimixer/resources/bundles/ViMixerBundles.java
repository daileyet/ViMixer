package com.openthinks.vimixer.resources.bundles;

import com.openthinks.libs.i18n.implement.bundle.IBundleMessageType;

/**
 * Localization bundle type, include three type message: UI,MODEL,LOG
 * @author dailey.yet@outlook.com
 * @since v1.0
 */
public enum ViMixerBundles implements IBundleMessageType {
	UI, LOG, MODEL;

	@Override
	public String value() {
		return toString() + ":" + BASE_PACK_DIR + toString();
	}

	@Override
	public String getPackName() {
		String packName = null;
		String val = value();
		if (val != null) {
			int split = val.indexOf(":");
			if (-1 != split) {
				packName = val.substring(split + 1);
			}
		}
		return packName;
	}

	@Override
	public String getMessageType() {
		String packType = null;
		String val = value();
		if (val != null) {
			int split = val.indexOf(":");
			if (-1 != split) {
				packType = val.substring(0, split);
			}
		}
		return packType;
	}

	public static final String BASE_PACK_DIR = "openthinks/vimixer/resources/bundles/";

}
