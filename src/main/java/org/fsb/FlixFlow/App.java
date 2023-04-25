package org.fsb.FlixFlow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {

		scene = new Scene(loadFXML("Login"), 1200, 600);
		scene.getStylesheets().add("src/main/resources/FXML/Styles/Login.css");
		stage.setScene(scene);
		stage.show();
	}

	public static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		URL url = App.class.getResource("/FXML/" + fxml + ".fxml");
		if (url == null) {
			throw new RuntimeException("FXML file not found: " + fxml);
		}
		System.out.println("Loading FXML from: " + url);
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}

}