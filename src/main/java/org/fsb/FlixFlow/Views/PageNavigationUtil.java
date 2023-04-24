package org.fsb.FlixFlow.Views;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import org.fsb.FlixFlow.Controllers.MovieSeriesDetailsController;
import org.fsb.FlixFlow.Controllers.UserDashboardController;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.io.IOException;

public class PageNavigationUtil {

    public static void openMovieSeriesDetails(int id, boolean isMovie,
                                              UserDashboardController userDashboardController) {
    	
        Font bebasNeueFont = Font.loadFont(PageNavigationUtil.class.getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 50);

        if ("admin".equals(DatabaseUtil.readUserFromFile().getType())) {
            try {
                FXMLLoader loader = new FXMLLoader(PageNavigationUtil.class.getResource("/FXML/MovieSeriesDetails.fxml"));
                loader.setControllerFactory(
                        param -> new MovieSeriesDetailsController(id, isMovie, userDashboardController));
                Parent detailsPage = loader.load();

                Platform.runLater(() -> {
                    userDashboardController.getContentPane().getChildren().setAll(detailsPage);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(PageNavigationUtil.class.getResource("/FXML/MovieSeriesDetails_Others.fxml"));
                loader.setControllerFactory(
                        param -> new MovieSeriesDetailsController(id, isMovie, userDashboardController));
                Parent detailsPage = loader.load();

                Platform.runLater(() -> {
                    userDashboardController.getContentPane().getChildren().setAll(detailsPage);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
