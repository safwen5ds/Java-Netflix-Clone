package org.fsb.FlixFlow.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.Callback;

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

	Font bebasNeueFont = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 20);

	public void initData(int saisonId, int serieId) {
		episodeListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null && !empty) {
							setText(item);
							setStyle("-fx-background-color: purple; -fx-text-fill: white;");
						} else {
							setText(null);
							setStyle(null);
						}
					}
				};
			}
		});
		try {
			episodes = org.fsb.FlixFlow.Utilities.DatabaseUtil.getEpisodeByIds(saisonId, serieId);

			for (Episode episode : episodes) {
				System.out.println("Adding episode to ListView: " + episode.getNum_episode());
				episodeListView.getItems().add(0, "Episode " + episode.getNum_episode());
			}

			episodeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				int selectedIndex = episodeListView.getSelectionModel().getSelectedIndex();
				int adjustedIndex = episodes.size() - 1 - selectedIndex;
				if (adjustedIndex >= 0) {
					try {
						onEpisodeSelected(episodes.get(adjustedIndex));
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
		addcommentbtn.setFont(bebasNeueFont);
		delbtn.setFont(bebasNeueFont);
		modifbtn.setFont(bebasNeueFont);
		submitEpisodeRatingButton.setFont(bebasNeueFont);
		System.out.println("Fetched episodes: " + episodes);
		System.out.println("Number of episodes: " + episodes.size());

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
			boolean updated = DatabaseUtil.submitEpisodeRating(userId, episodeId, rating);
			if (updated) {
				showAlert("Rating Updated", "Your rating for this episode has been updated.");
			}
			updateAverageScore(episodeId);
		}
	}

	private void updateAverageScore(int episodeId) {
		try {
			double averageScore = DatabaseUtil.calculateAverageEpisodeScore(episodeId);
			average.setText("Average : " + String.format("%.2f", averageScore));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void onEpisodeSelected(Episode episode) throws SQLException {
		DATE_DIFFUSION.setText("Date: " + episode.getDate_diffusion());
		DATE_DIFFUSION.setFont(bebasNeueFont);
		if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
			VUES.setText("Views: " + episode.getVues());
			VUES.setFont(bebasNeueFont);
		}
		synopsistext.setText(episode.getSynopsis());
		synopsistext.setFont(bebasNeueFont);
		episodeWebView.getEngine().load(episode.getUrl_episode());
		double averageScore = DatabaseUtil.calculateAverageEpisodeScore(episode.getId_episode());
		average.setText("Average : " + String.format("%.2f", averageScore));
		average.setFont(bebasNeueFont);

		try {
			List<Commentaire_episode> comments = DatabaseUtil.getCommentaireEpisodesByMediaId(episode.getId_episode());
			listcomments.getItems().clear();
			for (Commentaire_episode comment : comments) {
				listcomments.getItems().add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Loading Comments ! ");
		}
		int id_episode = episode.getId_episode();
		addcommentbtn.setOnAction(event -> addComment(id_episode));
		addcommentbtn.setFont(bebasNeueFont);
		delbtn.setFont(bebasNeueFont);
		modifbtn.setFont(bebasNeueFont);
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

				List<Episode> updatedEpisodes = DatabaseUtil.getEpisodeByIds(episode.getId_saison(),
						episode.getId_serie());
				for (Episode updatedEpisode : updatedEpisodes) {
					if (updatedEpisode.getId_episode() == episode.getId_episode()) {
						if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
							VUES.setFont(bebasNeueFont);
							VUES.setText("Views: " + updatedEpisode.getVues());
						}
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
			if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
				vote.setFont(bebasNeueFont);
				vote.setText("Votes : " + String.valueOf(totalRatings));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Updating Vote !");
		}
	}

	public Callback<ListView<String>, ListCell<String>> episodeCellFactory() {
		return list -> new ListCell<String>() {
			private final ImageView playIcon;

			{
				playIcon = new ImageView(new Image("/FXML/play.png"));
				playIcon.setFitHeight(16);
				playIcon.setFitWidth(16);
				setGraphic(playIcon);
				setContentDisplay(ContentDisplay.RIGHT);
			}

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item);
					setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
					if (isSelected()) {
						setStyle("-fx-background-color: #9400D3; -fx-text-fill: white;");
						setGraphic(playIcon);
					} else {
						setGraphic(null);
					}
				}
			}
		};
	}

	private void showErrorDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private static void showAlert(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
