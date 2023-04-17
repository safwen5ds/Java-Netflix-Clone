package org.fsb.FlixFlow.Controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import org.fsb.FlixFlow.Utilities.DatabaseUtil;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

    @FXML
    private AnchorPane anchor;
    
    @FXML
    private Label alert1; 

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
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void changescene2() {
    	String email = inputemail.getText();
        String password = inputpass.getText();

        if (!isValidEmail(email)) {
            alert1.setText("Email Invalide !");
            return;
        }

        if (!DatabaseUtil.checkUserCredentials(email, password)) {
        	showErrorDialog("Invalid credentials");
            return;
        }
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
