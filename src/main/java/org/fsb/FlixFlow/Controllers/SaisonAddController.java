package org.fsb.FlixFlow.Controllers;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;

import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SaisonAddController {
	@FXML
	private TableColumn<Saison, Date> dateDebutColumn;
	@FXML
	private DatePicker dateDebutPicker;
	@FXML
	private TableColumn<Saison, Integer> idSaisonColumn;
	@FXML
	private TextField idSaisonField;
	@FXML
	private TableColumn<Saison, Integer> idSerieColumn;
	@FXML
	private TextField idSerieField;
	@FXML
	private TableColumn<Saison, Integer> numSaisonColumn;
	@FXML
	private TextField numSaisonField;
	@FXML
	private TableView<Saison> seasonTable;
	@FXML
	private TableColumn<Saison, String> synopsisColumn;
	@FXML
	private TextField synopsisField;
	@FXML
	private TableColumn<Saison, String> urlImageColumn;
	@FXML
	private TextField urlImageField;
	@FXML
	private TableColumn<Saison, String> urlVideoColumn;
	@FXML
	private TextField urlVideoField;

	@FXML
	private void addSeason() {
		try {
			Saison season = new Saison();
			season.setId_saison(Integer.parseInt(idSaisonField.getText()));
			season.setId_serie(Integer.parseInt(idSerieField.getText()));
			season.setNum_saison(Integer.parseInt(numSaisonField.getText()));
			season.setDate_debut(java.sql.Date.valueOf(dateDebutPicker.getValue()));
			season.setSynopsis(synopsisField.getText());
			season.setUrl_image(urlImageField.getText());
			season.setUrl_video(urlVideoField.getText());

			DatabaseUtil.addSaison(season);
			refreshTable();
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Adding Season ! ");
		} catch (NumberFormatException e) {
			showErrorDialog("Invalid input. Please check your input fields and try again.");
		}
	}

	@FXML
	private void deleteSeason() {
		try {
			int idSaison = Integer.parseInt(idSaisonField.getText());
			DatabaseUtil.deleteSaison(idSaison);
			refreshTable();
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Deleting Season ! ");
		} catch (NumberFormatException e) {
			showErrorDialog("Invalid input. Please check your input fields and try again !");
		}
	}

	public void initialize() {
		idSaisonColumn.setCellValueFactory(new PropertyValueFactory<>("id_saison"));
		idSerieColumn.setCellValueFactory(new PropertyValueFactory<>("id_serie"));
		numSaisonColumn.setCellValueFactory(new PropertyValueFactory<>("num_saison"));
		dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
		synopsisColumn.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
		urlImageColumn.setCellValueFactory(new PropertyValueFactory<>("url_image"));
		urlVideoColumn.setCellValueFactory(new PropertyValueFactory<>("url_video"));

		seasonTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Saison selectedSeason = seasonTable.getSelectionModel().getSelectedItem();
				idSaisonField.setText(String.valueOf(selectedSeason.getId_saison()));
				idSerieField.setText(String.valueOf(selectedSeason.getId_serie()));
				numSaisonField.setText(String.valueOf(selectedSeason.getNum_saison()));
				dateDebutPicker.setValue(((java.sql.Date) selectedSeason.getDate_debut()).toLocalDate());
				synopsisField.setText(selectedSeason.getSynopsis());
				urlImageField.setText(selectedSeason.getUrl_image());
				urlVideoField.setText(selectedSeason.getUrl_video());
			}
		});

		refreshTable();
	}

	private void refreshTable() {
		try {
			ObservableList<Saison> seasons = FXCollections.observableArrayList(DatabaseUtil.getAllSeasons());
			seasons.sort(Comparator.comparing(Saison::getId_saison));
			seasonTable.setItems(seasons);
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error When Refreshing Table ! ");
		}
	}

	private void showErrorDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	private void updateSeason() {
		try {
			Saison season = new Saison();
			season.setId_saison(Integer.parseInt(idSaisonField.getText()));
			season.setId_serie(Integer.parseInt(idSerieField.getText()));
			season.setNum_saison(Integer.parseInt(numSaisonField.getText()));
			season.setDate_debut(java.sql.Date.valueOf(dateDebutPicker.getValue()));
			season.setSynopsis(synopsisField.getText());
			season.setUrl_image(urlImageField.getText());
			season.setUrl_video(urlVideoField.getText());

			DatabaseUtil.updateSaison(season);
			refreshTable();
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Updating Season ! ");
		} catch (NumberFormatException e) {
			showErrorDialog("Invalid input. Please check your input fields and try again !");
		}
	}
}
