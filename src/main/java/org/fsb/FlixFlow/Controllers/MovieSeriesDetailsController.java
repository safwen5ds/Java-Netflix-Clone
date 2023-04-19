package org.fsb.FlixFlow.Controllers;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import org.fsb.FlixFlow.Models.Acteur;
import org.fsb.FlixFlow.Models.CommentaireDisplay;
import org.fsb.FlixFlow.Models.Commentaire_film;
import org.fsb.FlixFlow.Models.Commentaire_serie;
import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Models.Utilisateur;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieSeriesDetailsController {

	private int mediaId;
	private boolean isMovie;
	private UserDashboardController userDashboardController;

	// Modify the constructor
	public MovieSeriesDetailsController(int mediaId, boolean isMovie, UserDashboardController userDashboardController) {
		this.mediaId = mediaId;
		this.isMovie = isMovie;
		this.userDashboardController = userDashboardController;
	}

	@FXML
	private Button Watchtrailer;
	@FXML
	private Slider ratingSlider;

	@FXML
	private Button submitRating;

	
	 @FXML
	 private ListView<ActorRoleDisplay> actorslist;
	 

	    @FXML
	    private TextField txtcomment;
	    
	    @FXML
	    private Button commentbtn;
	    @FXML
	    private Button modifyCommentButton;

	    @FXML
	    private Button deleteCommentButton;


	@FXML
	private Button addfav;

	@FXML
	private Button addvote;

	@FXML
	private Label annee;

	@FXML
	private Rectangle backdropImage;

	@FXML
	private TableColumn<CommentaireDisplay, CommentaireDisplay> commentaire;

	@FXML
	private Label genre;

	@FXML
	private Label langue;

	@FXML
	private Label pays;

	@FXML
	private Rectangle posterImage;

	@FXML
	private TableColumn<CommentaireDisplay, CommentaireDisplay> prenom;

	@FXML
	private Label producteur;

	@FXML
	private StackPane root;

	@FXML
	private Label synopsis;

	@FXML
	private Label synopsisArea;

	@FXML
	private TableView<CommentaireDisplay> tab;

	@FXML
	private Label titre;

	@FXML
	private Label vues;

	@FXML
	private Button watchButton;



	@FXML
	public void initialize() throws SQLException {
		initializeTableView();

		if (isMovie) {
			try {
				Film film = DatabaseUtil.getFilmById(mediaId);
				setFilmDetails(film);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Serie serie = DatabaseUtil.getSerieById(mediaId);
				setSerieDetails(serie);
			} catch (SQLException e) {
				e.printStackTrace();
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
			}
			openUrlInNewWindow(url);
		});

        if (!isMovie)
        {
        	watchButton.setOnAction(event -> openSaisonPage());
        }
        else
        {
        	watchButton.setOnAction(e -> {
    			String url = null;
    			try {
    				url = DatabaseUtil.getFilmById(mediaId).getUrl_film();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			}
    			openUrlInNewWindow(url);
    		});
        }
        actorslist.setCellFactory(listView -> new ActorRoleListCell());
        Utilisateur loggedInUser = DatabaseUtil.readUserFromFile();

        
        addfav.setOnAction(e -> {
            try {
                if (isMovie) {
                    DatabaseUtil.addPreferenceFilm(loggedInUser.getId_utilisateur(), mediaId);
                } else {
                    DatabaseUtil.addPreferenceSerie(loggedInUser.getId_utilisateur(), mediaId);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        submitRating.setOnAction(event -> DatabaseUtil.submitRating(ratingSlider, isMovie, mediaId));

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
            }
        });


        modifyCommentButton.setOnAction(e -> {
            CommentaireDisplay selectedComment = tab.getSelectionModel().getSelectedItem();
            if (selectedComment != null) {
                try {
                    int comment_id = selectedComment.getComment_id();
                    txtcomment.setText(selectedComment.getCommentaire());
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
                }
            }
        });



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
	    }
	}


	private void table() {
		ObservableList<CommentaireDisplay> commentaires = FXCollections.observableArrayList();

		try {
			if (isMovie) {
				List<Commentaire_film> commentaireFilms = DatabaseUtil.getCommentaireFilmsByMediaId(mediaId);

				for (Commentaire_film cf : commentaireFilms) {
	                System.out.println("Commentaire_film: " + cf.getNom_User() + " - " + cf.getContenu()); 
					commentaires.add(new CommentaireDisplay(cf.getNom_User(), cf.getContenu(),cf.getComment_id()));
				}
			} else {
				List<Commentaire_serie> commentaireSeries = DatabaseUtil.getCommentaireSeriesByMediaId(mediaId);

				for (Commentaire_serie cs : commentaireSeries) {
	                System.out.println("Commentaire_serie: " + cs.getNom_User() + " - " + cs.getContenu());
					commentaires.add(new CommentaireDisplay(cs.getNom_User(), cs.getContenu(),cs.getComment_id()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tab.setItems(commentaires);
		
	}

	public void setFilmDetails(Film film) throws SQLException {
		titre.setText(film.getNom());
		ImagePattern pattern = new ImagePattern(new Image(film.getUrl_image()));
		backdropImage.setFill(pattern);
		annee.setText(String.valueOf(film.getAnnee_sortie()));
		posterImage.setFill(pattern);
		synopsisArea.setText(film.getSynopsis());
		vues.setText(String.valueOf(film.getVues()));
		genre.setText(film.getNom_genre());
		langue.setText(film.getNom_langue());
		producteur.setText(film.getNom_producteur());
		pays.setText(film.getNom_producteur());
		table();

	}

	public void setSerieDetails(Serie serie) throws SQLException {
		titre.setText(serie.getNom());
		ImagePattern pattern = new ImagePattern(new Image(serie.getUrl_image()));
		backdropImage.setFill(pattern);
		annee.setText(String.valueOf(serie.getAnnee_sortie()));
		posterImage.setFill(pattern);
		synopsisArea.setText(serie.getSynopsis());
		vues.setText(String.valueOf(serie.getVues()));
		genre.setText(serie.getNom_genre());
		langue.setText(serie.getNom_langue());
		producteur.setText(serie.getNom_producteur());
		pays.setText(serie.getNom_pays());
		table();
	}

	private void openUrlInNewWindow(String url) {
		Stage newWindow = new Stage();
		newWindow.initModality(Modality.APPLICATION_MODAL);
		final WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();
		webEngine.load(url);
		newWindow.setOnHidden(e -> webView.getEngine().load(null));
		newWindow.setScene(new Scene(webView));
		newWindow.show();
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
		}
	}

}
