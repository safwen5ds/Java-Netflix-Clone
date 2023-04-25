package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;

import org.fsb.FlixFlow.Models.Role_serie;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

public class Role_SerieAddController {
    @FXML
    private TableView<Role_serie> roleSerieTableView;
    @FXML
    private TableColumn<Role_serie, Integer> id_acteurColumn;
    @FXML
    private TableColumn<Role_serie, Integer> id_saisonColumn;
    @FXML
    private TableColumn<Role_serie, String> role_typeColumn;
    @FXML
    private TableColumn<Role_serie, String> url_imageColumn;
    @FXML
    private TextField id_acteurTextField;
    @FXML
    private TextField id_saisonTextField;
    @FXML
    private ComboBox<String> role_typeComboBox;
    @FXML
    private TextField url_imageTextField;
    @FXML
    private TableColumn<Role_serie, Integer> id_serieColumn;
    @FXML
    private TextField id_serieTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Role_serie> roleSerieList;

    @FXML
    public void initialize() {
        id_acteurColumn.setCellValueFactory(new PropertyValueFactory<>("id_acteur"));
        id_serieColumn.setCellValueFactory(new PropertyValueFactory<>("id_serie"));
        id_saisonColumn.setCellValueFactory(new PropertyValueFactory<>("id_saison"));
        role_typeColumn.setCellValueFactory(new PropertyValueFactory<>("role_type"));
        url_imageColumn.setCellValueFactory(new PropertyValueFactory<>("url_image"));
        
      
        roleSerieList = FXCollections.observableArrayList();
        roleSerieTableView.setItems(roleSerieList);
        role_typeComboBox.setItems(FXCollections.observableArrayList("Main Actor", "Secondary Actor"));

        roleSerieTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Role_serie selectedRoleSerie = roleSerieTableView.getSelectionModel().getSelectedItem();
                id_acteurTextField.setText(String.valueOf(selectedRoleSerie.getId_acteur()));
                id_serieTextField.setText(String.valueOf(selectedRoleSerie.getId_serie()));
                id_saisonTextField.setText(String.valueOf(selectedRoleSerie.getId_saison()));
                url_imageTextField.setText(selectedRoleSerie.getUrl_image());

            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            ObservableList<Role_serie> roleSeries = FXCollections.observableArrayList(DatabaseUtil.getAllRoleSeries());
            roleSerieList.setAll(roleSeries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) throws SQLException {
    
             Role_serie roleSerie = new Role_serie(Integer.parseInt(id_acteurTextField.getText()),
                     Integer.parseInt(id_serieTextField.getText()), 
                     Integer.parseInt(id_saisonTextField.getText()), role_typeComboBox.getValue(),
                     url_imageTextField.getText());
             DatabaseUtil.addRoleSerie(roleSerie);
             roleSerieList.add(roleSerie);
        
    }


    @FXML
    private void handleUpdateAction(ActionEvent event) {
    	Role_serie selectedRoleSerie = roleSerieTableView.getSelectionModel().getSelectedItem();
        if (selectedRoleSerie != null) {
            try {
                selectedRoleSerie.setId_acteur(Integer.parseInt(id_acteurTextField.getText()));
                selectedRoleSerie.setId_serie(Integer.parseInt(id_serieTextField.getText()));
                selectedRoleSerie.setId_saison(Integer.parseInt(id_saisonTextField.getText()));
                selectedRoleSerie.setRole_type(role_typeComboBox.getValue());
                selectedRoleSerie.setUrl_image(url_imageTextField.getText());
                DatabaseUtil.updateRoleSerie(selectedRoleSerie);
                roleSerieTableView.refresh();
            } catch (SQLException e) {
            	showErrorDialog("Error updating Role_serie "+e);
            }
        } else {
        	showErrorDialog("No Role_serie Selected Please select a Role_serie to update !");
        }
   }
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Role_serie selectedRoleSerie = roleSerieTableView.getSelectionModel().getSelectedItem();
        if (selectedRoleSerie != null) {
            try {
                DatabaseUtil.deleteRoleSerie(selectedRoleSerie);
                roleSerieList.remove(selectedRoleSerie);
            } catch (SQLException e) {
            	showErrorDialog("Error deleting Role_serie " );
            }
        } else {
        	showErrorDialog("No Role_serie Selected Please select a Role_serie to delete.");
        }
    }



    private void showErrorDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

   
}
