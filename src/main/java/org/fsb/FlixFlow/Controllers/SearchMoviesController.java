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
import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import org.fsb.FlixFlow.Views.PageNavigationUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchMoviesController {
	@FXML
	private TextField movieTitleSearchTextField;
	Font Montessart = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 20);

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
	
	private HashMap<Integer, Film> filmsMap = new HashMap<>();

	@FXML
	private FlowPane moviesFlowPane;
	private final UserDashboardController userDashboardController;

	private ObservableList<Film> masterData;

	public SearchMoviesController(UserDashboardController userDashboardController) {
		this.userDashboardController = userDashboardController;
	}

	@FXML
	public void initialize() {
		loadAllMovies();
		setupSearchFieldListeners();
	}

	private void updateMoviesFlowPane(ObservableList<Film> films) {
		moviesFlowPane.getChildren().clear();
		loadFilm(films, moviesFlowPane);
	}

	private void loadFilm(List<Film> movies, FlowPane moviesFlowPane) {
		for (Film film : movies) {
			ImageView poster = new ImageView(new Image(film.getUrl_image()));
			poster.setFitHeight(250);
			poster.setFitWidth(200);

			Label title = new Label(film.getNom());
			title.setWrapText(true);
			title.setMaxWidth(150);
			title.setAlignment(Pos.CENTER);
			title.setFont(Montessart);
			VBox mediaContainer = new VBox(5);
			mediaContainer.getChildren().addAll(poster, title);
			mediaContainer.setAlignment(Pos.CENTER);
			mediaContainer.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.5)));
			mediaContainer.setOnMouseClicked(event -> {
				PageNavigationUtil.openMovieSeriesDetails(film.getId_film(), true, userDashboardController);
			});

			moviesFlowPane.getChildren().add(mediaContainer);
		}
	}

	private Map<Integer, List<Acteur>> moviesActorsMap;

	private void loadAllMovies() {
	    try {
	        List<Film> films = DatabaseUtil.getMoviesSortedByViews();
	        masterData = FXCollections.observableArrayList();
	        moviesActorsMap = new HashMap<>();
	        filmsMap = new HashMap<>();
	        for (Film film : films) {
	            Film movie = DatabaseUtil.getFilmById(film.getId_film()); 
	            if (movie != null) {
	                masterData.add(movie);
	                filmsMap.put(movie.getId_film(), movie);
	                List<Acteur> actors = DatabaseUtil.getActorsByFilmId(movie.getId_film());
	                moviesActorsMap.put(movie.getId_film(), actors);
	            }
	        }
	        updateMoviesFlowPane(masterData);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



	private void setupSearchFieldListeners() {
		setDelayedSearchListener(movieTitleSearchTextField);
		setDelayedSearchListener(releaseYearSearchTextField);
		setDelayedSearchListener(genreSearchTextField);
		setDelayedSearchListener(languageSearchTextField);
		setDelayedSearchListener(countryOfOriginSearchTextField);
		setDelayedSearchListener(producerSearchTextField);
		setDelayedSearchListener(actorSearchTextField);
	}

	private void setDelayedSearchListener(TextField textField) {
		PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		textField.setOnKeyReleased(event -> {
			pause.setOnFinished(e -> searchMovies());
			pause.playFromStart();
		});
	}

	private void searchMovies() {
		String movieTitleFilter = movieTitleSearchTextField.getText().toLowerCase();
		String releaseYearFilter = releaseYearSearchTextField.getText().toLowerCase();
		String genreFilter = genreSearchTextField.getText().toLowerCase();
		String languageFilter = languageSearchTextField.getText().toLowerCase();
		String countryOfOriginFilter = countryOfOriginSearchTextField.getText().toLowerCase();
		String producerFilter = producerSearchTextField.getText().toLowerCase();
		String actorFilter = actorSearchTextField.getText().toLowerCase();

		List<Film> filteredData = masterData.stream()
			    .filter(film -> movieTitleFilter.isEmpty() || film.getNom().toLowerCase().contains(movieTitleFilter))
			    .filter(film -> releaseYearFilter.isEmpty()
			            || String.valueOf(film.getAnnee_sortie()).contains(releaseYearFilter))
			    .filter(film -> genreFilter.isEmpty() || (film.getNom_genre() != null && film.getNom_genre().toLowerCase().contains(genreFilter)))
			    .filter(film -> languageFilter.isEmpty() || film.getNom_langue().toLowerCase().contains(languageFilter))
			    .filter(film -> countryOfOriginFilter.isEmpty()
			            || film.getNom_pays().toLowerCase().contains(countryOfOriginFilter))
			    .filter(film -> producerFilter.isEmpty()
			            || film.getNom_producteur().toLowerCase().contains(producerFilter))
			    .filter(film -> {
			        if (actorFilter.isEmpty()) {
			            return true;
			        }
			        List<Acteur> actors = moviesActorsMap.get(film.getId_film());
			        return actors.stream().anyMatch(actor -> actor.getNom().toLowerCase().contains(actorFilter));
			    }).collect(Collectors.toList());
      for (Film f : filteredData)
      {
    	  System.out.println(f.toString());
      }

		updateMoviesFlowPane(FXCollections.observableArrayList(filteredData));
	}
}