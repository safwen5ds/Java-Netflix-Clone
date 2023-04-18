package org.fsb.FlixFlow.Controllers;

import org.fsb.FlixFlow.Models.Commentaire_episode;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.sql.SQLException;
import java.util.List;

public class EpisodeLayoutController {

    @FXML
    private ListView<String> episodeListView;

    @FXML
    private WebView episodeWebView;

    @FXML
    private Label DATE_DIFFUSION;

    @FXML
    private Label VUES;

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
                    onEpisodeSelected(episodes.get(selectedIndex));
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
        listcomments.setCellFactory(param -> new ListCell<Commentaire_episode>() {
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
    }

    private void onEpisodeSelected(Episode episode) {
        DATE_DIFFUSION.setText("Date: " + episode.getDate_diffusion());
        VUES.setText("Views: " + episode.getVues());
        synopsistext.setText(episode.getSynopsis());
        episodeWebView.getEngine().load(episode.getUrl_episode());

        // Load comments for the selected episode
        try {
            List<Commentaire_episode> comments = DatabaseUtil.getCommentaireEpisodesByMediaId(episode.getId_episode());
            listcomments.getItems().clear();
            for (Commentaire_episode comment : comments) {
                listcomments.getItems().add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
