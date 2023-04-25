package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.fsb.FlixFlow.Models.Utilisateur;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import java.sql.Date;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;






import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserAddController {
    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private ComboBox<String> userTypeComboBox;


    @FXML
    private TableView<Utilisateur> utilisateurTableView;

    @FXML
    private TableColumn<Utilisateur, Integer> id_utilisateurColumn;

    @FXML
    private TableColumn<Utilisateur, String> nomColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;
    @FXML
    private TableColumn<Utilisateur, String> mot_de_passeColumn;
    @FXML
    private TableColumn<Utilisateur, String> date_de_naissanceColumn;
    @FXML
    private TableColumn<Utilisateur, String> typeColumn;

    private DatabaseUtil dbUtil;

    @FXML
    public void initialize() {
    	ObservableList<String> userTypes = FXCollections.observableArrayList(
    		    "Admin",
    		    "Normal User",
    		    "Actor",
    		    "Producer"
    		);
    		userTypeComboBox.setItems(userTypes);
    		userTypeComboBox.setPromptText("Select User Type");

        dbUtil = new DatabaseUtil();

        id_utilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("id_utilisateur"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        mot_de_passeColumn.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        date_de_naissanceColumn.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));


        try {
            utilisateurTableView.setItems(getUtilisateurList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Utilisateur> getUtilisateurList() throws SQLException {
        ObservableList<Utilisateur> utilisateurList = FXCollections.observableArrayList();
        utilisateurList.addAll(dbUtil.getAllUtilisateurs());
        return utilisateurList;
    }

    @FXML
    private void addUtilisateur() {
        if (validateInput()) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(firstNameTextField.getText());
            utilisateur.setPrenom(lastNameTextField.getText());
            utilisateur.setEmail(emailTextField.getText());
            utilisateur.setMot_de_passe(passwordTextField.getText());
            utilisateur.setDate_de_naissance(Date.valueOf(birthDatePicker.getValue()));
            utilisateur.setType(userTypeComboBox.getValue());


            try {
                DatabaseUtil.addUtilisateur(utilisateur);
                utilisateurTableView.setItems(getUtilisateurList());
                clearInputFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (firstNameTextField.getText() == null || firstNameTextField.getText().isEmpty()) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameTextField.getText() == null || lastNameTextField.getText().isEmpty()) {
            errorMessage += "No valid last name!\n";
        }
        if (emailTextField.getText() == null || emailTextField.getText().isEmpty()) {
            errorMessage += "No valid email!\n";
        } else {
            if (!Pattern.matches("^(.+)@(.+)$", emailTextField.getText())) {
                errorMessage += "Invalid email format!\n";
            }
        }
        if (passwordTextField.getText() == null || passwordTextField.getText().isEmpty()) {
            errorMessage += "No valid password!\n";
        }
        if (birthDatePicker.getValue() == null) {
            errorMessage += "No valid birth date!\n";
        }
        if (userTypeComboBox.getValue() == null || userTypeComboBox.getValue().isEmpty()) {
            errorMessage += "No valid user type!\n";
        }


        if (errorMessage.isEmpty()) {
            return true;
        } else {
          
            return false;
        }
    }

    private void clearInputFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        passwordTextField.clear();
        birthDatePicker.setValue(null);
        userTypeComboBox.getSelectionModel().clearSelection();
    }

}

