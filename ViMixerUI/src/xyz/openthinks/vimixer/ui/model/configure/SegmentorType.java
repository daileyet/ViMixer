package xyz.openthinks.vimixer.ui.model.configure;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import openthinks.libs.i18n.I18n;
import xyz.openthinks.vimixer.resources.bundles.ViMixerBundles;

@XmlType
@XmlEnum(String.class)
public enum SegmentorType {
	SMART, SIMPLE;

	@Override
	public String toString() {
		return I18n.getMessage(ViMixerBundles.MODEL, this.name());
	}

	public static void main(String[] args) {
		//		I18nApplicationLocale.getInstance().changeCurrentLocale(Locale.CHINESE);
		System.out.println(SMART);
	}
}
