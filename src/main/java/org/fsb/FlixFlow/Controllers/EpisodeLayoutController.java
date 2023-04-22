package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import org.fsb.FlixFlow.Models.Commentaire_episode;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.sql.SQLException;
import java.util.List;

public class EpisodeLayoutController {

    @FXML
    private ListView<String> episodeListView;
    @FXML
    private Label average;
    @FXML
    private Label vote;

    @FXML
    private TextField txtcomment;

    @FXML
    private WebView episodeWebView;

    @FXML
    private Label DATE_DIFFUSION;

    @FXML
    private Label VUES;

    @FXML
    private Button addcommentbtn;

    @FXML
    private Button delbtn;

    @FXML
    private Button modifbtn;

    @FXML
	private Slider episodeRatingSlider;

	@FXML
	private Button submitEpisodeRatingButton;

    @FXML
    private Text synopsistext;

    @FXML
    private Button addfavbtn;

    @FXML
    private Button votebtn;
    @FXML
    private ListView<Commentaire_episode> listcomments;


    private List<Episode> episodes;

    public void initData(int saisonId, int serieId) {
        try {
            episodes = org.fsb.FlixFlow.Utilities.DatabaseUtil.getEpisodeByIds(saisonId, serieId);

            for (Episode episode : episodes) {
                episodeListView.getItems().add("Episode " + episode.getNum_episode());

            }

            episodeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                int selectedIndex = episodeListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    try {
						onEpisodeSelected(episodes.get(selectedIndex));
					} catch (SQLException e) {

						e.printStackTrace();
					}
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
        listcomments.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Commentaire_episode item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom_User() + " : " + item.getContenu());
                }
            }
        });
		submitEpisodeRatingButton.setOnAction(event -> submitEpisodeRating());

    }
    private void addComment(int id_episode) {
        int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
        String content = txtcomment.getText();

        try {
            DatabaseUtil.addCommentForEpisode(userId, id_episode, content);
            updateCommentList(id_episode);
            txtcomment.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteComment(int id_episode) {
        Commentaire_episode selectedComment = listcomments.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            try {
                DatabaseUtil.deleteCommentForEpisode(selectedComment.getComment_id());
                updateCommentList(id_episode);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void modifyComment(int id_episode) {
        Commentaire_episode selectedComment = listcomments.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            String newContent = txtcomment.getText();
            try {
                DatabaseUtil.updateCommentForEpisode(selectedComment.getComment_id(), newContent);
                updateCommentList(id_episode);
                txtcomment.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCommentList(int id_episode) {
        try {
            List<Commentaire_episode> comments = DatabaseUtil.getCommentaireEpisodesByMediaId(id_episode);
            listcomments.getItems().setAll(comments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void submitEpisodeRating() {
        int selectedIndex = episodeListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
            int episodeId = episodes.get(selectedIndex).getId_episode();
            int rating = (int) Math.round(episodeRatingSlider.getValue());
            DatabaseUtil.submitEpisodeRating(userId, episodeId, rating);
            updateAverageScore(episodeId);
        }
    }
    private void updateAverageScore(int episodeId) {
        try {
            double averageScore = DatabaseUtil.calculateAverageEpisodeScore(episodeId);
            average.setText(String.format("%.2f", averageScore));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void onEpisodeSelected(Episode episode) throws SQLException {
        DATE_DIFFUSION.setText("Date: " + episode.getDate_diffusion());
        VUES.setText("Views: " + episode.getVues());
        synopsistext.setText(episode.getSynopsis());
        episodeWebView.getEngine().load(episode.getUrl_episode());
        double averageScore = DatabaseUtil.calculateAverageEpisodeScore(episode.getId_episode());
        average.setText(String.format("%.2f", averageScore));

        try {
            List<Commentaire_episode> comments = DatabaseUtil.getCommentaireEpisodesByMediaId(episode.getId_episode());
            listcomments.getItems().clear();
            for (Commentaire_episode comment : comments) {
                listcomments.getItems().add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int id_episode = episode.getId_episode();
        addcommentbtn.setOnAction(event -> addComment(id_episode));
        delbtn.setOnAction(event -> deleteComment(id_episode));
        modifbtn.setOnAction(event -> modifyComment(id_episode));
        updateCommentList(id_episode);
        updateVoteCount(id_episode);

        try {
            int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
            if (!DatabaseUtil.hasUserSeenEpisode(userId, episode.getId_episode())) {
                DatabaseUtil.incrementEpisodeViews(episode.getId_episode());
                DatabaseUtil.incrementSeasonViews(episode.getId_saison());
                DatabaseUtil.addUserView(userId, episode.getId_episode());

                List<Episode> updatedEpisodes = DatabaseUtil.getEpisodeByIds(episode.getId_saison(), episode.getId_serie());
                for (Episode updatedEpisode : updatedEpisodes) {
                    if (updatedEpisode.getId_episode() == episode.getId_episode()) {
                        VUES.setText("Views: " + updatedEpisode.getVues());
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateVoteCount(int episodeId) {
        try {
            int totalRatings = DatabaseUtil.getTotalRatingsForEpisode(episodeId);
            vote.setText(String.valueOf(totalRatings));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
