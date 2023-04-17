package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;


import java.sql.SQLException;
import java.util.List;

public class SaisonController {
	

    @FXML
    private VBox seasonDetailsVBox;

    @FXML
    private ListView<Commentaire_saison> commentListView;

    @FXML
    private ImageView seasonImageView;

    @FXML
    private MediaView seasonVideoView;

    @FXML
    private Label seasonNameLabel;

    @FXML
    private Label seasonNumLabel;

    @FXML
    private Label seasonSynopsisLabel;

    @FXML
    private Label seasonDateLabel;

    @FXML
    private Label seasonViewsLabel;

    private int mediaId;
    private int serieId;

    public void initialize() {
        // Call this method with appropriate mediaId and serieId values
        loadData(mediaId, serieId);
    }

    public void loadData(int mediaId, int serieId) {
        this.mediaId = mediaId;
        this.serieId = serieId;

        try {
            List<Commentaire_saison> comments = DatabaseUtil.getCommentaireSaisonsByMediaId(mediaId);
            commentListView.getItems().setAll(comments);

            List<Saison> seasons = DatabaseUtil.getSaisonBySerieId(serieId);
            if (!seasons.isEmpty()) {
                Saison season = seasons.get(0);
                seasonNameLabel.setText("Name: " + season.getNom_serie());
                seasonNumLabel.setText("Season: " + season.getNum_saison());
                seasonSynopsisLabel.setText("Synopsis: " + season.getSynopsis());
                seasonDateLabel.setText("Date: " + season.getDate_debut());
                seasonViewsLabel.setText("Views: " + season.getVues());

                // Set image for ImageView
                String imageUrl = season.getUrl_image();
                Image image = new Image(imageUrl);
                seasonImageView.setImage(image);

                // Set video for MediaView
                String videoUrl = season.getUrl_video();
                Media media = new Media(videoUrl);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                seasonVideoView.setMediaPlayer(mediaPlayer);
                mediaPlayer.setAutoPlay(false); // Set to true if you want the video to auto-play
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}

