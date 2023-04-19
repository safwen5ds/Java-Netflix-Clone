package org.fsb.FlixFlow.Controllers;

import java.io.IOException;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
	private void handleHomeClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/HomePage.fxml"));
			loader.setControllerFactory(param -> new HomePageController(this));
			Parent homePage = loader.load();
			contentPane.getChildren().clear(); // Clear the previous content
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

	}

	public void handleRecentClick() {
		

	}

	public void handleFavoriteClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/favorite.fxml"));
			loader.setControllerFactory(param -> new FavControler(this));
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
			loader.setControllerFactory(param -> new ScheduleController(this));
			Parent recentPage = loader.load();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(recentPage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void handleMoviesClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/movies.fxml"));
			loader.setControllerFactory(param -> new MovieController(this));
			Parent recentPage = loader.load();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(recentPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleSeriesClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/series.fxml"));
			loader.setControllerFactory(param -> new SerieController(this));
			Parent recentPage = loader.load();
			contentPane.getChildren().clear();
			contentPane.getChildren().add(recentPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
