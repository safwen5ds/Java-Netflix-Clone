package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.fsb.FlixFlow.Models.Role_serie;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.sql.SQLException;

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
	private TextField id_acteurTextField;
	@FXML
	private TextField id_saisonTextField;
	@FXML
	private TextField role_typeTextField;
	@FXML
	private Button addButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;

	private ObservableList<Role_serie> roleSerieList;

	@FXML
	public void initialize() {
		roleSerieList = FXCollections.observableArrayList();
		roleSerieTableView.setItems(roleSerieList);
		id_acteurColumn.setCellValueFactory(cellData -> cellData.getValue().id_acteurProperty().asObject());
		id_saisonColumn.setCellValueFactory(cellData -> cellData.getValue().id_saisonProperty().asObject());
		role_typeColumn.setCellValueFactory(cellData -> cellData.getValue().role_typeProperty());
	}

	@FXML
	private void handleAddAction(ActionEvent event) {
		try {
			Role_serie roleSerie = new Role_serie(Integer.parseInt(id_acteurTextField.getText()),
					Integer.parseInt(id_saisonTextField.getText()), role_typeTextField.getText());
			DatabaseUtil.addRoleSerie(roleSerie);
			roleSerieList.add(roleSerie);
			clearForm();
		} catch (SQLException e) {
			showErrorAlert("Error adding Role_serie", e);
		}
	}

	@FXML
	private void handleUpdateAction(ActionEvent event) {
		Role_serie selectedRoleSerie = roleSerieTableView.getSelectionModel().getSelectedItem();
		if (selectedRoleSerie != null) {
			try {
				selectedRoleSerie.setId_acteur(Integer.parseInt(id_acteurTextField.getText()));
				selectedRoleSerie.setId_saison(Integer.parseInt(id_saisonTextField.getText()));
				selectedRoleSerie.setRole_type(role_typeTextField.getText());
				DatabaseUtil.updateRoleSerie(selectedRoleSerie);
				roleSerieTableView.refresh();
				clearForm();
			} catch (SQLException e) {
				showErrorAlert("Error updating Role_serie", e);
			}
		}
	}

	@FXML
	private void handleDeleteAction(ActionEvent event) {
		Role_serie selectedRoleSerie = roleSerieTableView.getSelectionModel().getSelectedItem();
		if (selectedRoleSerie != null) {
			try {
				DatabaseUtil.deleteRoleSerie(selectedRoleSerie.getId_acteur(), selectedRoleSerie.getId_saison());
				roleSerieList.remove(selectedRoleSerie);
				clearForm();
			} catch (SQLException e) {
				showErrorAlert("Error deleting Role_serie", e);
			}
		}
	}

	private void clearForm() {
		id_acteurTextField.clear();
		id_saisonTextField.clear();
		role_typeTextField.clear();
	}

	private void showErrorAlert(String headerText, Throwable e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(headerText);
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
}
