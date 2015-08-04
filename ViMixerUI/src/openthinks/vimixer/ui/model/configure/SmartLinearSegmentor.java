package openthinks.vimixer.ui.model.configure;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import openthinks.crypto.mix.MixSegmentor;
import openthinks.crypto.mix.impl.SmartMixSegment;
import openthinks.vimixer.ui.controller.biz.figure.block.BlockOverViewFigure;
import openthinks.vimixer.ui.model.ViFile;

@XmlType
@XmlRootElement(name = "smart-segmentor")
public class SmartLinearSegmentor extends Segmentor {
	public static final SegmentorType TYPE = SegmentorType.SMART;

	public SmartLinearSegmentor() {
		super(TYPE);
	}

	@Override
	public String toString() {
		return "SmartLinearSegmentor [getType()=" + getType() + "]";
	}

	@Override
	public void refresh(Segmentor otherSeg) {

	}

	@Override
	public MixSegmentor mixSegmentor() {
		return SmartMixSegment.get();
	}

	@Override
	public BlockOverViewFigure valueOf(ViFile viFile) {
		return new BlockOverViewFigure(viFile);
	}

}
