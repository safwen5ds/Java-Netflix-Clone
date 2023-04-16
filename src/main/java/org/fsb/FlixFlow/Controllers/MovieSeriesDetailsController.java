package org.fsb.FlixFlow.Controllers;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fsb.FlixFlow.Models.Commentaire_episode;
import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Commentaire_serie;
import org.fsb.FlixFlow.Models.Commentaire_film;
import java.sql.SQLException;
import java.util.List;

import org.fsb.FlixFlow.Models.CommentaireDisplay;
import org.fsb.FlixFlow.Models.Commentaire_episode;
import org.fsb.FlixFlow.Models.Commentaire_film;
import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Commentaire_serie;
import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    // Add the new constructor
    public MovieSeriesDetailsController(int mediaId, boolean isMovie) {
        this.mediaId = mediaId;
        this.isMovie = isMovie;
      
    }
    @FXML
    private Button Watchtrailer;

    @FXML
    private Button addfav;

    @FXML
    private Button addvote;

    @FXML
    private Label annee;

    @FXML
    private Rectangle backdropImage;

    @FXML
    private TableColumn<CommentaireDisplay , CommentaireDisplay> commentaire;

    @FXML
    private Label genre;

    @FXML
    private Label langue;

    @FXML
    private Label pays;

    @FXML
    private Rectangle posterImage;

    @FXML
    private TableColumn<CommentaireDisplay , CommentaireDisplay > prenom;

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
    public void initialize() {
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

        table();

        Watchtrailer.setOnAction(e -> {
            String url = null;
            try {
                url = isMovie ? DatabaseUtil.getFilmById(mediaId).getUrl_video() : DatabaseUtil.getSerieById(mediaId).getUrl_video();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            openUrlInNewWindow(url);
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


    private void table() {
        ObservableList<CommentaireDisplay> commentaires = FXCollections.observableArrayList();

        try {
            if (isMovie) {
                List<Commentaire_film> commentaireFilms = DatabaseUtil.getCommentaireFilmsByMediaId(mediaId);

                for (Commentaire_film cf : commentaireFilms) {
                    commentaires.add(new CommentaireDisplay(cf.getNom_User(), cf.getContenu()));
                }
            } else {
                List<Commentaire_serie> commentaireSeries = DatabaseUtil.getCommentaireSeriesByMediaId(mediaId);

                for (Commentaire_serie cs : commentaireSeries) {
                    commentaires.add(new CommentaireDisplay(cs.getNom_User(), cs.getContenu()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tab.setItems(commentaires);
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







}
