package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.fsb.FlixFlow.Models.Utilisateur;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.sql.SQLException;
import java.util.Date;

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
    private TextField typeTextField;

    @FXML
    private void addUtilisateur() {
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(firstNameTextField.getText());
            utilisateur.setPrenom(lastNameTextField.getText());
            utilisateur.setEmail(emailTextField.getText());
            utilisateur.setMot_de_passe(passwordTextField.getText());
            utilisateur.setDate_de_naissance(java.sql.Date.valueOf(birthDatePicker.getValue()));
            utilisateur.setType(typeTextField.getText());

            DatabaseUtil.addUtilisateur(utilisateur);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
