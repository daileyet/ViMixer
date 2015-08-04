package xyz.openthinks.vimixer.ui.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import openthinks.libs.i18n.I18n;
import openthinks.libs.i18n.I18nApplicationLocale;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.FileChooser;
import xyz.openthinks.vimixer.ViMixerApp;
import xyz.openthinks.vimixer.resources.bundles.ViMixerBundles;
import xyz.openthinks.vimixer.ui.model.ViFile;
import xyz.openthinks.vimixer.ui.model.ViFileSupportType;
import xyz.openthinks.vimixer.ui.util.VersionGetter;

public class RootLayoutController extends BaseController {

	@FXML
	private RadioMenuItem en_Menuitem;
	@FXML
	private RadioMenuItem zh_Menuitem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		//initial locale menu item selected
		Locale currentLocale = I18nApplicationLocale.getInstance().getCurrentLocale();
		if (Locale.CHINA.equals(currentLocale) || Locale.CHINESE.equals(currentLocale)) {
			zh_Menuitem.setSelected(true);
		} else {
			en_Menuitem.setSelected(true);
		}

	}

	@Override
	protected void afterSetTransfer() {
		super.afterSetTransfer();
		File storeFile = this.lastConfigureFile();
		loadConfigure(storeFile);
	}

	@FXML
	private void handAddAction() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter1 = new FileChooser.ExtensionFilter("video files",
				ViFileSupportType.SUPPORT_LIST_VIDEO);
		FileChooser.ExtensionFilter extensionFilter2 = new FileChooser.ExtensionFilter("image files",
				ViFileSupportType.SUPPORT_LIST_IMAGE);
		fileChooser.getExtensionFilters().addAll(extensionFilter1, extensionFilter2);
		fileChooser.setTitle(I18n.getMessage(ViMixerBundles.UI, "dialog.open.title"));//overrider its title
		List<File> files = fileChooser.showOpenMultipleDialog(this.stage());
		if (files != null) {
			for (File file : files) {
				this.listProperty().get().add(new ViFile(file));
			}
		}
	}

	@FXML
	private void handQuitAction() {
		System.exit(0);
	}

	@FXML
	private void handViewCurrent() {
		((ViMixerApp) this.app()).showConfigurePane();
	}

	@FXML
	private void handSaveConfigure() {
		File storeFile = this.lastConfigureFile();
		if (storeFile != null) {
			saveConfigure(storeFile);
		} else {
			handSaveAsConfigure();
		}
	}

	@FXML
	private void handSaveAsConfigure() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		// Show save file dialog
		fileChooser.setTitle(I18n.getMessage(ViMixerBundles.UI, "dialog.save.title"));//overrider its title
		File file = fileChooser.showSaveDialog(this.stage());
		this.saveConfigure(file);
	}

	@FXML
	private void handLoadConfigure() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		// Show save file dialog
		fileChooser.setTitle(I18n.getMessage(ViMixerBundles.UI, "dialog.open.title"));//overrider its title
		File file = fileChooser.showOpenDialog(this.stage());
		this.loadConfigure(file);
	}

	@FXML
	private void handAbout() {
		String buttonText = I18n.getMessage(ViMixerBundles.UI, "about.alert.button.ok");
		ButtonType customerButton = new ButtonType(buttonText, ButtonData.OK_DONE);//overrider its default button
		String version = VersionGetter.valueOf(ViMixerApp.class).get();
		String alertContent = I18n.getMessage(ViMixerBundles.UI, "about.alert.content", version, "Dailey Dai",
				"http://openthinks.xyz");
		Alert alert = new Alert(AlertType.INFORMATION, alertContent, customerButton);
		alert.setTitle(getBundleMessage("app.title"));
		alert.setHeaderText(getBundleMessage("about.alert.header"));
		alert.initOwner(this.stage());
		alert.showAndWait();
	}

	@FXML
	private void handLocaleChange(Event event) {
		String menuitemId = ((RadioMenuItem) event.getSource()).getId();
		String changeToLocale = menuitemId.split("_")[0];
		Locale locale = new Locale.Builder().setLanguage(changeToLocale).build();
		I18nApplicationLocale.getInstance().changeCurrentLocale(locale);
	}

}
