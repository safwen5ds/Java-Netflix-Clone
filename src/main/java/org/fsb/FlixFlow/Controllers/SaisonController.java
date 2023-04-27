package org.fsb.FlixFlow.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SaisonController {
	private final IntegerProperty mediaIdProperty = new SimpleIntegerProperty();

	@FXML
	private VBox seasonsContainer;
	private final IntegerProperty serieIdProperty = new SimpleIntegerProperty();

	public int getMediaId() {
		return mediaIdProperty.get();
	}

	public int getSerieId() {
		return serieIdProperty.get();
	}

	public void initData(int mediaId, int serieId) {
		setMediaId(mediaId);
		setSerieId(serieId);
	}

	public void initialize() {
		mediaIdProperty.addListener((observable, oldValue, newValue) -> loadData(newValue.intValue(), getSerieId()));
		serieIdProperty.addListener((observable, oldValue, newValue) -> loadData(getMediaId(), newValue.intValue()));

		loadData(getMediaId(), getSerieId());
	}

	public void loadData(int mediaId, int serieId) {
		setMediaId(mediaId);
		setSerieId(serieId);
		if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
			try {
				List<Saison> seasons = DatabaseUtil.getSaisonBySerieId(serieId);

				System.out.println("Seasons: " + seasons);

				seasonsContainer.getChildren().clear();

				for (Saison season : seasons) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SeasonLayout.fxml"));
					AnchorPane seasonLayout = loader.load();
					SeasonLayoutController seasonLayoutController = loader.getController();
					seasonLayoutController.initData(season);

					seasonsContainer.getChildren().add(seasonLayout);
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
				showErrorDialog("Error Loading Season");
			}
		} else {
			try {
				List<Saison> seasons = DatabaseUtil.getSaisonBySerieId(serieId);

				System.out.println("Seasons: " + seasons);

				seasonsContainer.getChildren().clear();

				for (Saison season : seasons) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SeasonLayout_Others.fxml"));
					AnchorPane seasonLayout = loader.load();
					SeasonLayoutController seasonLayoutController = loader.getController();
					seasonLayoutController.initData(season);

					seasonsContainer.getChildren().add(seasonLayout);
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
				showErrorDialog("Error Loading Season");
			}
		}

	}

	public IntegerProperty mediaIdProperty() {
		return mediaIdProperty;
	}

	public IntegerProperty serieIdProperty() {
		return serieIdProperty;
	}

	public void setMediaId(int mediaId) {
		this.mediaIdProperty.set(mediaId);
	}

	public void setSerieId(int serieId) {
		this.serieIdProperty.set(serieId);
	}

	private void showErrorDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void updateMediaAndSerieId(int mediaId, int serieId) {
		setMediaId(mediaId);
		setSerieId(serieId);
	}

}
