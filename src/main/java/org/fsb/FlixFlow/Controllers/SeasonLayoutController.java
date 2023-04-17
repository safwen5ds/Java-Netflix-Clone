package org.fsb.FlixFlow.Controllers;

import org.fsb.FlixFlow.Models.Commentaire_saison;
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

	public void initData(Saison season) {
		if (season != null) {
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
