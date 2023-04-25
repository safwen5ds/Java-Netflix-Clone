package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.fsb.FlixFlow.Models.*;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import java.util.Date;

import java.sql.SQLException;
import java.util.Comparator;
import javafx.scene.control.cell.PropertyValueFactory;

public class SaisonAddController {
    @FXML
    private TextField idSaisonField;
    @FXML
    private TextField idSerieField;
    @FXML
    private TextField numSaisonField;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private TextField synopsisField;
    @FXML
    private TextField urlImageField;
    @FXML
    private TextField urlVideoField;
    @FXML
    private TableView<Saison> seasonTable;
    @FXML
    private TableColumn<Saison, Integer> idSaisonColumn;
    @FXML
    private TableColumn<Saison, Integer> idSerieColumn;
    @FXML
    private TableColumn<Saison, Integer> numSaisonColumn;
    @FXML
    private TableColumn<Saison, Date> dateDebutColumn;
    @FXML
    private TableColumn<Saison, String> synopsisColumn;
    @FXML
    private TableColumn<Saison, String> urlImageColumn;
    @FXML
    private TableColumn<Saison, String> urlVideoColumn;


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
        }
    }

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
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please check your input fields and try again.");
        }
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
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please check your input fields and try again.");
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
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please check your input fields and try again.");
        }
    }
}


