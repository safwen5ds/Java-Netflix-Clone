package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.beans.property.*;
import javafx.scene.control.ListCell;


import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.sql.SQLException;
import java.util.List;

public class SaisonController {

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


    public void initialize()  {
    	mediaIdProperty.addListener((observable, oldValue, newValue) -> loadData(newValue.intValue(), getSerieId()));
        serieIdProperty.addListener((observable, oldValue, newValue) -> loadData(getMediaId(), newValue.intValue()));

        // Set up cell value factories for ListView
        listcomment.setCellFactory(param -> new ListCell<Commentaire_saison>() {
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
            if (!seasons.isEmpty()) {
                Saison season = seasons.get(0);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
