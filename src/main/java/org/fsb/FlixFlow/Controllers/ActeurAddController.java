package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fsb.FlixFlow.Models.Acteur;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Comparator;



public class ActeurAddController {

	@FXML
	private TextField idField;
	@FXML
	private TextField nameField;
	@FXML
	private Button addButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
	private TableView<Acteur> acteurTable;
	@FXML
	private TableColumn<Acteur, Integer> idColumn;
	@FXML
	private TableColumn<Acteur, String> nameColumn;

	private ObservableList<Acteur> acteurs;

	@FXML
	public void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id_acteur"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

		acteurs = FXCollections.observableArrayList(DatabaseUtil.getAllActeurs());
		acteurTable.setItems(acteurs);
	}

	@FXML
	private void addActeur() {
	    int id = Integer.parseInt(idField.getText());
	    String name = nameField.getText();

	    Acteur newActeur = new Acteur(id, name);
	    if (DatabaseUtil.insertActeur(newActeur)) {
	        acteurs.add(newActeur);
	        acteurs.sort(Comparator.comparing(Acteur::getId_acteur)); 
	    } else {
	        showAlert("Erreur add acteur ! ");
	    }
	}

	private void refreshTable() {
	    acteurs.clear();
	    acteurs.addAll(DatabaseUtil.getAllActeurs());
	    acteurs.sort(Comparator.comparing(Acteur::getId_acteur)); 
	    acteurTable.refresh();
	}



	@FXML
	private void updateActeur() {
	    Acteur selectedActeur = acteurTable.getSelectionModel().getSelectedItem();

	    if (selectedActeur != null) {
	        int id = Integer.parseInt(idField.getText());
	        String name = nameField.getText();

	        Acteur updatedActeur = new Acteur(id, name);
	        if (DatabaseUtil.updateActeur(updatedActeur)) {
	            selectedActeur.setId_acteur(id);
	            selectedActeur.setNom(name);
	            refreshTable(); 
	        } else {
	        	showAlert("Erreur update acteur ! ");
	        }
	    }
	}




	@FXML
	private void deleteActeur() {
		Acteur selectedActeur = acteurTable.getSelectionModel().getSelectedItem();

		if (selectedActeur != null) {
			if (DatabaseUtil.deleteActeur(selectedActeur.getId_acteur())) {
				acteurs.remove(selectedActeur);
			} else {
				showAlert("Erreur delete acteur ! ");
			}
		}
	}
	
	private void showAlert(String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}