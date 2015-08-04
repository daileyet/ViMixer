package openthinks.vimixer.ui.controller.biz.figure;

import openthinks.vimixer.ui.model.ViFileInfo;

/**
 * Dynamic change interface for {@link FigureOverview}
 * @author minjdai
 *
 */
public interface Dynamically {

	/**
	 * dynamic change in {@link FigureOverview}
	 * @param paintType {@link DynamicPaintType}
	 * @param infos {@link ViFileInfo}
	 */
	public void dynamic(DynamicPaintType paintType, ViFileInfo... infos);
}
