package org.fsb.FlixFlow.Views;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.fsb.FlixFlow.Controllers.MovieSeriesDetailsController;
import org.fsb.FlixFlow.Controllers.UserDashboardController;

import java.io.IOException;

public class PageNavigationUtil {

	public static void openMovieSeriesDetails(int id, boolean isMovie,
			UserDashboardController userDashboardController) {
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
	}
}
