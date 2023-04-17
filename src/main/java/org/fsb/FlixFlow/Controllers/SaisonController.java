package org.fsb.FlixFlow.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class SaisonController {
	@FXML
	private VBox seasonsContainer;

	@FXML
	private Button addcommentbtn;

	@FXML
	private Button addfav;

	@FXML
	private TextField commentinput;

	@FXML
	private Rectangle image;

	@FXML
	private ListView<Commentaire_saison> listcomment;

	@FXML
	private Label seasonDate_debutLabel;

	@FXML
	private Label seasonNumLabel;

	@FXML
	private Text seasonSynopsistext;

	@FXML
	private Label seasonViewsLabel;

	@FXML
	private Label serieNameLabel;

	@FXML
	private WebView videoweb;

	@FXML
	private Button votebtn;

	@FXML
	private Button watchnow;

	@FXML
	private Button watchtrailer;

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

		// Set up cell value factories for ListView
		listcomment.setCellFactory(param -> new ListCell<>() {
			@Override
			protected void updateItem(Commentaire_saison item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getNom_User() + ": " + item.getContenu());
				}
			}
		});
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
			List<Commentaire_saison> comments = DatabaseUtil.getCommentaireSaisonsByMediaId(mediaId);
			System.out.println("Comments: " + comments);
			listcomment.getItems().clear(); // Clear the items before setting new ones
			listcomment.getItems().setAll(comments);

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
