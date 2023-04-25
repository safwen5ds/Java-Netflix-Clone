package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

public class FavController {
	@FXML
	private TextField actorSearch, filmSearch, seriesSearch, genreSearch;

	@FXML
	private ListView<String> actorsList, filmsList, seriesList, genresList;

	private int userId;

	@FXML
	private void initialize() {
		userId = DatabaseUtil.readUserFromFile().getId_utilisateur();

		loadActors();
		loadFilms();
		loadSeries();
		loadGenres();

		actorSearch.textProperty().addListener((observable, oldValue, newValue) -> filterActors(newValue));
		filmSearch.textProperty().addListener((observable, oldValue, newValue) -> filterFilms(newValue));
		seriesSearch.textProperty().addListener((observable, oldValue, newValue) -> filterSeries(newValue));
		genreSearch.textProperty().addListener((observable, oldValue, newValue) -> filterGenres(newValue));
	}

	private void loadActors() {
		actorsList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_acteur JOIN acteur ON preferences_acteur.id_acteur=acteur.id_acteur WHERE preferences_acteur.id_utilisateur = "
						+ userId));
	}

	private void loadFilms() {
		filmsList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_film JOIN film ON preferences_film.id_film=film.id_film WHERE preferences_film.id_utilisateur = "
						+ userId));
	}

	private void loadSeries() {
		seriesList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_serie JOIN serie ON preferences_serie.id_serie=serie.id_serie WHERE preferences_serie.id_utilisateur = "
						+ userId));
	}

	private void loadGenres() {
		genresList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_genre JOIN genre ON preferences_genre.id_genre=genre.id_genre WHERE preferences_genre.id_utilisateur = "
						+ userId));
	}

	private void filterActors(String search) {
		actorsList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_acteur JOIN acteur ON preferences_acteur.id_acteur=acteur.id_acteur WHERE preferences_acteur.id_utilisateur = "
						+ userId + " AND LOWER(nom) LIKE '%" + search.toLowerCase() + "%'"));
	}

	private void filterFilms(String search) {
		filmsList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_film JOIN film ON preferences_film.id_film=film.id_film WHERE preferences_film.id_utilisateur = "
						+ userId + " AND LOWER(nom) LIKE '%" + search.toLowerCase() + "%'"));
	}

	private void filterSeries(String search) {
		seriesList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_serie JOIN serie ON preferences_serie.id_serie=serie.id_serie WHERE preferences_serie.id_utilisateur = "
						+ userId + " AND LOWER(nom) LIKE '%" + search.toLowerCase() + "%'"));
	}

	private void filterGenres(String search) {
		genresList.setItems(DatabaseUtil.getData(
				"SELECT nom FROM preferences_genre JOIN genre ON preferences_genre.id_genre=genre.id_genre WHERE preferences_genre.id_utilisateur = "
						+ userId + " AND LOWER(nom) LIKE '%" + search.toLowerCase() + "%'"));
	}
}
