package org.fsb.FlixFlow.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.fsb.FlixFlow.Models.Commentaire_film;
import org.fsb.FlixFlow.Models.Commentaire_serie;
import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Models.Utilisateur;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import org.fsb.FlixFlow.Views.CommentaireDisplay;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieSeriesDetailsController {

	@FXML
	private ListView<ActorRoleDisplay> actorslist;
	@FXML
	private Button addfav;
	@FXML
	private Button addgenrefav;
	@FXML
	private Button addvote;
	@FXML
	private Label annee;

	@FXML
	private Label average;

	@FXML
	private Rectangle backdropImage;
	Font bebasNeueFont = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 20);
	Font bebasNeueFont1 = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 50);

	Font bebasNeueFont2 = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 90);
	@FXML
	private TableColumn<CommentaireDisplay, CommentaireDisplay> commentaire;
	@FXML
	private Button commentbtn;
	@FXML
	private Button deleteCommentButton;
	@FXML
	private Label genre;

	private final boolean isMovie;

	@FXML
	private Label langue;

	@FXML
	private ImageView m1;

	@FXML
	private ImageView m2;
	private final int mediaId;

	@FXML
	private Button modifyCommentButton;

	@FXML
	private Label pays;

	@FXML
	private Rectangle posterImage;

	@FXML
	private TableColumn<CommentaireDisplay, CommentaireDisplay> prenom;

	@FXML
	private Label producteur;

	@FXML
	private Slider ratingSlider;

	@FXML
	private StackPane root;

	@FXML
	private Button submitRating;

	@FXML
	private Label synopsis;

	@FXML
	private Label synopsisArea;

	@FXML
	private TableView<CommentaireDisplay> tab;

	@FXML
	private Label titre;

	@FXML
	private TextField txtcomment;

	private final UserDashboardController userDashboardController;

	@FXML
	private Label v1;

	@FXML
	private Label v2;

	@FXML
	private Label vote;

	@FXML
	private Label vues;

	@FXML
	private Button watchButton;
	@FXML
	private Button Watchtrailer;

	public MovieSeriesDetailsController(int mediaId, boolean isMovie, UserDashboardController userDashboardController) {
		this.mediaId = mediaId;
		this.isMovie = isMovie;
		this.userDashboardController = userDashboardController;
	}

	private void addFavoriteGenre(int genreId) {
		try {
			int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
			if (DatabaseUtil.isGenreFavExists(userId, genreId)) {
				showmsg("This genre is already in your favorites.");

			} else {
				DatabaseUtil.addPreferenceGenre(userId, genreId);
				showmsg("Genre added to your favorites.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error ! ");
		}
	}

	@FXML
	public void initialize() throws SQLException {
		commentbtn.setFont(bebasNeueFont);
		deleteCommentButton.setFont(bebasNeueFont);
		modifyCommentButton.setFont(bebasNeueFont);
		watchButton.setFont(bebasNeueFont);
		Watchtrailer.setFont(bebasNeueFont);
		addfav.setFont(bebasNeueFont);
		submitRating.setFont(bebasNeueFont);
		initializeTableView();

		if (isMovie) {
			try {
				Film film = DatabaseUtil.getFilmById(mediaId);
				if (film != null) {
					setFilmDetails(film);
				} else {

					showErrorDialog("Film not found with mediaId: " + mediaId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Serie serie = DatabaseUtil.getSerieById(mediaId);
				DatabaseUtil.calculateTotalSeriesViews(mediaId);
				if (serie != null) {
					setSerieDetails(serie);
				} else {
					showErrorDialog("Serie not found with mediaId: " + mediaId);

				}
			} catch (SQLException e) {
				e.printStackTrace();
				showErrorDialog("Error ! ");
			}
		}
		loadActorsList();
		table();

		Watchtrailer.setOnAction(e -> {
			String url = null;
			try {
				url = isMovie ? DatabaseUtil.getFilmById(mediaId).getUrl_video()
						: DatabaseUtil.getSerieById(mediaId).getUrl_video();
			} catch (SQLException e1) {
				e1.printStackTrace();
				showErrorDialog("Error ! ");
			}
			openUrlInNewWindow(url);
		});

		if (!isMovie) {
			watchButton.setOnAction(event -> openSaisonPage());
		} else {
			watchButton.setOnAction(e -> {
				String url = null;
				try {
					url = DatabaseUtil.getFilmById(mediaId).getUrl_film();
					int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();

					if (!DatabaseUtil.hasUserSeenFilm(userId, mediaId)) {
						DatabaseUtil.incrementFilmViews(mediaId);
						DatabaseUtil.addUserFilmView(userId, mediaId);

						Film updatedFilm = DatabaseUtil.getFilmById(mediaId);
						if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
							v2.setFont(bebasNeueFont2);
							vues.setText(String.valueOf(updatedFilm.getVues()));
							vues.setFont(bebasNeueFont2);
						}

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					showErrorDialog("Error ! ");

				}
				openUrlInNewWindow(url);
			});

		}
		actorslist.setCellFactory(listView -> new ActorRoleListCell());
		Utilisateur loggedInUser = DatabaseUtil.readUserFromFile();

		addfav.setOnAction(e -> {
			try {
				if (isMovie) {

					if (DatabaseUtil.addPreferenceFilm(loggedInUser.getId_utilisateur(), mediaId) == 0) {
						showmsg("Movie Already In Your Favorites ! ");
					} else {
						showmsg("Movie Added To Favorites ! ");
					}

				} else {
					if (DatabaseUtil.addPreferenceSerie(loggedInUser.getId_utilisateur(), mediaId) == 0) {
						showmsg("Serie Already In Your Favorites ! ");
					} else {
						showmsg("Serie Added To Favorites ! ");
					}

				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				showErrorDialog("Error Adding To Favorite ! ");
			}
		});
		submitRating.setOnAction(event -> {
			DatabaseUtil.submitRating(ratingSlider, isMovie, mediaId);
			updateAverageRating();
		});

		commentbtn.setOnAction(e -> {
			Utilisateur User;
			try {
				User = DatabaseUtil.readUserFromFile();
				String content = txtcomment.getText();
				if (!content.isEmpty()) {
					if (isMovie) {
						DatabaseUtil.addCommentForFilm(User.getId_utilisateur(), mediaId, content);
					} else {
						DatabaseUtil.addCommentForSeries(User.getId_utilisateur(), mediaId, content);
					}
					txtcomment.clear();
					table();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				showErrorDialog("Error Adding Comment ! ");
			}
		});

		modifyCommentButton.setOnAction(e -> {
			CommentaireDisplay selectedComment = tab.getSelectionModel().getSelectedItem();
			if (selectedComment != null) {
				try {
					int comment_id = selectedComment.getComment_id();
					String newContent = txtcomment.getText();
					if (!newContent.isEmpty()) {
						if (isMovie) {
							DatabaseUtil.updateCommentForFilm(comment_id, newContent);
						} else {
							DatabaseUtil.updateCommentForSeries(comment_id, newContent);
						}
						txtcomment.clear();
						table();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					showErrorDialog("Error Modifying Comment ! ");
				}
			}
		});

		deleteCommentButton.setOnAction(e -> {
			CommentaireDisplay selectedComment = tab.getSelectionModel().getSelectedItem();
			if (selectedComment != null) {
				try {
					int comment_id = selectedComment.getComment_id();
					if (isMovie) {
						DatabaseUtil.deleteCommentForFilm(comment_id);
					} else {
						DatabaseUtil.deleteCommentForSeries(comment_id);
					}
					initializeTableView();
					table();
				} catch (SQLException ex) {
					ex.printStackTrace();
					showErrorDialog("Error Deleting Comment !  ");
				}
			}
		});

		addgenrefav.setOnAction(e -> {
			int genreId = -1;
			try {
				if (isMovie) {
					Film film = DatabaseUtil.getFilmById(mediaId);
					genreId = film.getId_genre();
				} else {
					Serie serie = DatabaseUtil.getSerieById(mediaId);
					genreId = serie.getId_genre();
				}
				addFavoriteGenre(genreId);
			} catch (SQLException ex) {
				ex.printStackTrace();
				showErrorDialog("Error Adding To Favorite ! ");
			}
		});

		updateVoteCount();

	}

	private void initializeTableView() {
		prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		commentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

		tab.setRowFactory(tv -> {
			TableRow<CommentaireDisplay> myRow = new TableRow<>();
			myRow.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
					int myIndex = tab.getSelectionModel().getSelectedIndex();
					prenom.setText(tab.getItems().get(myIndex).getPrenom());
					commentaire.setText(tab.getItems().get(myIndex).getCommentaire());
				}
			});
			return myRow;
		});
	}

	private void loadActorsList() {
		try {
			List<ActorRoleDisplay> actorRoles;
			if (isMovie) {
				actorRoles = DatabaseUtil.getActorRolesForMovie(mediaId);
			} else {
				actorRoles = DatabaseUtil.getActorRolesForSeries(mediaId);
			}
			actorslist.getItems().clear();
			for (ActorRoleDisplay actorRole : actorRoles) {
				actorslist.getItems().add(actorRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Loading Actors List ! ");
		}
	}

	private void openSaisonPage() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/saison.fxml"));
			SaisonController saisonController = new SaisonController();
			loader.setController(saisonController);
			Parent saisonPage = loader.load();
			int serieId = 1;
			saisonController.initData(serieId, mediaId);

			Platform.runLater(() -> {
				userDashboardController.getContentPane().getChildren().setAll(saisonPage);
			});

		} catch (IOException e) {
			e.printStackTrace();
			showErrorDialog("Error Openning Seasons Page ! ");
		}
	}

	private void openUrlInNewWindow(String url) {
		Stage newWindow = new Stage();
		newWindow.initModality(Modality.APPLICATION_MODAL);
		final WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
		webEngine.load(url);
		newWindow.setOnHidden(e -> webView.getEngine().load(null));
		newWindow.setScene(new Scene(new StackPane(webView), 800, 600));
		newWindow.show();
	}

	public void setFilmDetails(Film film) throws SQLException {
		titre.setText(film.getNom());
		titre.setFont(bebasNeueFont1);
		ImagePattern pattern = new ImagePattern(new Image(film.getUrl_image()));
		backdropImage.setFill(pattern);
		annee.setText(String.valueOf(film.getAnnee_sortie()));
		annee.setFont(bebasNeueFont);
		posterImage.setFill(pattern);
		synopsisArea.setText(film.getSynopsis());
		if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
			v2.setFont(bebasNeueFont2);
			vues.setText(String.valueOf(film.getVues()));
			vues.setFont(bebasNeueFont2);
		}

		genre.setText(film.getNom_genre());
		genre.setFont(bebasNeueFont);
		langue.setText(film.getNom_langue());
		langue.setFont(bebasNeueFont);
		producteur.setText(film.getNom_producteur());
		producteur.setFont(bebasNeueFont);
		pays.setText(film.getNom_pays());
		pays.setFont(bebasNeueFont);
		table();
		double averageScore = DatabaseUtil.calculateAverageFilmScore(film.getId_film());
		average.setText(String.format("%.2f", averageScore));
		average.setFont(bebasNeueFont);

	}

	public void setSerieDetails(Serie serie) throws SQLException {
		titre.setText(serie.getNom());
		titre.setFont(bebasNeueFont1);
		ImagePattern pattern = new ImagePattern(new Image(serie.getUrl_image()));
		backdropImage.setFill(pattern);
		annee.setText(String.valueOf(serie.getAnnee_sortie()));
		annee.setFont(bebasNeueFont);
		posterImage.setFill(pattern);
		synopsisArea.setText(serie.getSynopsis());
		DatabaseUtil.calculateTotalSeriesViews(mediaId);
		if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
			v2.setFont(bebasNeueFont2);
			vues.setText(String.valueOf(serie.getVues()));
			vues.setFont(bebasNeueFont2);
		}

		genre.setText(serie.getNom_genre());
		genre.setFont(bebasNeueFont);
		langue.setText(serie.getNom_langue());
		langue.setFont(bebasNeueFont);
		producteur.setText(serie.getNom_producteur());
		producteur.setFont(bebasNeueFont);
		pays.setText(serie.getNom_pays());
		pays.setFont(bebasNeueFont);
		table();
		double averageScore = DatabaseUtil.calculateAverageSeriesScore(serie.getId_serie());
		average.setText(String.format("%.2f", averageScore));
		average.setFont(bebasNeueFont);
	}

	private void showErrorDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showmsg(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Important");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void table() {
		ObservableList<CommentaireDisplay> commentaires = FXCollections.observableArrayList();

		try {
			if (isMovie) {
				List<Commentaire_film> commentaireFilms = DatabaseUtil.getCommentaireFilmsByMediaId(mediaId);

				for (Commentaire_film cf : commentaireFilms) {
					System.out.println("Commentaire_film: " + cf.getNom_User() + " - " + cf.getContenu());
					commentaires.add(new CommentaireDisplay(cf.getNom_User(), cf.getContenu(), cf.getComment_id()));
				}
			} else {
				List<Commentaire_serie> commentaireSeries = DatabaseUtil.getCommentaireSeriesByMediaId(mediaId);

				for (Commentaire_serie cs : commentaireSeries) {
					System.out.println("Commentaire_serie: " + cs.getNom_User() + " - " + cs.getContenu());
					commentaires.add(new CommentaireDisplay(cs.getNom_User(), cs.getContenu(), cs.getComment_id()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error Loading Page ! ");
		}

		tab.setItems(commentaires);

	}

	private void updateAverageRating() {
		try {
			double averageScore;
			if (isMovie) {
				averageScore = DatabaseUtil.calculateAverageFilmScore(mediaId);

			} else {
				averageScore = DatabaseUtil.calculateAverageSeriesScore(mediaId);

			}
			average.setText(String.format("%.2f", averageScore));

		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error ! ");
		}
	}

	private void updateVoteCount() {

		try {
			int voteCount;
			if (isMovie) {
				voteCount = DatabaseUtil.getVoteCountForFilm(mediaId);
			} else {
				voteCount = DatabaseUtil.getVoteCountForSeries(mediaId);
			}
			if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
				v1.setFont(bebasNeueFont2);
				vote.setText(String.valueOf(voteCount));
				vote.setFont(bebasNeueFont2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showErrorDialog("Error ! ");
		}

	}

}
