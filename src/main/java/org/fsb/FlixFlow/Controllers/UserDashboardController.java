package org.fsb.FlixFlow.Controllers;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.effect.Blend;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Models.Notification;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserDashboardController {

	@FXML
	private ImageView actor_manager;

	@FXML
	private BorderPane border;

	@FXML
	private Button btnfavorite;

	@FXML
	private Button btnhome;

	@FXML
	private Button btnm1;

	@FXML
	private Button btnm2;

	@FXML
	private Button btnm3;

	@FXML
	private Button btnm4;

	@FXML
	private Button btnm5;

	@FXML
	private Button btnm6;

	@FXML
	private Button btnm7;

	@FXML
	private Button btnm8;

	@FXML
	private Button btnm9;

	@FXML
	private Button btnmovie;

	@FXML
	private Button btnrank;

	@FXML
	private Button btnschedule;

	@FXML
	private Button btnserie;

	@FXML
	private ImageView calen;

	@FXML
	private HBox contentPane;

	@FXML
	private ImageView episode;

	@FXML
	private ImageView film;

	@FXML
	private ImageView flixi;

	@FXML
	private HBox header;

	@FXML
	private ImageView heart;

	@FXML
	private ImageView home;

	@FXML
	private VBox menu;

	@FXML
	private VBox menu2;

	@FXML
	private ImageView movie_manager;

	@FXML
	private ImageView movie_role;

	@FXML
	private ImageView notificon;

	@FXML
	private Pane pane1;

	@FXML
	private Pane pane2;

	@FXML
	private ImageView producer;

	@FXML
	private ImageView profileicon;

	@FXML
	private ImageView rank;

	@FXML
	private ScrollPane scroll2;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private ImageView season_manager;

	@FXML
	private ImageView serie_role;

	@FXML
	private ImageView serie;

	@FXML
	private ImageView serie_manager;

	@FXML
	private ImageView user_manager;

	public static Stage dashboardStage;

	public UserDashboardController() {

	}

	@FXML
	public void initialize() {

		int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
		List<Episode> todaysEpisodes = DatabaseUtil.fetchTodaysEpisodes(userId);
		showNewEpisodeAlerts(todaysEpisodes);
		handleHomeClick();
		if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
			setHoverEffect(btnm1, actor_manager);
			setHoverEffect(btnm2, producer);
			setHoverEffect(btnm3, serie_manager);
			setHoverEffect(btnm4, season_manager);
			setHoverEffect(btnm5, episode);
			setHoverEffect(btnm6, movie_manager);
			setHoverEffect(btnm7, user_manager);
			setHoverEffect(btnm8, serie_role);
			setHoverEffect(btnm9, movie_role);
			setHoverEffect(btnrank, rank);
		} else if ("Producer".equals(DatabaseUtil.readUserFromFile().getType())) {
			setHoverEffect(btnm3, serie_manager);
			setHoverEffect(btnm4, season_manager);
			setHoverEffect(btnm5, episode);
			setHoverEffect(btnm6, movie_manager);
			setHoverEffect(btnm8, serie_role);
			setHoverEffect(btnm9, movie_role);
		}

		setHoverEffect(btnhome, home);
		setHoverEffect(btnfavorite, heart);
		setHoverEffect(btnschedule, calen);
		setHoverEffect(btnserie, serie);
		setHoverEffect(btnmovie, film);

		setFontForAllButtons();
	}

	private void setFontForAllButtons() {
		Font bebasNeueFont = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 20);
		if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
			List<Button> buttons = new ArrayList<>(Arrays.asList(btnfavorite, btnhome, btnm1, btnm2, btnm3, btnm4,
					btnm5, btnm6, btnm7, btnm8, btnm9, btnmovie, btnrank, btnschedule, btnserie));

		} else if ("Producer".equals(DatabaseUtil.readUserFromFile().getType())) {
			List<Button> buttons = new ArrayList<>(Arrays.asList(btnfavorite, btnhome, btnm3, btnm4, btnm5, btnm6,
					btnm8, btnm9, btnmovie, btnschedule, btnserie));

		}
		List<Button> buttons = new ArrayList<>(Arrays.asList(btnfavorite, btnhome, btnmovie, btnschedule, btnserie));

		for (Button button : buttons) {
			button.setFont(bebasNeueFont);
		}
	}

	private void setHoverEffect(Button button1, ImageView imageView) {
		if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
			Color sourceColor = Color.web("#000000");
			Color finalColor = Color.web("#AD241B");

			button1.setOnMouseEntered(event -> {
				button1.setStyle(
						"-fx-background-color: #ffffff;    -fx-border-radius: 5px;   -fx-background-radius: 5px; -fx-text-fill: #AD241B;");
				Image inputImage = imageView.getImage();
				Image outputImage = reColor(inputImage, sourceColor, finalColor);
				imageView.setImage(outputImage);
			});

			button1.setOnMouseExited(event -> {
				button1.setStyle(
						"-fx-background-color: #2F3136;-fx-border-radius: 5px;   -fx-background-radius: 5px; -fx-text-fill: #FFFFFF;");
				Image inputImage = imageView.getImage();
				Image originalImage = reColor(inputImage, finalColor, sourceColor);
				imageView.setImage(originalImage);
			});
		}

	}

	private static Image reColor(Image inputImage, Color sourceColor, Color finalColor) {
		int W = (int) inputImage.getWidth();
		int H = (int) inputImage.getHeight();
		WritableImage outputImage = new WritableImage(W, H);
		PixelReader reader = inputImage.getPixelReader();
		PixelWriter writer = outputImage.getPixelWriter();
		float ocR = (float) sourceColor.getRed();
		float ocG = (float) sourceColor.getGreen();
		float ocB = (float) sourceColor.getBlue();
		float ncR = (float) finalColor.getRed();
		float ncG = (float) finalColor.getGreen();
		float ncB = (float) finalColor.getBlue();
		java.awt.Color oldColor = new java.awt.Color(ocR, ocG, ocB);
		java.awt.Color newColor = new java.awt.Color(ncR, ncG, ncB);
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				int argb = reader.getArgb(x, y);
				java.awt.Color pixelColor = new java.awt.Color(argb, true);
				writer.setArgb(x, y, pixelColor.equals(oldColor) ? newColor.getRGB() : pixelColor.getRGB());
			}
		}
		return outputImage;
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

	public HBox getContentPane() {
		return contentPane;
	}

	@FXML
	private void handleProfileClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Profile.fxml"));
			ProfileController profileController = new ProfileController(this);
			loader.setControllerFactory(param -> profileController);
			Parent profileRoot = loader.load();
			Scene profileScene = new Scene(profileRoot);
			profileScene.getStylesheets().add(getClass().getResource("/FXML/Styles/Login.css").toExternalForm());

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
			notificationsScene.getStylesheets().add(getClass().getResource("/FXML/Styles/Login.css").toExternalForm());

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
				alert.setContentText("Series: " + episode.getNom_serie() + "\nEpisode Number: "
						+ episode.getNum_episode() + "\nRelease Date: " + episode.getDate_diffusion());
				alert.showAndWait();
			});
		}
	}

	@FXML
	void handleaddepisode() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EpisodeAdd.fxml"));
			EpisodeAddController episodeAddController = new EpisodeAddController();
			loader.setControllerFactory(param -> episodeAddController);
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
			Role_FilmAddController role_FilmAddController = new Role_FilmAddController();
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
			Role_SerieAddController role_SerieAddController = new Role_SerieAddController();
			loader.setControllerFactory(param -> role_SerieAddController);
			Parent recentPage = loader.load();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(recentPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
