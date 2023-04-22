package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fsb.FlixFlow.Models.Producteur;

public class ProducteurAddController {
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
	private TableView<Producteur> producteurTable;
	@FXML
	private TableColumn<Producteur, Integer> idColumn;
	@FXML
	private TableColumn<Producteur, String> nameColumn;

	private ObservableList<Producteur> producteurs;

	@FXML
	public void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id_producteur"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

		producteurs = FXCollections.observableArrayList(org.fsb.FlixFlow.Utilities.DatabaseUtil.getAllProducteurs());
		producteurTable.setItems(producteurs);
	}

	@FXML
	private void addProducteur() {
		int id = Integer.parseInt(idField.getText());
		String name = nameField.getText();

		Producteur newProducteur = new Producteur(id, name);
		if (org.fsb.FlixFlow.Utilities.DatabaseUtil.insertProducteur(newProducteur)) {
			producteurs.add(newProducteur);
		} else {

		}
	}

	@FXML
	private void updateProducteur() {
		Producteur selectedProducteur = producteurTable.getSelectionModel().getSelectedItem();

		if (selectedProducteur != null) {
			int id = Integer.parseInt(idField.getText());
			String name = nameField.getText();

			Producteur updatedProducteur = new Producteur(id, name);
			if (org.fsb.FlixFlow.Utilities.DatabaseUtil.updateProducteur(updatedProducteur)) {
				selectedProducteur.setId_producteur(id);
				selectedProducteur.setNom(name);
			} else {

			}
		}
	}

	@FXML
	private void deleteProducteur() {
		Producteur selectedProducteur = producteurTable.getSelectionModel().getSelectedItem();

		if (selectedProducteur != null) {
			if (org.fsb.FlixFlow.Utilities.DatabaseUtil.deleteProducteur(selectedProducteur.getId_producteur())) {
				producteurs.remove(selectedProducteur);
			} else {

			}
		}
	}
}
