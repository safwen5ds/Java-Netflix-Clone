package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SaisonAddController implements Initializable {

	@FXML
	private TableView<Saison> seasonTable;

	private ObservableList<Saison> seasons;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			seasons = FXCollections.observableArrayList(DatabaseUtil.getAllSeasons());
		} catch (SQLException e) {
			e.printStackTrace();
			seasons = FXCollections.observableArrayList();
		}

		seasonTable.setItems(seasons);

	}

	@FXML
	private void addSeason() {

		Saison newSeason = new Saison(/* Add constructor parameters based on user input */);

		try {
			DatabaseUtil.addSaison(newSeason);
			seasons.add(newSeason);
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	@FXML
	private void updateSeason() {
		Saison selectedSeason = seasonTable.getSelectionModel().getSelectedItem();
		if (selectedSeason != null) {

			try {
				DatabaseUtil.updateSaison(selectedSeason);
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	@FXML
	private void deleteSeason() {
		Saison selectedSeason = seasonTable.getSelectionModel().getSelectedItem();
		if (selectedSeason != null) {
			try {
				DatabaseUtil.deleteSaison(selectedSeason.getId_saison());
				seasons.remove(selectedSeason);
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}
}
