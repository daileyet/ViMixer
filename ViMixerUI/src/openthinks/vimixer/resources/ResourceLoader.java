package openthinks.vimixer.resources;

import java.net.URL;

import javafx.scene.image.Image;

/**
 * Resource management
 * @author dailey.yet@outlook.com
 * @since v1.0
 */
public class ResourceLoader {
	public static final Image APP_ICON;
	public static final URL FXML_ROOTLAYOUT;
	public static final URL FXML_MAINFRAME;
	public static final URL CSS_APP;
	public static final URL FXML_CONFIGUREPANE;

	static {
		// UI layout FXML
		FXML_ROOTLAYOUT = ResourceLoader.class.getResource("/openthinks/vimixer/ui/view/RootLayout.fxml");
		FXML_MAINFRAME = ResourceLoader.class.getResource("/openthinks/vimixer/ui/view/MainFrame.fxml");
		FXML_CONFIGUREPANE = ResourceLoader.class.getResource("/openthinks/vimixer/ui/view/ConfigurePane.fxml");
		// app icon and css style
		CSS_APP = ResourceLoader.class.getResource("/openthinks/vimixer/ui/view/application.css");
		APP_ICON = new Image(ResourceLoader.class.getResourceAsStream("vimixer_4.png"));

	}
}
