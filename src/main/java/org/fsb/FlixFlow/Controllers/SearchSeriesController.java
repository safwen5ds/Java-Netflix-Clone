package org.fsb.FlixFlow.Controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import org.fsb.FlixFlow.Views.PageNavigationUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

public class SearchSeriesController {
    @FXML
    private TextField seriesTitleSearchField;

    @FXML
    private TextField releaseYearSearchField;

    @FXML
    private FlowPane seriesFlowPane;
  
        private UserDashboardController userDashboardController;

        public SearchSeriesController(UserDashboardController userDashboardController) {
            this.userDashboardController = userDashboardController;
        }

        // ...
    

    @FXML
    public void initialize() {
        seriesTitleSearchField.setOnKeyPressed(this::handleSearchSeries);
        releaseYearSearchField.setOnKeyPressed(this::handleSearchSeries);
    }

    private void handleSearchSeries(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchSeries();
        }
    }

    private void searchSeries() {
        String title = seriesTitleSearchField.getText();
        String releaseYear = releaseYearSearchField.getText();

        try {
            List<Serie> allSeries = DatabaseUtil.getSeriesSortedByViews();
            List<Serie> filteredSeries = allSeries.stream()
                    .filter(serie -> title.isEmpty() || serie.getNom().toLowerCase().contains(title.toLowerCase()))
                    .filter(serie -> releaseYear.isEmpty() || Integer.toString(serie.getAnnee_sortie()).equals(releaseYear))
                    .collect(Collectors.toList());
            HomePageController homePageController = new HomePageController(userDashboardController);
            homePageController.loadSerie(filteredSeries, seriesFlowPane);
            seriesFlowPane.getChildren().forEach(node -> {
                node.setOnMouseClicked(event -> {
                    Serie serie = (Serie) node.getUserData();
                    PageNavigationUtil.openMovieSeriesDetails(serie.getId_serie(), false, userDashboardController);
                });
            });

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
