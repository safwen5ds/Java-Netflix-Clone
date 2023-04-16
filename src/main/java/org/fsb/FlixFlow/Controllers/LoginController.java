package org.fsb.FlixFlow.Controllers;

import java.io.IOException;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button btnlogin;

    @FXML
    private Button btnreg;

    @FXML
    private FontAwesomeIconView ic1;

    @FXML
    private FontAwesomeIconView ic2;

    @FXML
    private FontAwesomeIconView ic3;

    @FXML
    private ImageView im1;

    @FXML
    private ImageView im2;

    @FXML
    private TextField inputemail;

    @FXML
    private PasswordField inputpass;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Pane pane1;

    @FXML
    public void initialize() {
        btnreg.setOnAction(event -> changeScene());
        btnlogin.setOnAction(event -> changescene2());
    }

    private void changescene2() {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/dash.fxml"));
             Parent secondaryRoot = loader.load();
             Scene secondaryScene = new Scene(secondaryRoot);
             secondaryScene .getStylesheets().add(getClass().getResource("/FXML/Styles/discord_theme.css").toExternalForm());
             Stage primaryStage = (Stage) btnreg.getScene().getWindow();
             primaryStage.setScene(secondaryScene);
         } catch (IOException e) {
             e.printStackTrace();
         }
	}

	private void changeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/secondary.fxml"));
            Parent secondaryRoot = loader.load();
            Scene secondaryScene = new Scene(secondaryRoot);

            Stage primaryStage = (Stage) btnreg.getScene().getWindow();
            primaryStage.setScene(secondaryScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
