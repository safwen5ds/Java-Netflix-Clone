package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.fsb.FlixFlow.Models.Acteur;
import org.fsb.FlixFlow.Utilities.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ActeurAddController  {

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
	        } else {

	        }
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
	            } else {

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

	            }
	        }
	    }
}