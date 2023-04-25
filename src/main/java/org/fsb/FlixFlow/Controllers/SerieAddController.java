package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Comparator;

public class SerieAddController {
	@FXML
	private TextField idTextField;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField releaseYearTextField;
	@FXML
	private TextField imageUrlTextField;
	@FXML
	private TextField videoUrlTextField;
	@FXML
	private TextField genreIdTextField;
	@FXML
	private TextField languageIdTextField;
	@FXML
	private TextField countryIdTextField;
	@FXML
	private TextField producerIdTextField;
	@FXML
	private TextField synopsisTextField;
	@FXML
	private TableView<Serie> serieTableView;
	@FXML
	private TableColumn<Serie, Integer> idColumn;
	@FXML
	private TableColumn<Serie, String> NameColumn;
	@FXML
	private TableColumn<Serie, Integer> ReleaseYearColumn;
	@FXML
	private TableColumn<Serie, String> Image_URLColumn;
	@FXML
	private TableColumn<Serie, String> Video_URLColumn;
	@FXML
	private TableColumn<Serie, Integer> Genre_IDColumn;
	@FXML
	private TableColumn<Serie, Integer> Language_IDColumn;
	@FXML
	private TableColumn<Serie, Integer> Country_IDColumn;
	@FXML
	private TableColumn<Serie, Integer> Producer_IDColumn;
	@FXML
	private TableColumn<Serie, String> SynopsisColumn;

	public void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id_serie"));
		NameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		ReleaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("annee_sortie"));
		Image_URLColumn.setCellValueFactory(new PropertyValueFactory<>("url_image"));
		Video_URLColumn.setCellValueFactory(new PropertyValueFactory<>("url_video"));
		Genre_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_genre"));
		Language_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_langue"));
		Country_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_pays_origine"));
		Producer_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_producteur"));
		SynopsisColumn.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
		serieTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Serie selectedSerie = serieTableView.getSelectionModel().getSelectedItem();
				idTextField.setText(String.valueOf(selectedSerie.getId_serie()));
				nameTextField.setText(selectedSerie.getNom());
				releaseYearTextField.setText(String.valueOf(selectedSerie.getAnnee_sortie()));
				imageUrlTextField.setText(selectedSerie.getUrl_image());
				videoUrlTextField.setText(selectedSerie.getUrl_video());
				genreIdTextField.setText(String.valueOf(selectedSerie.getId_genre()));
				languageIdTextField.setText(String.valueOf(selectedSerie.getId_langue()));
				countryIdTextField.setText(String.valueOf(selectedSerie.getId_pays_origine()));
				producerIdTextField.setText(String.valueOf(selectedSerie.getId_producteur()));
				synopsisTextField.setText(selectedSerie.getSynopsis());
			}
		});

		refreshTable();
	}

	private void refreshTable() {
		try {
			ObservableList<Serie> series = FXCollections.observableArrayList(DatabaseUtil.getAllSeries());
			System.out.println("Series data: " + series);
			series.sort(Comparator.comparing(Serie::getId_serie));
			serieTableView.setItems(series);
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error When Refreshing Table");
		}
	}

	@FXML
	private void createSerie() {
		try {
			Serie serie = new Serie();
			if (idTextField.getText().matches("\\d+")) {
				int id = Integer.parseInt(idTextField.getText());
				serie.setId_serie(id);
			} else {
				System.out.println(" Not Cool !");
			}

			serie.setNom(nameTextField.getText());
			serie.setAnnee_sortie(Integer.parseInt(releaseYearTextField.getText()));
			serie.setUrl_image(imageUrlTextField.getText());
			serie.setUrl_video(videoUrlTextField.getText());
			serie.setId_genre(Integer.parseInt(genreIdTextField.getText()));
			serie.setVues(0);
			serie.setId_langue(Integer.parseInt(languageIdTextField.getText()));
			serie.setId_pays_origine(Integer.parseInt(countryIdTextField.getText()));
			serie.setId_producteur(Integer.parseInt(producerIdTextField.getText()));
			serie.setSynopsis(synopsisTextField.getText());

			DatabaseUtil.addSerie(serie);
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Creating Serie ! ");
		} catch (NumberFormatException e) {
			showErrorDialog("Invalid input. Please check your input fields and try again !");
		}
		refreshTable();
	}

	@FXML
	private void updateSerie() {
		try {
			Serie serie = new Serie();
			serie.setId_serie(Integer.parseInt(idTextField.getText()));
			serie.setNom(nameTextField.getText());
			serie.setAnnee_sortie(Integer.parseInt(releaseYearTextField.getText()));
			serie.setUrl_image(imageUrlTextField.getText());
			serie.setUrl_video(videoUrlTextField.getText());
			serie.setId_genre(Integer.parseInt(genreIdTextField.getText()));
			serie.setId_langue(Integer.parseInt(languageIdTextField.getText()));
			serie.setId_pays_origine(Integer.parseInt(countryIdTextField.getText()));
			serie.setId_producteur(Integer.parseInt(producerIdTextField.getText()));
			serie.setSynopsis(synopsisTextField.getText());

			DatabaseUtil.updateSerie(serie);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		refreshTable();
	}

	@FXML
	private void deleteSerie() {
		try {
			int id = Integer.parseInt(idTextField.getText());
			DatabaseUtil.deleteSerie(id);
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Deleting Serie");
		}
		refreshTable();
	}

	private void showErrorDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
