/**
 * The dynamic progress show up package
 */
package com.openthinks.vimixer.ui.controller.biz.figure;

import com.openthinks.vimixer.ui.controller.BaseController;
import com.openthinks.vimixer.ui.controller.biz.figure.block.BlocksView;
import com.openthinks.vimixer.ui.controller.biz.figure.progress.ProgressView;
import com.openthinks.vimixer.ui.model.ViFile;

import javafx.scene.Node;

/**
 * graphic for one {@link ViFile} view, render and process
 * @see BlocksView
 * @see ProgressView
 * @author minjdai
 * @since v1.0
 */
public interface Figureable {

	/**
	 * check all the elements at the node are initialized or not
	 * @return
	 */
	public abstract boolean isInitialized();

	/**
	 * initial figure in this UI view
	 * @param observable {@link ViFile}
	 * @param controller {@link BaseController}
	 */
	public abstract void initial(ViFile observable, BaseController controller);

	/**
	 * get base view for this figure
	 * @return {@link Node}
	 */
	public abstract Node getView();

	public abstract void destory();

}