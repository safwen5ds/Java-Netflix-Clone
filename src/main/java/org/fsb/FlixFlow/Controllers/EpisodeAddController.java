package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EpisodeAddController implements Initializable {

    @FXML
    private TableView<Episode> episodeTable;



    private ObservableList<Episode> episodes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            episodes = FXCollections.observableArrayList(DatabaseUtil.getAllEpisodes());
        } catch (SQLException e) {
            e.printStackTrace();
            episodes = FXCollections.observableArrayList();
        }

        episodeTable.setItems(episodes);

    }

    @FXML
    private void addEpisode(ActionEvent event) {

        Episode newEpisode = new Episode();

        try {
            DatabaseUtil.createEpisode(newEpisode);
            episodes.add(newEpisode);
        } catch (SQLException e) {
            e.printStackTrace();
          
        }
    }

    @FXML
    private void updateEpisode(ActionEvent event) {
        Episode selectedEpisode = episodeTable.getSelectionModel().getSelectedItem();
        if (selectedEpisode != null) {
          

            try {
                DatabaseUtil.updateEpisode(selectedEpisode);
            } catch (SQLException e) {
                e.printStackTrace();
         
            }
        }
    }

    @FXML
    private void deleteEpisode(ActionEvent event) {
        Episode selectedEpisode = episodeTable.getSelectionModel().getSelectedItem();
        if (selectedEpisode != null) {
            try {
                DatabaseUtil.deleteEpisode(selectedEpisode.getId_episode());
                episodes.remove(selectedEpisode);
            } catch (SQLException e) {
                e.printStackTrace();
        
            }
        }
    }
}
