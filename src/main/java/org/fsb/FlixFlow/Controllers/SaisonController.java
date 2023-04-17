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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SaisonController {
	@FXML
	private VBox seasonsContainer;

	private int mediaId;
	private int serieId;

	private final IntegerProperty mediaIdProperty = new SimpleIntegerProperty();
	private final IntegerProperty serieIdProperty = new SimpleIntegerProperty();

	public IntegerProperty mediaIdProperty() {
		return mediaIdProperty;
	}

	public void setMediaId(int mediaId) {
		this.mediaIdProperty.set(mediaId);
	}

	public int getMediaId() {
		return mediaIdProperty.get();
	}

	public IntegerProperty serieIdProperty() {
		return serieIdProperty;
	}

	public void setSerieId(int serieId) {
		this.serieIdProperty.set(serieId);
	}

	public int getSerieId() {
		return serieIdProperty.get();
	}

	public void initialize() {
		mediaIdProperty.addListener((observable, oldValue, newValue) -> loadData(newValue.intValue(), getSerieId()));
		serieIdProperty.addListener((observable, oldValue, newValue) -> loadData(getMediaId(), newValue.intValue()));

		loadData(getMediaId(), getSerieId());
	}

	public void initData(int mediaId, int serieId) {
		setMediaId(mediaId);
		setSerieId(serieId);
	}

	public void updateMediaAndSerieId(int mediaId, int serieId) {
		setMediaId(mediaId);
		setSerieId(serieId);
	}

	public void loadData(int mediaId, int serieId) {
		this.mediaId = mediaId;
		this.serieId = serieId;
		setMediaId(mediaId);
		setSerieId(serieId);

		try {
			List<Saison> seasons = DatabaseUtil.getSaisonBySerieId(serieId);
			System.out.println("Seasons: " + seasons);

			// Clear previous seasons
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
		}
	}
}
