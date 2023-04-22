package org.fsb.FlixFlow.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Models.Notification;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.io.IOException;
import java.util.List;

public class UserDashboardController {

	@FXML
	private HBox contentPane;
	@FXML
	private Button btnhome;

    @FXML
    private ImageView notificon;

    @FXML
    private ImageView profileicon;

	public UserDashboardController() {

	}

	 @FXML
	    public void initialize() {
		 int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
		    List<Episode> todaysEpisodes = DatabaseUtil.fetchTodaysEpisodes(userId);
		    showNewEpisodeAlerts(todaysEpisodes);
	 }

	@FXML
	private void handleHomeClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/HomePage.fxml"));
			loader.setControllerFactory(param -> new HomePageController(this));
			Parent homePage = loader.load();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(homePage);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("exports")
	public HBox getContentPane() {
		return contentPane;
	}

	@FXML
    private void handleProfileClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Profile.fxml"));
            Parent profileRoot = loader.load();
            Scene profileScene = new Scene(profileRoot);

            Stage profileStage = new Stage();
            profileStage.setScene(profileScene);
            profileStage.setTitle("Profile");
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void handleNotificationClick() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Notifications.fxml"));
	        NotificationsController notificationsController = new NotificationsController();
	        loader.setControllerFactory(param -> notificationsController);

	        List<Notification> userNotifications = DatabaseUtil.getEpisodeNotifications();
	        for (Notification notification : userNotifications) {
	            notificationsController.addNotification(notification);
	        }

	        Parent notificationsRoot = loader.load();
	        Scene notificationsScene = new Scene(notificationsRoot);

	        Stage notificationsStage = new Stage();
	        notificationsStage.setScene(notificationsScene);
	        notificationsStage.setTitle("Notifications");
	        notificationsStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}





	public void handleFavoriteClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/favorite.fxml"));
			loader.setControllerFactory(param -> new FavController());
			Parent recentPage = loader.load();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(recentPage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void handleScheduleClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/schedule.fxml"));
			loader.setControllerFactory(param -> new ScheduleController());
			Parent recentPage = loader.load();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(recentPage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void handleMoviesClick() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SearchMovies.fxml"));
	        SearchMoviesController searchMoviesController = new SearchMoviesController(this);
	        loader.setControllerFactory(param -> searchMoviesController);
	        Parent recentPage = loader.load();
	        contentPane.getChildren().clear();
	        contentPane.getChildren().add(recentPage);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void handleSeriesClick() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SearchSeries.fxml"));
	        SearchSeriesController searchSeriesController = new SearchSeriesController(this);
	        loader.setControllerFactory(param -> searchSeriesController);
	        Parent recentPage = loader.load();
	        contentPane.getChildren().clear();
	        contentPane.getChildren().add(recentPage);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void handleRankClick() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Classement.fxml"));
	        ClassementController classementController = new ClassementController();
	        loader.setControllerFactory(param -> classementController);
	        Parent recentPage = loader.load();
	        contentPane.getChildren().clear();
	        contentPane.getChildren().add(recentPage);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	public void showNewEpisodeAlerts(List<Episode> episodes) {
	    for (Episode episode : episodes) {
	        Platform.runLater(() -> {
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("New Episode Released");
	            alert.setHeaderText("A new episode of " + episode.getNom_serie() + " is now available!");
	            alert.setContentText("Series: " + episode.getNom_serie() + "\nEpisode Number: " + episode.getNum_episode() + "\nRelease Date: " + episode.getDate_diffusion());
	            alert.showAndWait();
	        });
	    }
	}
	  @FXML
	    void handleaddepisode() {
		  try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EpisodeAdd.fxml"));
		        EpisodeAddController  episodeAddController = new  EpisodeAddController();
		        loader.setControllerFactory(param ->  episodeAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    @FXML
	    void handleaddfilm() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/FilmAdd.fxml"));
		        FilmAddController filmAddController = new FilmAddController();
		        loader.setControllerFactory(param -> filmAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    @FXML
	    void handleaddproducteur() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ProducteurAdd.fxml"));
		        ProducteurAddController producteurAddController = new ProducteurAddController();
		        loader.setControllerFactory(param -> producteurAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    @FXML
	    void handleaddsaison() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SaisonAdd.fxml"));
		        SaisonAddController saisonAddController = new SaisonAddController();
		        loader.setControllerFactory(param -> saisonAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    @FXML
	    void handleaddserie() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SerieAdd.fxml"));
		        SerieAddController serieAddController = new SerieAddController();
		        loader.setControllerFactory(param -> serieAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    @FXML
	    void handleadduser() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UserAdd.fxml"));
		        UserAddController userAddController = new UserAddController();
		        loader.setControllerFactory(param -> userAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    @FXML
	    void hendleaddactor() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ActeurAdd.fxml"));
		        ActeurAddController acteurAddController = new ActeurAddController();
		        loader.setControllerFactory(param -> acteurAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }
	    
	    @FXML
	    void handlerole_film() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Role_FilmAdd.fxml"));
		        Role_FilmAddController role_FilmAddController  = new Role_FilmAddController();
		        loader.setControllerFactory(param -> role_FilmAddController);
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    @FXML
	    void handlerole_saison() {
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Role_SerieAdd.fxml"));
		        Role_SerieAddController role_SerieAddController  = new Role_SerieAddController ();
		        loader.setControllerFactory(param -> role_SerieAddController );
		        Parent recentPage = loader.load();
		        contentPane.getChildren().clear();
		        contentPane.getChildren().add(recentPage);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }



}
