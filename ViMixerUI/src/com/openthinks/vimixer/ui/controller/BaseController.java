/**
 * 
 */
package com.openthinks.vimixer.ui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBException;

import com.openthinks.libs.i18n.I18n;
import com.openthinks.libs.i18n.I18nApplicationLocale;
import com.openthinks.vimixer.ViMixerApp;
import com.openthinks.vimixer.ViMixerApp.TransferData;
import com.openthinks.vimixer.resources.bundles.ViMixerBundles;
import com.openthinks.vimixer.ui.model.ViFile;
import com.openthinks.vimixer.ui.model.configure.ViMixerConfigure;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Base controller for common method and attribute
 * @author minjdai
 * @since v1.0
 */
public abstract class BaseController implements Initializable, Observer {
	private static final String PREF_FILE = "filePath";
	private TransferData transfer;
	protected ResourceBundle resourceBundle;

	public final void setTransfer(final TransferData transfer) {
		this.beforeSetTransfer();
		this.transfer = transfer;
		this.afterSetTransfer();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		I18nApplicationLocale.getInstance().addObserver(this);
		this.resourceBundle = resources;
	}

	@Override
	public void update(Observable o, Object newlocale) {
		this.resourceBundle = I18n.getResourceBundle(ViMixerBundles.UI, (Locale) newlocale);
	}

	protected String getBundleMessage(String bundleKey) {
		if (this.resourceBundle == null) {
			return "";
		}
		return this.resourceBundle.getString(bundleKey);
	}

	protected void afterSetTransfer() {
	}

	protected void beforeSetTransfer() {
	}

	public final Application app() {
		return transfer.app();
	}

	public final ObjectProperty<ObservableList<ViFile>> listProperty() {
		return transfer.listProperty();
	}

	public final ViMixerConfigure configure() {
		return transfer.configure();
	}

	public final Stage stage() {
		return transfer.stage();
	}

	public final File lastConfigureFile() {
		Preferences preferences = Preferences.userNodeForPackage(ViMixerApp.class);
		String filePath = preferences.get(PREF_FILE, null);
		if (filePath != null) {
			return new File(filePath);
		}
		return null;
	}

	private final void storeConfigurePath(File file) {
		Preferences preferences = Preferences.userNodeForPackage(ViMixerApp.class);
		if (file != null) {
			preferences.put(PREF_FILE, file.getPath());
			configure().setStoredFile(file.getAbsolutePath());
		} else {
			preferences.remove(PREF_FILE);
			configure().setStoredFile(null);
		}
	}

	/**
	 * @param file
	 */
	public final void loadConfigure(File file) {
		if (file != null) {
			try {
				ViMixerConfigure loadCconfigure = ViMixerConfigure.unmarshal(file);
				configure().setConfigureName(loadCconfigure.getConfigureName());
				configure().setSecretKey(loadCconfigure.getSecretKey());
				configure().setTempSecretKey(loadCconfigure.getSecretKey());
				configure().setSegmentor(loadCconfigure.getSegmentor());
				this.storeConfigurePath(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param file
	 */
	public final void saveConfigure(File file) {
		if (file != null) {
			try {
				ViMixerConfigure.marshal(this.configure(), file);
				this.storeConfigurePath(file);
			} catch (FileNotFoundException | JAXBException e) {
				e.printStackTrace();
			}
		}
	}

}
