package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.fsb.FlixFlow.Models.Film;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FilmAddController implements Initializable {

	@FXML
	private TableView<Film> filmTable;

	private ObservableList<Film> films;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			films = FXCollections.observableArrayList(org.fsb.FlixFlow.Utilities.DatabaseUtil.getAllFilms());
		} catch (SQLException e) {
			e.printStackTrace();
			films = FXCollections.observableArrayList();
		}

		filmTable.setItems(films);

	}

	@FXML
	private void addFilm() {

		Film newFilm = new Film();

		try {
			org.fsb.FlixFlow.Utilities.DatabaseUtil.createFilm(newFilm);
			films.add(newFilm);
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	@FXML
	private void updateFilm() {
		Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
		if (selectedFilm != null) {

			try {
				org.fsb.FlixFlow.Utilities.DatabaseUtil.updateFilm(selectedFilm);
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	@FXML
	private void deleteFilm() {
		Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
		if (selectedFilm != null) {
			try {
				org.fsb.FlixFlow.Utilities.DatabaseUtil.deleteFilm(selectedFilm.getId_film());
				films.remove(selectedFilm);
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}
}
