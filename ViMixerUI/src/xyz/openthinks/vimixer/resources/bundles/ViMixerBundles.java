package xyz.openthinks.vimixer.resources.bundles;

import openthinks.libs.i18n.implement.bundle.IBundleMessageType;

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

	public static final String BASE_PACK_DIR = "xyz/openthinks/vimixer/resources/bundles/";

}
