package openthinks.vimixer.ui.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import openthinks.vimixer.ui.controller.biz.ProcessMixBizThread;
import openthinks.vimixer.ui.controller.biz.figure.DynamicPaintType;
import openthinks.vimixer.ui.controller.biz.figure.FigureOverviewPool;
import openthinks.vimixer.ui.controller.biz.figure.block.BlocksView;
import openthinks.vimixer.ui.model.ViFile;
import openthinks.vimixer.ui.model.ViFileInfo;
import openthinks.vimixer.ui.model.ViFileStatus;
import openthinks.vimixer.ui.model.ViFileSupportType;

/**
 * The main window UI controller
 * @author dailey.yet@outlook.com
 * @since v1.0
 */
public class MainFrameController extends BaseController {

	@FXML
	private TableView<ViFile> vifileTable;
	@FXML
	private TableColumn<ViFile, Number> idColumn;
	@FXML
	private TableColumn<ViFile, String> filePathColumn;
	@FXML
	private TableColumn<ViFile, ViFileStatus> statusColumn;
	@FXML
	private TableColumn<ViFile, ViFileInfo> infoColumn;
	@FXML
	private TextField configPathField;
	@FXML
	private Button runBtn;
	@FXML
	private ContextMenu tableCtxmenu;
	@FXML
	private MenuItem resetMenuItem;
	@FXML
	private MenuItem resetAllMenuItem;
	@FXML
	private MenuItem clearMenuItem;
	@FXML
	private MenuItem clearAllMenuItem;
	@FXML
	private FlowPane blockPane;
	@FXML
	private ScrollPane bottomScrollPane;

	public DoubleBinding getBlockPaneWidthProperty() {
		return vifileTable.widthProperty().add(-15);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		filePathColumn.setCellValueFactory(cellData -> cellData.getValue().filePathProperty());
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		infoColumn.setCellValueFactory(cellData -> cellData.getValue().infoProperty());
		tableCtxmenu.setOnShowing((event) -> {
			boolean isShow = !vifileTable.getSelectionModel().getSelectedItems().isEmpty();
			resetMenuItem.setVisible(isShow);
			clearMenuItem.setVisible(isShow);
			isShow = !vifileTable.getItems().isEmpty();
			resetAllMenuItem.setVisible(isShow);
			clearAllMenuItem.setVisible(isShow);
		});
		// resetAllMenuItem.visibleProperty().bind(Bindings.isNotEmpty(vifileTable.getItems()));
		// clearAllMenuItem.visibleProperty().bind(Bindings.isNotEmpty(vifileTable.getItems()));
		// resetMenuItem.visibleProperty().bind(Bindings.isNotEmpty(vifileTable.getSelectionModel().getSelectedItems()));
		// clearMenuItem.visibleProperty().bind(Bindings.isNotEmpty(vifileTable.getSelectionModel().getSelectedItems()));
		blockPane.prefWidthProperty().bind(vifileTable.widthProperty().add(-15));
	}

	@Override
	protected void afterSetTransfer() {
		super.afterSetTransfer();
		// bind table view item property to data model
		vifileTable.itemsProperty().bindBidirectional(this.listProperty());
		configPathField.textProperty().bind(this.configure().storedFileProperty());
		// bind run button disable or not
		this.configure().secretKeyProperty().addListener((observable, oldvalue, newvalue) -> {
			if (newvalue == null || newvalue.trim().isEmpty()) {
				runBtn.setDisable(true);
			} else {
				runBtn.setDisable(false);
			}
		});
		// initial run button disable or not
		if (this.configure().getSecretKey() == null || this.configure().getSecretKey().trim().isEmpty()) {
			runBtn.setDisable(true);
		}
		// start a new thread to monitor the request to show {@link FigureOverview}
		FigureOverviewPool.active();
	}

	@FXML
	private void handStartAction() {
		ObservableList<ViFile> viFiles = this.listProperty().get();
		FilteredList<ViFile> filteredList = viFiles
				.filtered(testFile -> ViFileStatus.NOT_START == testFile.getStatus());
		new ProcessMixBizThread(this, filteredList).start();
	}

	/**
	 * monitor TableView selected a row and show {@link BlocksView}
	 */
	@FXML
	private void handMouseClick() {
		if (vifileTable.getSelectionModel().getSelectedItems().isEmpty())
			;
		else {
			ViFile viFile = vifileTable.getSelectionModel().getSelectedItem();
			configure().getSegmentor().valueOf(viFile).with(this).targetOn(blockPane).push();
		}
	}

	@FXML
	private void handOnDragOver(DragEvent event) {
		Dragboard dragboard = event.getDragboard();
		if (dragboard.hasFiles()) {
			event.acceptTransferModes(TransferMode.LINK);
		} else {
			event.consume();
		}
	}

	@FXML
	private void handDragDropped(DragEvent event) {
		Dragboard dragboard = event.getDragboard();
		boolean success = false;
		if (dragboard.hasFiles()) {
			for (File file : dragboard.getFiles()) {
				if (file.isFile() && ViFileSupportType.accept(file)) {
					ViFile viFile = new ViFile(file);
					ObservableList<ViFile> viFiles = this.listProperty().get();
					if (!viFiles.contains(viFile)) {
						viFiles.add(viFile);
						success = true;
					}
				}
			}
		}
		event.setDropCompleted(success);
		event.consume();
	}

	@FXML
	private void handResetAction() {
		ObservableList<ViFile> viFiles = vifileTable.getSelectionModel().getSelectedItems();
		for (ViFile viFile : viFiles) {
			viFile.reset();
			configure().getSegmentor().valueOf(viFile).with(this).dynamic(DynamicPaintType.INITIALIZED_ALL);
		}
	}

	@FXML
	private void handResetAllAction() {
		ObservableList<ViFile> viFiles = this.listProperty().get();
		for (ViFile viFile : viFiles) {
			viFile.reset();
			configure().getSegmentor().valueOf(viFile).with(this).dynamic(DynamicPaintType.INITIALIZED_ALL);
		}
	}

	@FXML
	private void handClearAction() {
		ObservableList<ViFile> viFiles = vifileTable.getSelectionModel().getSelectedItems();
		vifileTable.getItems().removeAll(viFiles);
		FigureOverviewPool.removeAll(viFiles);
	}

	@FXML
	private void handClearAllAction() {
		this.listProperty().get().clear();
		FigureOverviewPool.clear();
	}

}
