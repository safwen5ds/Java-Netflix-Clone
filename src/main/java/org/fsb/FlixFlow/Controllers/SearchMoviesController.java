package org.fsb.FlixFlow.Controllers;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.fsb.FlixFlow.Models.Acteur;
import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import org.fsb.FlixFlow.Views.PageNavigationUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

public class SearchMoviesController {
    @FXML
    private TextField movieTitleSearchTextField;

    @FXML
    private TextField releaseYearSearchTextField;

    @FXML
    private TextField genreSearchTextField;

    @FXML
    private TextField languageSearchTextField;

    @FXML
    private TextField countryOfOriginSearchTextField;

    @FXML
    private TextField producerSearchTextField;

    @FXML
    private TextField actorSearchTextField;

    @FXML
    private FlowPane moviesFlowPane;
    private UserDashboardController userDashboardController;

    private ObservableList<Film> masterData;

    public SearchMoviesController(UserDashboardController userDashboardController) {
        this.userDashboardController = userDashboardController;
    }

    @FXML
    public void initialize() {
        loadAllMovies();
        setupSearchFieldListeners();
    }

    private void loadAllMovies() {
        try {
            List<Film> films = DatabaseUtil.getMoviesSortedByViews();
            masterData = FXCollections.observableArrayList(films);
            updateMoviesFlowPane(masterData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupSearchFieldListeners() {
        movieTitleSearchTextField.setOnKeyReleased(event -> searchMovies());
        releaseYearSearchTextField.setOnKeyReleased(event -> searchMovies());
        genreSearchTextField.setOnKeyReleased(event -> searchMovies());
        languageSearchTextField.setOnKeyReleased(event -> searchMovies());
        countryOfOriginSearchTextField.setOnKeyReleased(event -> searchMovies());
        producerSearchTextField.setOnKeyReleased(event -> searchMovies());
        actorSearchTextField.setOnKeyReleased(event -> searchMovies());
    }

    private void searchMovies() {
        String movieTitleFilter = movieTitleSearchTextField.getText().toLowerCase();
        String releaseYearFilter = releaseYearSearchTextField.getText().toLowerCase();
        String genreFilter = genreSearchTextField.getText().toLowerCase();
        String languageFilter = languageSearchTextField.getText().toLowerCase();
        String countryOfOriginFilter = countryOfOriginSearchTextField.getText().toLowerCase();
        String producerFilter = producerSearchTextField.getText().toLowerCase();
        String actorFilter = actorSearchTextField.getText().toLowerCase();

        List<Film> filteredData = masterData.stream()
            .filter(film -> film.getNom().toLowerCase().contains(movieTitleFilter))
            .filter(film -> String.valueOf(film.getAnnee_sortie()).contains(releaseYearFilter))
            .filter(film -> film.getNom_genre().toLowerCase().contains(genreFilter))
            .filter(film -> film.getNom_langue().toLowerCase().contains(languageFilter))
            .filter(film -> film.getNom_pays().toLowerCase().contains(countryOfOriginFilter))
            .filter(film -> film.getNom_producteur().toLowerCase().contains(producerFilter))
            .filter(film -> {
                try {
                    List<Acteur> actors = DatabaseUtil.getActorsByFilmId(film.getId_film());
                    return actors.stream()
                        .anyMatch(actor -> actor.getNom().toLowerCase().contains(actorFilter));
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            })
            .collect(Collectors.toList());

        updateMoviesFlowPane(FXCollections.observableArrayList(filteredData));
    }

}