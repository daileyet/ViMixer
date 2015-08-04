package xyz.openthinks.vimixer.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import xyz.openthinks.vimixer.ui.model.configure.Segmentor;
import xyz.openthinks.vimixer.ui.model.configure.SegmentorType;
import xyz.openthinks.vimixer.ui.model.configure.SimpleLinearSegmentor;
import xyz.openthinks.vimixer.ui.model.configure.SmartLinearSegmentor;
import xyz.openthinks.vimixer.ui.model.configure.ViMixerConfigure;
import xyz.openthinks.vimixer.ui.util.MD5;
import xyz.openthinks.vimixer.ui.util.NumberStringConverter;

public class ConfigurePaneController extends BaseController {
	@FXML
	private TextField nameField;
	@FXML
	private PasswordField secretField;
	@FXML
	private ComboBox<SegmentorType> typeCombox;
	@FXML
	private Slider spaceSlider;
	@FXML
	private Slider lengthSlider;
	@FXML
	private Label spaceValueLabel;
	@FXML
	private Label lengthValuelabel;
	@FXML
	private PasswordField secretStoredField;

	private static final SmartLinearSegmentor SMART = new SmartLinearSegmentor();
	private static final SimpleLinearSegmentor SIMPLE = new SimpleLinearSegmentor();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		secretStoredField.setVisible(false);
		spaceValueLabel.textProperty().bindBidirectional(spaceSlider.valueProperty(), new NumberStringConverter());
		lengthValuelabel.textProperty().bindBidirectional(lengthSlider.valueProperty(), new NumberStringConverter());

		typeCombox.getItems().add(SmartLinearSegmentor.TYPE);
		typeCombox.getItems().add(SimpleLinearSegmentor.TYPE);
		typeCombox.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			if (SmartLinearSegmentor.TYPE == newvalue) {
				spaceSlider.setDisable(true);
				lengthSlider.setDisable(true);
				ConfigurePaneController.this.configure().setSegmentor(SMART);
			} else {
				spaceSlider.setDisable(false);
				lengthSlider.setDisable(false);
				SIMPLE.refresh(ConfigurePaneController.this.configure().getSegmentor());
				ConfigurePaneController.this.configure().setSegmentor(SIMPLE);
			}
		});
		spaceSlider.valueProperty().bindBidirectional(SIMPLE.spaceProperty());
		lengthSlider.valueProperty().bindBidirectional(SIMPLE.lengthProperty());
	}

	@Override
	protected void afterSetTransfer() {
		super.afterSetTransfer();
		ViMixerConfigure configure = this.configure();
		nameField.textProperty().bindBidirectional(configure.configureNameProperty());
		secretField.textProperty().bindBidirectional(configure.tempSecretKeyProperty());
		secretStoredField.textProperty().bindBidirectional(configure.secretKeyProperty());
		//update real secret key by MD5
		secretField.focusedProperty().addListener(
				(observable, oldvalue, newvalue) -> {
					if (oldvalue == true && newvalue == false) {
						if (configure.getTempSecretKey() != null && !configure.getTempSecretKey().trim().isEmpty()
								&& !configure.getTempSecretKey().equals(configure.getSecretKey())) {
							MD5 md5 = new MD5();
							configure.setSecretKey(md5.getMD5ofStr(configure.getTempSecretKey()));
						} else {
							configure.setSecretKey("");
						}
					}
				});

		Segmentor segmentor = configure.getSegmentor();
		if (segmentor == null) {
			segmentor = SMART;
		}
		typeCombox.valueProperty().bindBidirectional(segmentor.typeProperty());

		//if(segmentor instanceof SimpleLinearSegmentor)
		SIMPLE.refresh(segmentor);
	}

}
