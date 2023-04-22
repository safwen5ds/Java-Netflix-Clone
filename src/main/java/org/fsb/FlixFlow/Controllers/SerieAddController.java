package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.sql.SQLException;

public class SerieAddController {
	@FXML
	private TextField idTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField releaseYearTextField;

	@FXML
	private void createSerie() {
		try {
			Serie serie = new Serie();
			serie.setNom(nameTextField.getText());
			serie.setAnnee_sortie(Integer.parseInt(releaseYearTextField.getText()));

			DatabaseUtil.addSerie(serie);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void readSerie() {
		try {
			int id = Integer.parseInt(idTextField.getText());
			Serie serie = DatabaseUtil.getSerie(id);

			if (serie != null) {
				nameTextField.setText(serie.getNom());
				releaseYearTextField.setText(String.valueOf(serie.getAnnee_sortie()));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void updateSerie() {
		try {
			Serie serie = new Serie();
			serie.setId_serie(Integer.parseInt(idTextField.getText()));
			serie.setNom(nameTextField.getText());
			serie.setAnnee_sortie(Integer.parseInt(releaseYearTextField.getText()));

			DatabaseUtil.updateSerie(serie);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteSerie() {
		try {
			int id = Integer.parseInt(idTextField.getText());
			DatabaseUtil.deleteSerie(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}