package com.openthinks.vimixer;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.openthinks.libs.i18n.I18n;
import com.openthinks.libs.i18n.I18nApplicationLocale;
import com.openthinks.vimixer.resources.ResourceLoader;
import com.openthinks.vimixer.resources.bundles.ViMixerBundles;
import com.openthinks.vimixer.ui.controller.ConfigurePaneController;
import com.openthinks.vimixer.ui.controller.MainFrameController;
import com.openthinks.vimixer.ui.controller.RootLayoutController;
import com.openthinks.vimixer.ui.model.ViFile;
import com.openthinks.vimixer.ui.model.configure.ViMixerConfigure;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The main start entry for this application
 * @author minjdai
 * @since v1.0
 */
public class ViMixerApp extends Application implements Observer {

	private BorderPane rootLayout;
	private final TransferData transfer = new TransferData(this);

	@Override
	public void start(Stage primaryStage) {
		I18nApplicationLocale.getInstance().addObserver(this);
		primaryStage.getIcons().add(ResourceLoader.APP_ICON);
		primaryStage.setTitle(I18n.getMessage(ViMixerBundles.UI, "app.title"));
		primaryStage.setOnCloseRequest((event) -> {
			System.exit(0);
		});
		transfer.setPrimaryStage(primaryStage);
		initRootLayout();
		showMainFrame();
	}

	private void initRootLayout() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ResourceLoader.FXML_ROOTLAYOUT);
		loader.setResources(I18n.getResourceBundle(ViMixerBundles.UI));
		try {
			this.rootLayout = loader.load();
			Scene scene = new Scene(this.rootLayout);
			scene.getStylesheets().add(ResourceLoader.CSS_APP.toExternalForm());

			RootLayoutController controller = loader.getController();
			controller.setTransfer(transfer);

			transfer.stage().setScene(scene);
			transfer.stage().show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showMainFrame() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ResourceLoader.FXML_MAINFRAME);
		loader.setResources(I18n.getResourceBundle(ViMixerBundles.UI));
		try {
			AnchorPane anchorPane = loader.load();
			MainFrameController controller = loader.getController();
			controller.setTransfer(transfer);
			this.rootLayout.setCenter(anchorPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showConfigurePane() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ResourceLoader.FXML_CONFIGUREPANE);
		loader.setResources(I18n.getResourceBundle(ViMixerBundles.UI));
		try {
			AnchorPane anchorPane = loader.load();
			ConfigurePaneController controller = loader.getController();
			controller.setTransfer(transfer);
			Stage stage = new Stage();
			stage.getIcons().add(ResourceLoader.APP_ICON);
			stage.initOwner(transfer.stage());
			stage.setTitle(I18n.getMessage(ViMixerBundles.UI, "stage.conf.title"));
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setResizable(false);
			Scene scene = new Scene(anchorPane);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Wrapped data which will be passed between each window
	 * @author dailey.yet@outlook.com
	 * @since v1.0
	 */
	public static class TransferData {
		private final Application application;

		private final ViMixerConfigure configureXML = new ViMixerConfigure();

		private final ObjectProperty<ObservableList<ViFile>> viFiles = new SimpleObjectProperty<>(
				FXCollections.observableArrayList());
		private Stage primaryStage;

		public TransferData(final Application application) {
			this.application = application;
		}

		private void setPrimaryStage(Stage primaryStage) {
			this.primaryStage = primaryStage;
		}

		public Application app() {
			return application;
		}

		public ObjectProperty<ObservableList<ViFile>> listProperty() {
			return viFiles;
		}

		public ViMixerConfigure configure() {
			return configureXML;
		}

		public Stage stage() {
			return primaryStage;
		}
	}

	private void reload() {
		initRootLayout();
		showMainFrame();
	}

	/**
	 * reload for localization message
	 */
	@Override
	public void update(Observable o, Object newloace) {
		transfer.stage().setTitle(I18n.getMessage(ViMixerBundles.UI, "app.title"));
		reload();
	}

}
