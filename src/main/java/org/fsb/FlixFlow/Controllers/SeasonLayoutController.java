package org.fsb.FlixFlow.Controllers;

import org.fsb.FlixFlow.Models.Commentaire_saison;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.fsb.FlixFlow.Models.Saison;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class SeasonLayoutController {

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

	private int saisonId;

	private int serieId;
	public void setIds(int saisonId, int serieId) {
	    this.saisonId = saisonId;
	    this.serieId = serieId;
	}

	@FXML
	public void initialize() {
	    watchnow.setOnAction(event -> openEpisodeLayout());
	}

	private void openEpisodeLayout() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/episode.fxml"));
	        Parent root = loader.load();
	        EpisodeLayoutController controller = loader.getController();
	        controller.initData(saisonId, serieId);

	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	public void initData(Saison season) {
		if (season != null) {
			setIds(season.getId_saison(), season.getId_serie());
			serieNameLabel.setText("Name: " + season.getNom_serie());
			seasonNumLabel.setText("Season: " + season.getNum_saison());
			seasonSynopsistext.setText(season.getSynopsis());
			seasonDate_debutLabel.setText("Date: " + season.getDate_debut());
			seasonViewsLabel.setText("Views: " + season.getVues());

			// Set image for the Rectangle
			String imageUrl = season.getUrl_image();
			Image imageContent = new Image(imageUrl);
			ImagePattern pattern = new ImagePattern(imageContent);
			image.setFill(pattern);

			videoweb.getEngine().load(season.getUrl_video());
		}
	}
}
