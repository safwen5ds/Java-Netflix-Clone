package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import org.fsb.FlixFlow.Models.Role_film;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

public class Role_FilmAddController {
    @FXML
    private TableView<Role_film> roleFilmTableView;
    @FXML
    private TableColumn<Role_film, Integer> id_acteurColumn;
    @FXML
    private TableColumn<Role_film, Integer> id_filmColumn;
    @FXML
    private TableColumn<Role_film, String> role_typeColumn;
    @FXML
    private TableColumn<Role_film, String> url_imageColumn;
    @FXML
    private TextField id_acteurTextField;
    @FXML
    private TextField id_filmTextField;
    @FXML
    private TextField role_typeTextField;
    @FXML
    private TextField url_imageTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Role_film> roleFilmList;
    @FXML
    public void initialize() {
        id_acteurColumn.setCellValueFactory(new PropertyValueFactory<>("id_acteur"));
        id_filmColumn.setCellValueFactory(new PropertyValueFactory<>("id_film"));
        role_typeColumn.setCellValueFactory(new PropertyValueFactory<>("role_type"));
        url_imageColumn.setCellValueFactory(new PropertyValueFactory<>("url_image"));

        roleFilmList = FXCollections.observableArrayList();
        roleFilmTableView.setItems(roleFilmList);

        roleFilmTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Role_film selectedRoleFilm = roleFilmTableView.getSelectionModel().getSelectedItem();
                id_acteurTextField.setText(String.valueOf(selectedRoleFilm.getId_acteur()));
                id_filmTextField.setText(String.valueOf(selectedRoleFilm.getId_film()));
                role_typeTextField.setText(selectedRoleFilm.getRole_type());
                url_imageTextField.setText(selectedRoleFilm.getUrl_image());
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            ObservableList<Role_film> roleFilms = FXCollections.observableArrayList(DatabaseUtil.getAllRoleFilms());
            roleFilmList.setAll(roleFilms);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        try {
            Role_film roleFilm = new Role_film(Integer.parseInt(id_acteurTextField.getText()),
                    Integer.parseInt(id_filmTextField.getText()),
                    role_typeTextField.getText(),
                    url_imageTextField.getText());
            DatabaseUtil.addRoleFilm(roleFilm);
            roleFilmList.add(roleFilm);
            clearForm();
        } catch (SQLException e) {
            showErrorAlert("Error adding Role_film", e);
        }
    }

    @FXML
    private void handleUpdateAction(ActionEvent event) {
        Role_film selectedRoleFilm = roleFilmTableView.getSelectionModel().getSelectedItem();
        if (selectedRoleFilm != null) {
            try {
                selectedRoleFilm.setId_acteur(Integer.parseInt(id_acteurTextField.getText()));
                selectedRoleFilm.setId_film(Integer.parseInt(id_filmTextField.getText()));
                selectedRoleFilm.setRole_type(role_typeTextField.getText());
                selectedRoleFilm.setUrl_image(url_imageTextField.getText());
                DatabaseUtil.updateRoleFilm(selectedRoleFilm);
                roleFilmTableView.refresh();
                clearForm();
            } catch (SQLException e) {
                showErrorAlert("Error updating Role_film", e);
            }
        } else {
            showWarningAlert("No Role_film Selected", "Please select a Role_film to update.");
        }
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Role_film selectedRoleFilm = roleFilmTableView.getSelectionModel().getSelectedItem();
        if (selectedRoleFilm != null) {
            try {
                DatabaseUtil.deleteRoleFilm(selectedRoleFilm);
                roleFilmList.remove(selectedRoleFilm);
                clearForm();
            } catch (SQLException e) {
                showErrorAlert("Error deleting Role_film", e);
            }
        } else {
            showWarningAlert("No Role_film Selected", "Please select a Role_film to delete.");
        }
    }

    private void clearForm() {
        id_acteurTextField.clear();
        id_filmTextField.clear();
        role_typeTextField.clear();
        url_imageTextField.clear();
    }

    private void showErrorAlert(String title, SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private void showWarningAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
