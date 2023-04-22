package org.fsb.FlixFlow.Controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.fsb.FlixFlow.Models.Acteur;
import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import org.fsb.FlixFlow.Views.PageNavigationUtil;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchSeriesController {

    @FXML
    private TextField serieTitleSearchTextField;

    @FXML
    private TextField releaseYearSearchTextField;

    @FXML
    private TextField genreSearchTextField;

    @FXML
    private TextField languageSearchTextField;

    @FXML
    private TextField countryOfOriginSearchTextField;

    @FXML
    private TextField producerSearchTextField;

    @FXML
    private TextField actorSearchTextField;

    @FXML
    private FlowPane seriesFlowPane;

    private final UserDashboardController userDashboardController;
    private ObservableList<Serie> masterData;
    Font Montessart = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 20);

    public SearchSeriesController(UserDashboardController userDashboardController) {
        this.userDashboardController = userDashboardController;
    }

    @FXML
    public void initialize() {
        loadAllSeries();
        setupSearchFieldListeners();
    }

    private void updateSeriesFlowPane(ObservableList<Serie> series) {
        seriesFlowPane.getChildren().clear();
        loadSerie(series, seriesFlowPane);
    }

    private void loadSerie(List<Serie> series, FlowPane seriesFlowPane) {
        for (Serie serie : series) {
            ImageView poster = new ImageView(new Image(serie.getUrl_image()));
            poster.setFitHeight(250);
            poster.setFitWidth(200);

            Label title = new Label(serie.getNom());
            title.setWrapText(true);
            title.setMaxWidth(150);
            title.setAlignment(Pos.CENTER);
            title.setFont(Montessart);
            VBox mediaContainer = new VBox(5);
            mediaContainer.getChildren().addAll(poster, title);
            mediaContainer.setAlignment(Pos.CENTER);
            mediaContainer.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.5)));
            mediaContainer.setOnMouseClicked(event -> {
                PageNavigationUtil.openMovieSeriesDetails(serie.getId_serie(), false, userDashboardController);
            });

            seriesFlowPane.getChildren().add(mediaContainer);
        }
    }

    private Map<Integer, List<Acteur>> seriesActorsMap;

    private void loadAllSeries() {
        try {
            List<Serie> series = DatabaseUtil.getSeriesSortedByViews();
            masterData = FXCollections.observableArrayList(series);
            seriesActorsMap = new HashMap<>();
            for (Serie serie : series) {
                List<Acteur> actors = DatabaseUtil.getActorsBySerieId(serie.getId_serie());
                seriesActorsMap.put(serie.getId_serie(), actors);
            }
            updateSeriesFlowPane(masterData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setupSearchFieldListeners() {
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(event -> searchSeries());

        serieTitleSearchTextField.setOnKeyReleased(event -> {
            pause.playFromStart();
        });
        releaseYearSearchTextField.setOnKeyReleased(event -> {
            pause.playFromStart();
        });
        genreSearchTextField.setOnKeyReleased(event -> {
            pause.playFromStart();
        });
        languageSearchTextField.setOnKeyReleased(event -> {
            pause.playFromStart();
        });
        countryOfOriginSearchTextField.setOnKeyReleased(event -> {
            pause.playFromStart();
        });
        producerSearchTextField.setOnKeyReleased(event -> {
            pause.playFromStart();
        });
        actorSearchTextField.setOnKeyReleased(event -> {
            pause.playFromStart();
        });
    }


    private void searchSeries() {
        String serieTitleFilter = serieTitleSearchTextField.getText().toLowerCase().trim();
        String releaseYearFilter = releaseYearSearchTextField.getText().toLowerCase().trim();
        String genreFilter = genreSearchTextField.getText().toLowerCase().trim();
        String languageFilter = languageSearchTextField.getText().toLowerCase().trim();
        String countryOfOriginFilter = countryOfOriginSearchTextField.getText().toLowerCase().trim();
        String producerFilter = producerSearchTextField.getText().toLowerCase().trim();
        String actorFilter = actorSearchTextField.getText().toLowerCase().trim();

        List<Serie> filteredData = masterData.stream()
            .filter(serie -> serieTitleFilter.isEmpty() || serie.getNom().toLowerCase().contains(serieTitleFilter))
            .filter(serie -> releaseYearFilter.isEmpty() || String.valueOf(serie.getAnnee_sortie()).contains(releaseYearFilter))
            .filter(serie -> genreFilter.isEmpty() || serie.getNom_genre().toLowerCase().contains(genreFilter))
            .filter(serie -> languageFilter.isEmpty() || serie.getNom_langue().toLowerCase().contains(languageFilter))
            .filter(serie -> countryOfOriginFilter.isEmpty() || serie.getNom_pays().toLowerCase().contains(countryOfOriginFilter))
            .filter(serie -> producerFilter.isEmpty() || serie.getNom_producteur().toLowerCase().contains(producerFilter))
            .filter(serie -> {
                List<Acteur> actors = seriesActorsMap.getOrDefault(serie.getId_serie(), Collections.emptyList());
                return actorFilter.isEmpty() || actors.stream()
                    .anyMatch(actor -> actor.getNom().toLowerCase().contains(actorFilter));
            })
            .collect(Collectors.toList());

        updateSeriesFlowPane(FXCollections.observableArrayList(filteredData));
    }



}