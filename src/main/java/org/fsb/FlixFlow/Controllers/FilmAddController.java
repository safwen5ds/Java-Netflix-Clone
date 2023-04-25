package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.SQLException;
import java.util.Comparator;

public class FilmAddController {
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
	private TextField FilmUrlTextFiled;
	@FXML
    private TableView<Film> FilmTableView;
    @FXML
    private TableColumn<Film, Integer> idColumn;
    @FXML
    private TableColumn<Film, String> NameColumn;
    @FXML
    private TableColumn<Film, Integer> ReleaseYearColumn;
    @FXML
    private TableColumn<Film, String> Image_URLColumn;
    @FXML
    private TableColumn<Film, String> Video_URLColumn;
    @FXML
    private TableColumn<Film, Integer> Genre_IDColumn;
    @FXML
    private TableColumn<Film, Integer> Language_IDColumn;
    @FXML
    private TableColumn<Film, Integer> Country_IDColumn;
    @FXML
    private TableColumn<Film, Integer> Producer_IDColumn;
    @FXML
    private TableColumn<Film, String> SynopsisColumn;
    @FXML
    private TableColumn<Film, String> FilmUrlColumn;
    
    public void initialize() {
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id_film"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ReleaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("annee_sortie"));
        Image_URLColumn.setCellValueFactory(new PropertyValueFactory<>("url_image")); 
        Video_URLColumn.setCellValueFactory(new PropertyValueFactory<>("url_video")); 
        Genre_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_genre"));
        Language_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_langue"));
        Country_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_pays_origine"));
        Producer_IDColumn.setCellValueFactory(new PropertyValueFactory<>("id_producteur"));
        SynopsisColumn.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
        FilmUrlColumn.setCellValueFactory(new PropertyValueFactory<>("url_film"));
        FilmTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Film selectedFilm = FilmTableView.getSelectionModel().getSelectedItem();
                idTextField.setText(String.valueOf(selectedFilm.getId_film()));
                nameTextField.setText(selectedFilm.getNom());
                releaseYearTextField.setText(String.valueOf(selectedFilm.getAnnee_sortie()));
                imageUrlTextField.setText(selectedFilm.getUrl_image());
                videoUrlTextField.setText(selectedFilm.getUrl_video());
                genreIdTextField.setText(String.valueOf(selectedFilm.getId_genre()));
                languageIdTextField.setText(String.valueOf(selectedFilm.getId_langue()));
                countryIdTextField.setText(String.valueOf(selectedFilm.getId_pays_origine()));
                producerIdTextField.setText(String.valueOf(selectedFilm.getId_producteur()));
                synopsisTextField.setText(selectedFilm.getSynopsis());
                FilmUrlTextFiled.setText(selectedFilm.getUrl_film());
            }
        });

        refreshTable();
    }
    private void refreshTable() {
        try {
            ObservableList<Film> Films = FXCollections.observableArrayList(DatabaseUtil.getAllFilms());
            System.out.println("Films data: " + Films);
            Films.sort(Comparator.comparing(Film::getId_film));
            FilmTableView.setItems(Films);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorDialog("Error When Refreshing Table !");
        }
    }


    @FXML
    private void createFilm() {
        try {
            Film Film = new Film();
            if (idTextField.getText().matches("\\d+")) { 
                int id = Integer.parseInt(idTextField.getText());
                Film.setId_film(id);
            } else {
                System.out.println(" Not Cool !");
            }
         
            Film.setNom(nameTextField.getText());
            Film.setAnnee_sortie(Integer.parseInt(releaseYearTextField.getText()));
            Film.setUrl_image(imageUrlTextField.getText());
            Film.setUrl_video(videoUrlTextField.getText());
            Film.setId_genre(Integer.parseInt(genreIdTextField.getText()));
            Film.setVues(0);
            Film.setId_langue(Integer.parseInt(languageIdTextField.getText()));
            Film.setId_pays_origine(Integer.parseInt(countryIdTextField.getText()));
            Film.setId_producteur(Integer.parseInt(producerIdTextField.getText()));
            Film.setSynopsis(synopsisTextField.getText());
            Film.setUrl_film(FilmUrlTextFiled.getText());

            DatabaseUtil.createFilm(Film);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorDialog("Error Creating Film");
        } catch (NumberFormatException e) {
        	showErrorDialog("Invalid input. Please check your input fields and try again !");
            
        }
        refreshTable();
    }


	

	@FXML
	private void updateFilm() {
		try {
			Film Film = new Film();
			Film.setId_film(Integer.parseInt(idTextField.getText()));
			Film.setNom(nameTextField.getText());
			Film.setAnnee_sortie(Integer.parseInt(releaseYearTextField.getText()));
			Film.setUrl_image(imageUrlTextField.getText());
			Film.setUrl_video(videoUrlTextField.getText());
			Film.setId_genre(Integer.parseInt(genreIdTextField.getText()));
			Film.setId_langue(Integer.parseInt(languageIdTextField.getText()));
			Film.setId_pays_origine(Integer.parseInt(countryIdTextField.getText()));
			Film.setId_producteur(Integer.parseInt(producerIdTextField.getText()));
			Film.setSynopsis(synopsisTextField.getText());
			Film.setUrl_film(FilmUrlTextFiled.getText());

			DatabaseUtil.updateFilm(Film);
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Updating Movie !");
		}
		refreshTable();
	}

	@FXML
	private void deleteFilm() {
		try {
			int id = Integer.parseInt(idTextField.getText());
			DatabaseUtil.deleteFilm(id);
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Deleting Movie !");
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

