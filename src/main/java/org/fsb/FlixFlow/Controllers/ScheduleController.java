package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

	@FXML
	private ListView<String> episodesListView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTodaysEpisodes();
	}

	private void loadTodaysEpisodes() {

		int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();

		List<Episode> episodes = DatabaseUtil.fetchTodaysEpisodes(userId);

		for (Episode episode : episodes) {
			episodesListView.getItems().add(
					"Series: " + episode.getNom_serie() +
							" | Episode: " + episode.getNum_episode() +
							" | Date: " + episode.getDate_diffusion()
			);
		}
	}
}
