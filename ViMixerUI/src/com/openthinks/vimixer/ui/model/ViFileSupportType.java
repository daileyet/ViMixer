package com.openthinks.vimixer.ui.model;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViMixer support file types
 * @author minjdai
 *
 */
public class ViFileSupportType {
	public static final ObservableList<String> SUPPORT_LIST_VIDEO;
	public static final ObservableList<String> SUPPORT_LIST_IMAGE;
	static {
		SUPPORT_LIST_VIDEO = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList("*.mkv",
				"*.flv", "*.ogg", "*.ogv", "*.avi", "*.mov", "*.qt", "*.wmv", "*.rm", ".rmvb", "*.mp4", "*.mpg",
				"*.mpeg", "*.3gp", "*.3g2"));
		SUPPORT_LIST_IMAGE = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList("*.tiff",
				"*.png", "*.gif", "*.jpg", "*.bmp", "*.jpeg"));
	}

	public static boolean accept(File file) {
		if (file == null)
			return false;
		String fileName = file.getName().toLowerCase();
		for (String support : SUPPORT_LIST_VIDEO) {
			String sufixx = support.replace("*", "");
			if (fileName.endsWith(sufixx)) {
				return true;
			}
		}
		for (String support : SUPPORT_LIST_IMAGE) {
			String sufixx = support.replace("*", "");
			if (fileName.endsWith(sufixx)) {
				return true;
			}
		}
		return false;
	}

}
