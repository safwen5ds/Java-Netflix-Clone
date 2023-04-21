package org.fsb.FlixFlow.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SeasonLayoutController {

	@FXML
	private Button addcommentbtn;
    @FXML
    private Button delbtn;
    @FXML
    private Label average;
    @FXML
    private Label vote;
    @FXML
    private Label nbreps;

    @FXML
    private Button modifbtn;
	@FXML
	private Button addfav;

	@FXML
	private Slider saisonRatingSlider;

	@FXML
	private Button submitSaisonRatingButton;

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
		 listcomment.setCellFactory(param -> new ListCell<>() {
	            @Override
	            protected void updateItem(Commentaire_saison item, boolean empty) {
	                super.updateItem(item, empty);

	                if (empty || item == null) {
	                    setText(null);
	                } else {
	                    setText(item.getNom_User() + " : " + item.getContenu());
	                }
	            }
	        });

	        watchnow.setOnAction(event -> openEpisodeLayout());
	        submitSaisonRatingButton.setOnAction(event -> submitSaisonRating());
	        addcommentbtn.setOnAction(event -> addComment());
	        delbtn.setOnAction(event -> deleteComment());
	        modifbtn.setOnAction(event -> modifyComment());
	        updateCommentList();
	    }



	    private void addComment() {
	        int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
	        String content = commentinput.getText();

	        try {
	            DatabaseUtil.addCommentForSeason(userId, saisonId, content);
	            updateCommentList();
	            commentinput.clear();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    private void deleteComment() {
	        Commentaire_saison selectedComment = listcomment.getSelectionModel().getSelectedItem();
	        if (selectedComment != null) {
	            try {
	                DatabaseUtil.deleteCommentForSeason(selectedComment.getComment_id());
	                updateCommentList();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    private void modifyComment() {
	        Commentaire_saison selectedComment = listcomment.getSelectionModel().getSelectedItem();
	        if (selectedComment != null) {
	            String newContent = commentinput.getText();
	            try {
	                DatabaseUtil.updateCommentForSeason(selectedComment.getComment_id(), newContent);
	                updateCommentList();
	                commentinput.clear();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    private void updateCommentList() {
	        try {
	            List<Commentaire_saison> comments = DatabaseUtil.getCommentaireSaisonsByMediaId(saisonId);
	            listcomment.getItems().setAll(comments);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


	    private void submitSaisonRating() {
	        int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
	        int rating = (int) Math.round(saisonRatingSlider.getValue());
	        DatabaseUtil.submitSaisonRating(userId, saisonId, rating);
	        updateAverageScore();
	    }
	    private void updateAverageScore() {
	        try {
	            double averageScore = DatabaseUtil.calculateAverageSeasonScore(saisonId);
	            average.setText(String.format("%.2f", averageScore));
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
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


	public void initData(Saison season) throws SQLException {
		if (season != null) {
			setIds(season.getId_saison(), season.getId_serie());
			serieNameLabel.setText("Name: " + season.getNom_serie());
			seasonNumLabel.setText("Season: " + season.getNum_saison());
			seasonSynopsistext.setText(season.getSynopsis());
			seasonDate_debutLabel.setText("Date: " + season.getDate_debut());
			seasonViewsLabel.setText("Views: " + season.getVues());
		    double averageScore = DatabaseUtil.calculateAverageSeasonScore(season.getId_saison());
			average.setText(String.format("%.2f", averageScore));

			// Set image for the Rectangle
			String imageUrl = season.getUrl_image();
			Image imageContent = new Image(imageUrl);
			ImagePattern pattern = new ImagePattern(imageContent);
			image.setFill(pattern);

			videoweb.getEngine().load(season.getUrl_video());
		}
		updateCommentList();
		updateVoteCount();
		updateTotalEpisodes();
	}
	private void updateVoteCount() {
	    try {
	        int voteCount = DatabaseUtil.getVoteCountForSeason(saisonId);
	        vote.setText(String.valueOf(voteCount));
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	private void updateTotalEpisodes() {
	    try {
	        int totalEpisodes = DatabaseUtil.getTotalEpisodesForSeason(saisonId);
	        nbreps.setText(String.valueOf(totalEpisodes));
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
