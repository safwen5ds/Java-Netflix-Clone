package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.fsb.FlixFlow.Models.Role_film;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.sql.SQLException;

public class Role_FilmAddController {

    @FXML
    private TextField idActeurTextField;

    @FXML
    private TextField idFilmTextField;

    @FXML
    private TextField roleTypeTextField;

    @FXML
    private void addRoleFilm() {
        Role_film roleFilm = new Role_film(
                Integer.parseInt(idActeurTextField.getText()),
                Integer.parseInt(idFilmTextField.getText()),
                roleTypeTextField.getText()
        );

        try {
            DatabaseUtil.addRoleFilm(roleFilm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateRoleFilm() {
        Role_film roleFilm = new Role_film(
                Integer.parseInt(idActeurTextField.getText()),
                Integer.parseInt(idFilmTextField.getText()),
                roleTypeTextField.getText()
        );

        try {
            DatabaseUtil.updateRoleFilm(roleFilm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteRoleFilm() {
        try {
            DatabaseUtil.deleteRoleFilm(
                    Integer.parseInt(idActeurTextField.getText()),
                    Integer.parseInt(idFilmTextField.getText())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
