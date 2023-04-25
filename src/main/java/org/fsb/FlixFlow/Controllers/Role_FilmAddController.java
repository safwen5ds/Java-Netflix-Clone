package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

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
    private ComboBox<String> role_typeComboBox;
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
        role_typeComboBox.setItems(FXCollections.observableArrayList("Main Actor", "Secondary Actor"));

        roleFilmTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Role_film selectedRoleFilm = roleFilmTableView.getSelectionModel().getSelectedItem();
                id_acteurTextField.setText(String.valueOf(selectedRoleFilm.getId_acteur()));
                id_filmTextField.setText(String.valueOf(selectedRoleFilm.getId_film()));
                role_typeComboBox.setValue(selectedRoleFilm.getRole_type()); 
                url_imageTextField.setText(selectedRoleFilm.getUrl_image());
            } else {
                clearForm();
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
                    role_typeComboBox.getValue(),
                    url_imageTextField.getText());
            System.out.println(roleFilm.toString());
            DatabaseUtil.addRoleFilm(roleFilm);
            roleFilmList.add(roleFilm);
        } catch (SQLException e) {
        	showErrorDialog("Error adding Role_film" );
        }
    }

    @FXML
    private void handleUpdateAction(ActionEvent event) throws SQLException {
        Role_film selectedRoleFilm = roleFilmTableView.getSelectionModel().getSelectedItem();
        if (selectedRoleFilm != null) {
                Role_film old_film = new Role_film();
                old_film.setId_acteur(selectedRoleFilm.getId_acteur());
                old_film.setId_film(selectedRoleFilm.getId_film());
                old_film.setRole_type(selectedRoleFilm.getRole_type());
                old_film.setUrl_image(selectedRoleFilm.getUrl_image());
                selectedRoleFilm.setId_acteur(Integer.parseInt(id_acteurTextField.getText()));
                selectedRoleFilm.setId_film(Integer.parseInt(id_filmTextField.getText()));
                selectedRoleFilm.setRole_type(role_typeComboBox.getValue());
                selectedRoleFilm.setUrl_image(url_imageTextField.getText());
                System.out.println("Updating roleFilm: " + selectedRoleFilm.toString());
                DatabaseUtil.updateRoleFilm(selectedRoleFilm,old_film);
                roleFilmTableView.refresh();
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
            	showErrorDialog("Error deleting Role_film");
            }
        } else {
        	showErrorDialog("No Role_film Selected Please select a Role_film to delete.");
        }
    }

    private void clearForm() {
        id_acteurTextField.clear();
        id_filmTextField.clear();
        url_imageTextField.clear();
    }

    private void showErrorDialog(String message) {
 		Alert alert = new Alert(AlertType.ERROR);
 		alert.setTitle("Error");
 		alert.setHeaderText(null);
 		alert.setContentText(message);
 		alert.showAndWait();
 	}
}
