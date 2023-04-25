package org.fsb.FlixFlow.Utilities;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import org.fsb.FlixFlow.Controllers.ActorRoleDisplay;
import org.fsb.FlixFlow.Models.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String DB_USER = "admin";
	private static final String DB_PASSWORD = "admin";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	public static List<Film> getTrendingMovies() throws SQLException {
		String query = "SELECT * FROM FILM ";
		List<Film> movies = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Film movie = new Film();
				movie.setId_film(resultSet.getInt("ID_FILM"));
				System.out.println(movie.getId_film());
				movie.setNom(resultSet.getString("NOM"));
				movie.setAnnee_sortie(resultSet.getInt("ANNEE_SORTIE"));
				movie.setUrl_film(resultSet.getString("URL_FILM"));
				String imageUrl = resultSet.getString("URL_IMAGE");
				if (imageUrl != null && !imageUrl.isEmpty()) {
					movie.setUrl_image(imageUrl);
					System.out.println("Image URL: " + movie.getUrl_image());
				} else {
					System.out.println("Image URL is null or empty.");
				}
				movie.setUrl_video(resultSet.getString("URL_VIDEO"));
				movie.setVues(resultSet.getInt("VUES"));

				movies.add(movie);
			}
		}

		return movies;
	}

	public static List<Serie> getTrendingSeries() throws SQLException {
		String query = "SELECT * FROM SERIE ";
		List<Serie> seriesList = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Serie series = new Serie();
				series.setId_serie(resultSet.getInt("ID_SERIE"));
				series.setNom(resultSet.getString("NOM"));
				series.setAnnee_sortie(resultSet.getInt("ANNEE_SORTIE"));
				series.setUrl_image(resultSet.getString("URL_IMAGE"));
				series.setUrl_video(resultSet.getString("URL_VIDEO"));
				series.setVues(resultSet.getInt("VUES"));

				seriesList.add(series);
			}
		}
		return seriesList;
	}

	public static List<Film> getMoviesSortedByViews() throws SQLException {
		String query = "SELECT * FROM ADMIN.FILM ORDER BY VUES DESC";
		List<Film> movies = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Film movie = new Film();
				movie.setId_film(resultSet.getInt("ID_FILM"));
				movie.setNom(resultSet.getString("NOM"));
				movie.setAnnee_sortie(resultSet.getInt("ANNEE_SORTIE"));
				movie.setUrl_film(resultSet.getString("URL_FILM"));
				movie.setUrl_image(resultSet.getString("URL_IMAGE"));
				movie.setUrl_video(resultSet.getString("URL_VIDEO"));
				movie.setVues(resultSet.getInt("VUES"));

				movies.add(movie);
			}
		}
		return movies;
	}

	public static List<Serie> getSeriesSortedByViews() throws SQLException {
		String query = "SELECT * FROM ADMIN.SERIE ORDER BY VUES DESC";
		List<Serie> series = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Serie serie = new Serie();
				serie.setId_serie(resultSet.getInt("ID_SERIE"));
				serie.setNom(resultSet.getString("NOM"));
				serie.setAnnee_sortie(resultSet.getInt("ANNEE_SORTIE"));
				serie.setUrl_image(resultSet.getString("URL_IMAGE"));
				serie.setUrl_video(resultSet.getString("URL_VIDEO"));
				serie.setVues(resultSet.getInt("VUES"));

				series.add(serie);
			}
		}
		return series;
	}

	public static Film getFilmById(int id) throws SQLException {
		String query = "SELECT film.*, genre.nom as genre_nom, langue.nom as langue_nom, pays.nom as pays_nom, producteur.nom as producteur_nom "
				+ "FROM film JOIN genre ON film.id_genre = genre.id_genre "
				+ "JOIN langue ON film.id_langue = langue.id_langue "
				+ "JOIN pays ON film.id_pays_origine = pays.id_pays "
				+ "JOIN producteur ON film.id_producteur = producteur.id_producteur " + "WHERE film.id_film = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			Film movie = new Film();
			movie.setId_film(resultSet.getInt("id_film"));
			movie.setNom(resultSet.getString("nom"));
			movie.setAnnee_sortie(resultSet.getInt("annee_sortie"));
			movie.setUrl_film(resultSet.getString("url_film"));
			movie.setUrl_image(resultSet.getString("url_image"));
			movie.setUrl_video(resultSet.getString("url_video"));
			movie.setVues(resultSet.getInt("vues"));
			movie.setId_genre(resultSet.getInt("id_genre"));
			movie.setId_langue(resultSet.getInt("id_langue"));
			movie.setId_pays_origine(resultSet.getInt("id_pays_origine"));
			movie.setId_producteur(resultSet.getInt("id_producteur"));
			movie.setSynopsis(resultSet.getString("synopsis"));
			movie.setId_genre(resultSet.getInt("id_genre"));
			movie.setNom_genre(resultSet.getString("genre_nom"));
			movie.setId_langue(resultSet.getInt("id_langue"));
			movie.setNom_langue(resultSet.getString("langue_nom"));
			movie.setId_pays_origine(resultSet.getInt("id_pays_origine"));
			movie.setNom_pays(resultSet.getString("pays_nom"));
			movie.setId_producteur(resultSet.getInt("id_producteur"));
			movie.setNom_producteur(resultSet.getString("producteur_nom"));

			return movie;
		}
		return null;
	}

	public static Serie getSerieById(int id) throws SQLException {
		String query = "SELECT serie.*, genre.nom as genre_nom, langue.nom as langue_nom, pays.nom as pays_nom, producteur.nom as producteur_nom "
				+ "FROM serie JOIN genre ON serie.id_genre = genre.id_genre "
				+ "JOIN langue ON serie.id_langue = langue.id_langue "
				+ "JOIN pays ON serie.id_pays_origine = pays.id_pays "
				+ "JOIN producteur ON serie.id_producteur = producteur.id_producteur " + "WHERE serie.id_serie = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			Serie serie = new Serie();
			serie.setId_serie(resultSet.getInt("id_serie"));
			serie.setNom(resultSet.getString("nom"));
			serie.setAnnee_sortie(resultSet.getInt("annee_sortie"));
			serie.setUrl_image(resultSet.getString("url_image"));
			serie.setUrl_video(resultSet.getString("url_video"));
			serie.setVues(resultSet.getInt("vues"));
			serie.setId_genre(resultSet.getInt("id_genre"));
			serie.setId_langue(resultSet.getInt("id_langue"));
			serie.setId_pays_origine(resultSet.getInt("id_pays_origine"));
			serie.setId_producteur(resultSet.getInt("id_producteur"));
			serie.setSynopsis(resultSet.getString("synopsis"));
			serie.setId_genre(resultSet.getInt("id_genre"));
			serie.setNom_genre(resultSet.getString("genre_nom"));
			serie.setId_langue(resultSet.getInt("id_langue"));
			serie.setNom_langue(resultSet.getString("langue_nom"));
			serie.setId_pays_origine(resultSet.getInt("id_pays_origine"));
			serie.setNom_pays(resultSet.getString("pays_nom"));
			serie.setId_producteur(resultSet.getInt("id_producteur"));
			serie.setNom_producteur(resultSet.getString("producteur_nom"));

			return serie;
		}
		return null;
	}

	public static List<Commentaire_episode> getCommentaireEpisodesByMediaId(int mediaId) throws SQLException {
		String query = "SELECT commentaire_episode.*, utilisateur.nom as user_name "
				+ "FROM commentaire_episode JOIN utilisateur ON utilisateur.id_utilisateur = commentaire_episode.id_utilisateur "
				+ "WHERE commentaire_episode.id_episode= ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, mediaId);
		ResultSet resultSet = statement.executeQuery();
		List<Commentaire_episode> c1 = new ArrayList<>();

		while (resultSet.next()) {
			Commentaire_episode c = new Commentaire_episode();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_episode(resultSet.getInt("id_episode"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
			c.setComment_id(resultSet.getInt("id_commentaire"));
			c1.add(c);

		}
		return c1;

	}

	public static List<Commentaire_saison> getCommentaireSaisonsByMediaId(int mediaId) throws SQLException {
		String query = "SELECT commentaire_saison.*, utilisateur.nom as user_name "
				+ "FROM commentaire_saison JOIN utilisateur ON utilisateur.id_utilisateur = commentaire_saison.id_utilisateur "
				+ "WHERE commentaire_saison.id_saison= ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, mediaId);
		ResultSet resultSet = statement.executeQuery();
		List<Commentaire_saison> c1 = new ArrayList<>();

		while (resultSet.next()) {
			Commentaire_saison c = new Commentaire_saison();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_saison(resultSet.getInt("id_saison"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
			c.setComment_id(resultSet.getInt("id_commentaire"));
			c1.add(c);

		}
		return c1;

	}

	public static List<Commentaire_serie> getCommentaireSeriesByMediaId(int mediaId) throws SQLException {
		String query = "SELECT commentaire_serie.*, utilisateur.nom as user_name "
				+ "FROM commentaire_serie JOIN utilisateur ON utilisateur.id_utilisateur = commentaire_serie.id_utilisateur "
				+ "WHERE commentaire_serie.id_serie= ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, mediaId);
		ResultSet resultSet = statement.executeQuery();
		List<Commentaire_serie> c1 = new ArrayList<>();

		while (resultSet.next()) {
			Commentaire_serie c = new Commentaire_serie();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_serie(resultSet.getInt("id_serie"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
			c.setComment_id(resultSet.getInt("id_commentaire"));
			c1.add(c);

		}
		return c1;

	}

	public static List<Commentaire_film> getCommentaireFilmsByMediaId(int mediaId) throws SQLException {
		String query = "SELECT commentaire_film.*, utilisateur.nom as user_name "
				+ "FROM commentaire_film JOIN utilisateur ON utilisateur.id_utilisateur = commentaire_film.id_utilisateur "
				+ "WHERE commentaire_film.id_film= ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, mediaId);
		ResultSet resultSet = statement.executeQuery();
		List<Commentaire_film> c1 = new ArrayList<>();

		while (resultSet.next()) {
			Commentaire_film c = new Commentaire_film();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_film(resultSet.getInt("id_film"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
			c.setComment_id(resultSet.getInt("id_commentaire"));
			c1.add(c);

		}
		return c1;

	}

	public static List<Saison> getSaisonBySerieId(int serieId) throws SQLException {
		String query = "SELECT saison.*, serie.nom AS serie_name FROM saison JOIN serie ON saison.id_serie = serie.id_serie WHERE serie.id_serie = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, serieId);
		ResultSet resultSet = statement.executeQuery();
		List<Saison> seasons = new ArrayList<>();

		while (resultSet.next()) {
			Saison season = new Saison();
			season.setDate_debut(resultSet.getDate("date_debut"));
			season.setId_saison(resultSet.getInt("id_saison"));
			season.setId_serie(resultSet.getInt("id_serie"));
			season.setNom_serie(resultSet.getString("serie_name"));
			season.setNum_saison(resultSet.getInt("num_saison"));
			season.setSynopsis(resultSet.getString("synopsis"));
			season.setUrl_image(resultSet.getString("url_image"));
			season.setUrl_video(resultSet.getString("url_video"));
			season.setVues(resultSet.getInt("vues"));
			seasons.add(season);
		}

		return seasons;
	}

	private static final String USER_FILE = "user.txt";
	private static final SimpleDateFormat DATE_FORMAT;

	static {
		DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	}

	public static boolean checkUserCredentials(String email, String password) {
		String query = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Utilisateur user = new Utilisateur();
					user.setId_utilisateur(resultSet.getInt("id_utilisateur"));
					user.setNom(resultSet.getString("nom"));
					user.setPrenom(resultSet.getString("prenom"));
					user.setEmail(resultSet.getString("email"));
					user.setMot_de_passe(resultSet.getString("mot_de_passe"));
					user.setDate_de_naissance(resultSet.getDate("date_de_naissance"));
					user.setType(resultSet.getString("type"));

					storeUserToFile(user);
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void storeUserToFile(Utilisateur user) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(USER_FILE), StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING)) {
			String sb = user.getId_utilisateur() + "," + user.getNom() + "," + user.getPrenom() + "," + user.getEmail()
					+ "," + user.getMot_de_passe() + "," + DATE_FORMAT.format(user.getDate_de_naissance()) + ","
					+ user.getType();

			String line = sb.replace("\n", "").replace("\r", "");
			writer.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteUserFromFile() {
		try {
			Files.deleteIfExists(Paths.get(USER_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Utilisateur readUserFromFile() {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(USER_FILE))) {
			String line = reader.readLine();
			if (line != null) {
				System.out.println("Reading line: " + line);
				String[] tokens = line.split(",");
				if (tokens.length < 7) {
					System.err.println("Error: The input file has fewer fields than expected.");
					return null;
				}
				Utilisateur user = new Utilisateur();
				user.setId_utilisateur(Integer.parseInt(tokens[0]));
				user.setNom(tokens[1]);
				user.setPrenom(tokens[2]);
				user.setEmail(tokens[3]);
				user.setMot_de_passe(tokens[4]);
				user.setDate_de_naissance(DATE_FORMAT.parse(tokens[5]));
				user.setType(tokens[6]);

				return user;
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Episode> getEpisodeByIds(int saisonId, int serieId) throws SQLException {
		String query = "select episode.*, serie.nom as serie_name, saison.num_saison as num_saison from episode join saison on episode.id_saison = saison.id_saison join serie on saison.id_serie = serie.id_serie where episode.id_saison = ? and episode.id_serie = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, saisonId);
		statement.setInt(2, serieId); 
		ResultSet resultSet = statement.executeQuery();
		List<Episode> c1 = new ArrayList<>();
		while (resultSet.next()) {
			Episode c = new Episode();
			c.setDate_diffusion(resultSet.getDate("date_diffusion"));
			c.setId_saison(resultSet.getInt("id_saison"));
			c.setId_serie(resultSet.getInt("id_serie"));
			c.setId_episode(resultSet.getInt("id_episode"));
			c.setNom_serie(resultSet.getString("serie_name"));
			c.setNum_saison(resultSet.getInt("num_saison"));
			c.setNum_episode(resultSet.getInt("num_episode"));
			c.setSynopsis(resultSet.getString("synopsis"));
			c.setUrl_episode(resultSet.getString("url_episode"));
			c.setVues(resultSet.getInt("vues"));
			c1.add(c);
		}

		return c1;
	}

	public static List<ActorRoleDisplay> getActorRolesForMovie(int filmId) throws SQLException {
		String query = "SELECT A.*, RF.ROLE_TYPE, RF.URL_IMAGE,RF.ID_ACTEUR FROM ACTEUR A "
				+ "JOIN ROLE_FILM RF ON A.ID_ACTEUR = RF.ID_ACTEUR " + "WHERE RF.ID_FILM = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, filmId);
		ResultSet resultSet = statement.executeQuery();
		List<ActorRoleDisplay> actorRoles = new ArrayList<>();
		while (resultSet.next()) {
			ActorRoleDisplay actorRole = new ActorRoleDisplay(resultSet.getString("URL_IMAGE"),
					resultSet.getString("NOM"), resultSet.getString("ROLE_TYPE"), resultSet.getInt("ID_ACTEUR"));
			actorRoles.add(actorRole);
		}
		return actorRoles;
	}

	public static List<ActorRoleDisplay> getActorRolesForSeries(int serieId) throws SQLException {
		String query = "SELECT A.*, RS.ROLE_TYPE, RS.URL_IMAGE FROM ACTEUR A "
				+ "JOIN ROLE_SERIE RS ON A.ID_ACTEUR = RS.ID_ACTEUR " + "JOIN SAISON S ON S.ID_SAISON = RS.ID_SAISON "
				+ "WHERE S.ID_SERIE = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, serieId);
		ResultSet resultSet = statement.executeQuery();
		List<ActorRoleDisplay> actorRoles = new ArrayList<>();
		while (resultSet.next()) {
			ActorRoleDisplay actorRole = new ActorRoleDisplay(resultSet.getString("URL_IMAGE"),
					resultSet.getString("NOM"), resultSet.getString("ROLE_TYPE"), resultSet.getInt("ID_ACTEUR"));
			actorRoles.add(actorRole);
		}
		return actorRoles;
	}

	public static Acteur extractActeurFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("ID_ACTEUR");
		String nom = resultSet.getString("NOM");

		Acteur acteur = new Acteur(id, nom);
		return acteur;
	}

	public static List<Acteur> getActorsByFilmId(int filmId) throws SQLException {
		Connection connection = getConnection();

		String query = "SELECT A.* FROM ACTEUR A " + "JOIN ROLE_FILM RF ON A.ID_ACTEUR = RF.ID_ACTEUR "
				+ "WHERE RF.ID_FILM = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, filmId);
		ResultSet resultSet = statement.executeQuery();
		List<Acteur> actors = new ArrayList<>();
		while (resultSet.next()) {
			actors.add(extractActeurFromResultSet(resultSet));
		}
		return actors;
	}

	public static List<Acteur> getActorsBySerieId(int serieId) throws SQLException {
		Connection connection = getConnection();

		String query = "SELECT A.* FROM ACTEUR A " + "JOIN ROLE_SERIE RS ON A.ID_ACTEUR = RS.ID_ACTEUR "
				+ "JOIN SAISON S ON S.ID_SAISON = RS.ID_SAISON " + "WHERE S.ID_SERIE = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, serieId);
		ResultSet resultSet = statement.executeQuery();
		List<Acteur> actors = new ArrayList<>();
		while (resultSet.next()) {
			actors.add(extractActeurFromResultSet(resultSet));
		}
		return actors;
	}

	public static boolean updateUserInDatabase(Utilisateur user) {
		String query = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, date_de_naissance = ?, type = ? WHERE id_utilisateur = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, user.getNom());
			preparedStatement.setString(2, user.getPrenom());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getMot_de_passe());
			preparedStatement.setDate(5, new java.sql.Date(user.getDate_de_naissance().getTime()));
			preparedStatement.setString(6, user.getType());
			preparedStatement.setInt(7, user.getId_utilisateur());

			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteUserFromDatabase(int id_utilisateur) {
		String query = "DELETE FROM utilisateur WHERE id_utilisateur = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id_utilisateur);

			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int addPreferenceFilm(int userId, int filmId) throws SQLException {
		if (preferenceFilmExists(userId, filmId)) {
			return 0;

		} else {
			String sql = "INSERT INTO PREFERENCES_FILM (ID_UTILISATEUR, ID_FILM) VALUES (?, ?)";
			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, filmId);
				preparedStatement.executeUpdate();
			}
		}
		return 1;
	}

	public static int addPreferenceSerie(int userId, int serieId) throws SQLException {
		if (preferenceSerieExists(userId, serieId)) {
			return 0;

		} else {
			String sql = "INSERT INTO PREFERENCES_SERIE (ID_UTILISATEUR, ID_SERIE) VALUES (?, ?)";
			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, serieId);
				preparedStatement.executeUpdate();
			}
		}
		return 1;
	}

	public static boolean preferenceFilmExists(int userId, int filmId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM PREFERENCES_FILM WHERE ID_UTILISATEUR = ? AND ID_FILM = ?";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, filmId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) > 0;
		}
	}

	public static boolean preferenceSerieExists(int userId, int serieId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM PREFERENCES_SERIE WHERE ID_UTILISATEUR = ? AND ID_SERIE = ?";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, serieId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) > 0;
		}
	}

	public static void submitRating(Slider ratingSlider, boolean isMovie, int mediaid) {
		int score = (int) ratingSlider.getValue();
		Utilisateur user = DatabaseUtil.readUserFromFile();
		int userId = user.getId_utilisateur();
		int mediaId = mediaid;

		try {
			if ((isMovie && ratingFilmExists(userId, mediaId)) || (!isMovie && ratingSerieExists(userId, mediaId))) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Rating already exists");
					alert.setContentText("You have already rated this film/serie.");
					alert.showAndWait();
				});
			} else {
				String query = null;
				if (isMovie) {
					query = "INSERT INTO ADMIN.SCORE_FILM (ID_UTILISATEUR, ID_FILM, SCORE) VALUES (?, ?, ?)";
				} else {
					query = "INSERT INTO ADMIN.SCORE_SERIE (ID_UTILISATEUR, ID_SERIE, SCORE) VALUES (?, ?, ?)";
				}

				try {
					Connection con = getConnection();
					PreparedStatement pstmt = con.prepareStatement(query);
					pstmt.setInt(1, userId);
					pstmt.setInt(2, mediaId);
					pstmt.setInt(3, score);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static boolean ratingFilmExists(int userId, int filmId) throws SQLException {
		String query = "SELECT COUNT(*) FROM ADMIN.SCORE_FILM WHERE ID_UTILISATEUR = ? AND ID_FILM = ?";
		try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			pstmt.setInt(2, filmId);
			ResultSet resultSet = pstmt.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) > 0;
		}
	}

	public static boolean ratingSerieExists(int userId, int serieId) throws SQLException {
		String query = "SELECT COUNT(*) FROM ADMIN.SCORE_SERIE WHERE ID_UTILISATEUR = ? AND ID_SERIE = ?";
		try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, userId);
			pstmt.setInt(2, serieId);
			ResultSet resultSet = pstmt.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) > 0;
		}
	}

	public static void submitSaisonRating(int userId, int saisonId, int score) {
		String checkQuery = "SELECT COUNT(*) FROM ADMIN.SCORE_SAISON WHERE ID_UTILISATEUR = ? AND ID_SAISON = ?";
		String insertQuery = "INSERT INTO ADMIN.SCORE_SAISON (ID_UTILISATEUR, ID_SAISON, SCORE) VALUES (?, ?, ?)";
		String updateQuery = "UPDATE ADMIN.SCORE_SAISON SET SCORE = ? WHERE ID_UTILISATEUR = ? AND ID_SAISON = ?";

		try {
			Connection con = getConnection();
			PreparedStatement checkStmt = con.prepareStatement(checkQuery);
			checkStmt.setInt(1, userId);
			checkStmt.setInt(2, saisonId);

			ResultSet rs = checkStmt.executeQuery();
			rs.next();
			PreparedStatement pstmt;
			if (rs.getInt(1) == 0) {
				pstmt = con.prepareStatement(insertQuery);
				pstmt.setInt(1, userId);
				pstmt.setInt(2, saisonId);
				pstmt.setInt(3, score);
			} else {
				pstmt = con.prepareStatement(updateQuery);
				pstmt.setInt(1, score);
				pstmt.setInt(2, userId);
				pstmt.setInt(3, saisonId);

				showAlert("Rating Updated", "Your rating for this season has been updated.");
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void submitEpisodeRating(int userId, int episodeId, int score) {
		String checkQuery = "SELECT COUNT(*) FROM ADMIN.SCORE_EPISODE WHERE ID_UTILISATEUR = ? AND ID_EPISODE = ?";
		String insertQuery = "INSERT INTO ADMIN.SCORE_EPISODE (ID_UTILISATEUR, ID_EPISODE, SCORE) VALUES (?, ?, ?)";
		String updateQuery = "UPDATE ADMIN.SCORE_EPISODE SET SCORE = ? WHERE ID_UTILISATEUR = ? AND ID_EPISODE = ?";

		try {
			Connection con = getConnection();
			PreparedStatement checkStmt = con.prepareStatement(checkQuery);
			checkStmt.setInt(1, userId);
			checkStmt.setInt(2, episodeId);

			ResultSet rs = checkStmt.executeQuery();
			rs.next();
			PreparedStatement pstmt;
			if (rs.getInt(1) == 0) {
				pstmt = con.prepareStatement(insertQuery);
				pstmt.setInt(1, userId);
				pstmt.setInt(2, episodeId);
				pstmt.setInt(3, score);
			} else {
				pstmt = con.prepareStatement(updateQuery);
				pstmt.setInt(1, score);
				pstmt.setInt(2, userId);
				pstmt.setInt(3, episodeId);

				showAlert("Rating Updated", "Your rating for this episode has been updated.");
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void showAlert(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void addPreference(int id_utilisateur, int id_acteur) throws SQLException {
		if (isActorInFavorites(id_utilisateur, id_acteur)) {
			throw new SQLException("Actor already exists in favorites");
		}

		String query = "INSERT INTO preferences_acteur(id_utilisateur, id_acteur) VALUES (?, ?)";

		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_utilisateur);
			preparedStatement.setInt(2, id_acteur);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding preference", e);
		}
	}

	public static boolean isActorInFavorites(int id_utilisateur, int id_acteur) throws SQLException {
		String query = "SELECT COUNT(*) FROM preferences_acteur WHERE id_utilisateur = ? AND id_acteur = ?";

		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_utilisateur);
			preparedStatement.setInt(2, id_acteur);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Error while checking actor in favorites", e);
		}
	}

	public static void updateCommentForFilm(int comment_id, String newContent) throws SQLException {
		String query = "UPDATE commentaire_film SET contenu = ? WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setString(1, newContent);
			preparedStatement.setInt(2, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating comment for film", e);
		}
	}

	public static void updateCommentForSeries(int comment_id, String newContent) throws SQLException {
		String query = "UPDATE commentaire_serie SET contenu = ? WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setString(1, newContent);
			preparedStatement.setInt(2, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating comment for series", e);
		}
	}

	public static void deleteCommentForFilm(int comment_id) throws SQLException {
		String query = "DELETE FROM commentaire_film WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting comment for film", e);
		}
	}

	public static void deleteCommentForSeries(int comment_id) throws SQLException {
		String query = "DELETE FROM commentaire_serie WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting comment for series", e);
		}
	}

	public static void addCommentForFilm(int id_utilisateur, int id_film, String content) throws SQLException {
		String query = "INSERT INTO commentaire_film (id_utilisateur, id_film, contenu) VALUES (?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_utilisateur);
			preparedStatement.setInt(2, id_film);
			preparedStatement.setString(3, content);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding comment for series", e);
		}
	}

	public static void addCommentForSeries(int id_utilisateur, int id_serie, String content) throws SQLException {
		String query = "INSERT INTO commentaire_serie (id_utilisateur, id_serie, contenu) VALUES (?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_utilisateur);
			preparedStatement.setInt(2, id_serie);
			preparedStatement.setString(3, content);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding comment for series", e);
		}
	}

	public static ObservableList<String> getData(String query) {
		ObservableList<String> data = FXCollections.observableArrayList();
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
				data.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void updateCommentForEpisode(int comment_id, String newContent) throws SQLException {
		String query = "UPDATE commentaire_episode SET contenu = ? WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setString(1, newContent);
			preparedStatement.setInt(2, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating comment for episode", e);
		}
	}

	public static void deleteCommentForEpisode(int comment_id) throws SQLException {
		String query = "DELETE FROM commentaire_episode WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting comment for episode", e);
		}
	}

	public static void addCommentForEpisode(int id_utilisateur, int id_episode, String content) throws SQLException {
		String query = "INSERT INTO commentaire_episode (id_utilisateur, id_episode, contenu) VALUES (?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_utilisateur);
			preparedStatement.setInt(2, id_episode);
			preparedStatement.setString(3, content);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding comment for episode", e);
		}
	}

	public static void updateCommentForSeason(int comment_id, String newContent) throws SQLException {
		String query = "UPDATE commentaire_saison SET contenu = ? WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setString(1, newContent);
			preparedStatement.setInt(2, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating comment for season", e);
		}
	}

	public static void deleteCommentForSeason(int comment_id) throws SQLException {
		String query = "DELETE FROM commentaire_saison WHERE id_commentaire = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, comment_id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting comment for season", e);
		}
	}

	public static void addCommentForSeason(int id_utilisateur, int id_saison, String content) throws SQLException {
		String query = "INSERT INTO commentaire_saison (id_utilisateur, id_saison, contenu) VALUES (?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_utilisateur);
			preparedStatement.setInt(2, id_saison);
			preparedStatement.setString(3, content);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding comment for season", e);
		}
	}

	public static void incrementEpisodeViews(int id_episode) throws SQLException {
		String query = "UPDATE episode SET VUES = VUES + 1 WHERE ID_EPISODE = ?";
		Connection con = getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, id_episode);
			stmt.executeUpdate();
		}
	}

	public static void incrementSeasonViews(int id_saison) throws SQLException {
		String query = "UPDATE saison SET VUES = VUES + 1 WHERE ID_SAISON = ?";
		Connection con = getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, id_saison);
			stmt.executeUpdate();
		}
	}

	public static boolean hasUserSeenEpisode(int id_utilisateur, int id_episode) throws SQLException {
		String query = "SELECT * FROM utilisateur_vue WHERE ID_UTILISATEUR = ? AND ID_EPISODE = ?";
		Connection con = getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, id_utilisateur);
			stmt.setInt(2, id_episode);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}
	}

	public static void addUserView(int id_utilisateur, int id_episode) throws SQLException {
		String query = "INSERT INTO utilisateur_vue (ID_UTILISATEUR, ID_EPISODE) VALUES (?, ?)";
		Connection con = getConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, id_utilisateur);
			stmt.setInt(2, id_episode);
			stmt.executeUpdate();
		}
	}

	public static void calculateTotalSeriesViews(int serieId) throws SQLException {
		String query = "SELECT * FROM saison WHERE ID_SERIE = ?";
		PreparedStatement pstmt = getConnection().prepareStatement(query);
		pstmt.setInt(1, serieId);
		ResultSet rs = pstmt.executeQuery();

		int totalViews = 0;

		while (rs.next()) {
			totalViews += rs.getInt("VUES");
		}

		String updateQuery = "UPDATE serie SET VUES = ? WHERE ID_SERIE = ?";
		PreparedStatement updateStmt = getConnection().prepareStatement(updateQuery);
		updateStmt.setInt(1, totalViews);
		updateStmt.setInt(2, serieId);

		updateStmt.executeUpdate();
	}

	public static boolean hasUserSeenFilm(int userId, int filmId) throws SQLException {
		String query = "SELECT * FROM utilisateur_vue_film WHERE ID_UTILISATEUR = ? AND ID_FILM = ?";
		PreparedStatement pstmt = getConnection().prepareStatement(query);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, filmId);
		ResultSet rs = pstmt.executeQuery();

		return rs.next();
	}

	public static void addUserFilmView(int userId, int filmId) throws SQLException {
		String query = "INSERT INTO utilisateur_vue_film (ID_UTILISATEUR, ID_FILM) VALUES (?, ?)";
		PreparedStatement pstmt = getConnection().prepareStatement(query);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, filmId);
		pstmt.executeUpdate();
	}

	public static void incrementFilmViews(int filmId) throws SQLException {
		String query = "UPDATE film SET vues = vues + 1 WHERE ID_FILM = ?";
		PreparedStatement pstmt = getConnection().prepareStatement(query);
		pstmt.setInt(1, filmId);
		pstmt.executeUpdate();
	}

	public static boolean isGenreFavExists(int userId, int genreId) throws SQLException {
		String query = "SELECT * FROM preferences_genre WHERE id_utilisateur = ? AND id_genre = ?";
		PreparedStatement pstmt = getConnection().prepareStatement(query);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, genreId);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}

	public static void addPreferenceGenre(int userId, int genreId) throws SQLException {
		String query = "INSERT INTO preferences_genre (id_utilisateur, id_genre) VALUES (?, ?)";
		PreparedStatement pstmt = getConnection().prepareStatement(query);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, genreId);
		pstmt.executeUpdate();
	}

	public static double calculateAverageEpisodeScore(int episodeId) throws SQLException {
		String query = "SELECT AVG(score) FROM score_episode WHERE id_episode = ?";
		return calculateAverage(query, episodeId);
	}

	public static double calculateAverageSeasonScore(int seasonId) throws SQLException {
		String query = "SELECT AVG(score) FROM score_saison WHERE id_saison = ?";
		return calculateAverage(query, seasonId);
	}

	public static double calculateAverageSeriesScore(int seriesId) throws SQLException {
		String query = "SELECT AVG(score) FROM score_serie WHERE id_serie = ?";
		return calculateAverage(query, seriesId);
	}

	public static double calculateAverageFilmScore(int filmId) throws SQLException {
		String query = "SELECT AVG(score) FROM score_film WHERE id_film = ?";
		return calculateAverage(query, filmId);
	}

	private static double calculateAverage(String query, int id) throws SQLException {
		double average = 0.0;

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				average = resultSet.getDouble(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return average;
	}

	public static List<Notification> getEpisodeNotifications() {
		List<Notification> notifications = new ArrayList<>();

		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			String query = "SELECT E.ID_EPISODE, E.ID_SERIE, E.NUM_EPISODE, E.DATE_DIFFUSION, S.NOM, SA.NUM_SAISON FROM EPISODE E INNER JOIN SERIE S ON E.ID_SERIE = S.ID_SERIE INNER JOIN SAISON SA ON E.ID_SAISON = SA.ID_SAISON WHERE E.DATE_DIFFUSION = TRUNC(SYSDATE) AND E.ID_SERIE IN ( SELECT P.ID_SERIE FROM PREFERENCES_SERIE P UNION SELECT SC.ID_SERIE FROM SCORE_SERIE SC WHERE SC.SCORE >= 5)";

			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Notification notification = new Notification();
				notification.setIdEpisode(resultSet.getInt("ID_EPISODE"));
				notification.setIdSerie(resultSet.getInt("ID_SERIE"));
				notification.setNumEpisode(resultSet.getInt("NUM_EPISODE"));
				notification.setDateDiffusion(resultSet.getDate("DATE_DIFFUSION"));
				notification.setSerieNom(resultSet.getString("NOM"));
				notification.setNum_saison(resultSet.getInt("NUM_SAISON"));
				notifications.add(notification);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notifications;
	}

	public static List<Episode> fetchTodaysEpisodes(int userId) {
	    List<Episode> episodes = new ArrayList<>();

	    String selectEpisodesQuery = "SELECT E.ID_EPISODE, E.ID_SERIE, E.NUM_EPISODE, E.DATE_DIFFUSION, S.NOM, SA.NUM_SAISON FROM EPISODE E INNER JOIN SERIE S ON E.ID_SERIE = S.ID_SERIE INNER JOIN SAISON SA ON E.ID_SAISON = SA.ID_SAISON WHERE E.DATE_DIFFUSION = TRUNC(SYSDATE) AND E.ID_SERIE IN ( SELECT P.ID_SERIE FROM PREFERENCES_SERIE P WHERE P.ID_UTILISATEUR = ? UNION SELECT SC.ID_SERIE FROM SCORE_SERIE SC WHERE SC.ID_UTILISATEUR = ? AND SC.SCORE >= 5)";

	    try (Connection connection = getConnection();
	            PreparedStatement preparedStatement = connection.prepareStatement(selectEpisodesQuery)) {

	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, userId);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            int idSerie = resultSet.getInt("ID_SERIE");
	            int numEpisode = resultSet.getInt("NUM_EPISODE");
	            java.sql.Date dateDiffusion = resultSet.getDate("DATE_DIFFUSION");

	            String selectSeriesNameQuery = "SELECT NOM FROM SERIE WHERE ID_SERIE = ?";
	            String serieNom = "";
	            try (PreparedStatement seriesStatement = connection.prepareStatement(selectSeriesNameQuery)) {
	                seriesStatement.setInt(1, idSerie);
	                ResultSet seriesResultSet = seriesStatement.executeQuery();
	                if (seriesResultSet.next()) {
	                    serieNom = seriesResultSet.getString("NOM");
	                }
	            }

	            Episode episode = new Episode();
	            episode.setId_serie(idSerie);
	            episode.setNum_episode(numEpisode);
	            episode.setDate_diffusion(dateDiffusion);
	            episode.setNom_serie(serieNom);

	            episodes.add(episode);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return episodes;
	}


	public static int getVoteCountForFilm(int filmId) throws SQLException {
		String query = "SELECT COUNT(*) FROM score_film WHERE id_film = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, filmId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	public static int getVoteCountForSeries(int serieId) throws SQLException {
		String query = "SELECT COUNT(*) FROM score_serie WHERE id_serie = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, serieId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	public static int getVoteCountForSeason(int seasonId) throws SQLException {
		String query = "SELECT COUNT(*) FROM score_saison WHERE id_saison = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, seasonId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	public static int getTotalEpisodesForSeason(int seasonId) throws SQLException {
		String query = "SELECT COUNT(*) FROM episode WHERE id_saison = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, seasonId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	public static int getTotalRatingsForEpisode(int episodeId) throws SQLException {
		String query = "SELECT COUNT(*) FROM score_episode WHERE id_episode = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, episodeId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	public static List<SeriesRanking> getSeriesRanking(LocalDate startDate, LocalDate endDate) {
		List<SeriesRanking> seriesRankings = new ArrayList<>();
		String sql = "WITH series_views AS (SELECT s.ID_SERIE, s.NOM, COUNT(uv.ID_VUE) AS VIEWS FROM \"ADMIN\".\"SERIE\" s JOIN \"ADMIN\".\"EPISODE\" e ON s.ID_SERIE = e.ID_SERIE JOIN \"ADMIN\".\"UTILISATEUR_VUE\" uv ON e.ID_EPISODE = uv.ID_EPISODE WHERE uv.DATE_CREATION BETWEEN ? AND ? GROUP BY s.ID_SERIE, s.NOM) SELECT ID_SERIE, NOM, VIEWS, RANK() OVER (ORDER BY VIEWS DESC) AS RANK FROM series_views";


		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
			preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int idSerie = resultSet.getInt("ID_SERIE");
				String nom = resultSet.getString("NOM");
				int views = resultSet.getInt("VIEWS");
				int rank = resultSet.getInt("RANK");
				seriesRankings.add(new SeriesRanking(idSerie, nom, views, rank));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return seriesRankings;
	}

	public static boolean insertActeur(Acteur acteur) {
		String sql = "INSERT INTO acteur (id_acteur, nom) VALUES (?, ?) ";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, acteur.getId_acteur());
			pstmt.setString(2, acteur.getNom());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static Acteur getActeurById(int id) {
		String sql = "SELECT * FROM acteur WHERE id_acteur = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Acteur(rs.getInt("id_acteur"), rs.getString("nom"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static List<Acteur> getAllActeurs() {
		List<Acteur> acteurs = new ArrayList<>();
		String sql = "SELECT * FROM acteur";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				acteurs.add(new Acteur(rs.getInt("id_acteur"), rs.getString("nom")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return acteurs;
	}

	public static boolean updateActeur(Acteur acteur) {
		String sql = "UPDATE acteur SET nom = ? WHERE id_acteur = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, acteur.getNom());
			pstmt.setInt(2, acteur.getId_acteur());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static boolean deleteActeur(int id) {
		String sql = "DELETE FROM acteur WHERE id_acteur = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static boolean insertProducteur(Producteur producteur) {
		String sql = "INSERT INTO producteur (id_producteur, nom) VALUES (?, ?)";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, producteur.getId_producteur());
			pstmt.setString(2, producteur.getNom());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static Producteur getProducteurById(int id) {
		String sql = "SELECT * FROM producteur WHERE id_producteur = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Producteur(rs.getInt("id_producteur"), rs.getString("nom"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static List<Producteur> getAllProducteurs() {
		List<Producteur> producteurs = new ArrayList<>();
		String sql = "SELECT * FROM producteur";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				producteurs.add(new Producteur(rs.getInt("id_producteur"), rs.getString("nom")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return producteurs;
	}

	public static boolean updateProducteur(Producteur producteur) {
		String sql = "UPDATE producteur SET nom = ? WHERE id_producteur = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, producteur.getNom());
			pstmt.setInt(2, producteur.getId_producteur());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static boolean deleteProducteur(int id) {
		String sql = "DELETE FROM producteur WHERE id_producteur = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static void createFilm(Film film) throws SQLException {
		String query = "INSERT INTO film (id_film,nom, annee_sortie, url_film, url_image, url_video, vues, id_genre, id_langue, id_pays_origine, id_producteur, synopsis) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			
			preparedStatement.setInt(1, film.getId_film());
			preparedStatement.setString(2, film.getNom());
			preparedStatement.setInt(3, film.getAnnee_sortie());
			preparedStatement.setString(4, film.getUrl_film());
			preparedStatement.setString(5, film.getUrl_image());
			preparedStatement.setString(6, film.getUrl_video());
			preparedStatement.setInt(7, film.getVues());
			preparedStatement.setInt(8, film.getId_genre());
			preparedStatement.setInt(9, film.getId_langue());
			preparedStatement.setInt(10, film.getId_pays_origine());
			preparedStatement.setInt(11, film.getId_producteur());
			preparedStatement.setString(12, film.getSynopsis());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while creating film", e);
		}
	}

	public static List<Film> getAllFilms() throws SQLException {
		String query = "SELECT * FROM film";
		List<Film> films = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Film film = new Film();
				film.setId_film(resultSet.getInt("id_film"));
				film.setNom(resultSet.getString("nom"));
				film.setAnnee_sortie(resultSet.getInt("annee_sortie"));
				film.setUrl_film(resultSet.getString("url_film"));
				film.setUrl_image(resultSet.getString("url_image"));
				film.setUrl_video(resultSet.getString("url_video"));
				film.setVues(resultSet.getInt("vues"));
				film.setId_genre(resultSet.getInt("id_genre"));
				film.setId_langue(resultSet.getInt("id_langue"));
				film.setId_pays_origine(resultSet.getInt("id_pays_origine"));
				film.setId_producteur(resultSet.getInt("id_producteur"));
				film.setSynopsis(resultSet.getString("synopsis"));
				films.add(film);
			}
		} catch (SQLException e) {
			throw new SQLException("Error while getting all films", e);
		}
		return films;
	}

	public static void updateFilm(Film film) throws SQLException {
		String query = "UPDATE film SET nom = ?, annee_sortie = ?, url_film = ?, url_image = ?, url_video = ?, vues = ?, id_genre = ?, id_langue = ?, id_pays_origine = ?, id_producteur = ?, synopsis = ? WHERE id_film = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setString(1, film.getNom());
			preparedStatement.setInt(2, film.getAnnee_sortie());
			preparedStatement.setString(3, film.getUrl_film());
			preparedStatement.setString(4, film.getUrl_image());
			preparedStatement.setString(5, film.getUrl_video());
			preparedStatement.setInt(6, film.getVues());
			preparedStatement.setInt(7, film.getId_genre());
			preparedStatement.setInt(8, film.getId_langue());
			preparedStatement.setInt(9, film.getId_pays_origine());
			preparedStatement.setInt(10, film.getId_producteur());
			preparedStatement.setString(11, film.getSynopsis());
			preparedStatement.setInt(12, film.getId_film());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating film", e);
		}
	}

	public static void deleteFilm(int id_film) throws SQLException {
		String query = "DELETE FROM film WHERE id_film = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_film);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting film", e);
		}
	}

	public static void createEpisode(Episode episode) throws SQLException {
		String query = "INSERT INTO episode (id_episode,id_saison, id_serie, num_episode, date_diffusion, synopsis, url_episode, vues) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setInt(1, episode.getId_episode());
			preparedStatement.setInt(2, episode.getId_saison());
			preparedStatement.setInt(3, episode.getId_serie());
			preparedStatement.setInt(4, episode.getNum_episode());
			preparedStatement.setDate(5, episode.getDate_diffusion());
			preparedStatement.setString(6, episode.getSynopsis());
			preparedStatement.setString(7, episode.getUrl_episode());
			preparedStatement.setInt(8, episode.getVues());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while creating episode", e);
		}
	}

	public static List<Episode> getAllEpisodes() throws SQLException {
		List<Episode> episodes = new ArrayList<>();
		String query = "SELECT * FROM episode";
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Episode episode = new Episode();
				episode.setId_episode(resultSet.getInt("id_episode"));
				episode.setId_saison(resultSet.getInt("id_saison"));
				episode.setId_serie(resultSet.getInt("id_serie"));
				episode.setNum_episode(resultSet.getInt("num_episode"));
				episode.setDate_diffusion(resultSet.getDate("date_diffusion"));
				episode.setSynopsis(resultSet.getString("synopsis"));
				episode.setUrl_episode(resultSet.getString("url_episode"));
				episode.setVues(resultSet.getInt("vues"));
				System.out.println(episode.toString());
				episodes.add(episode);
			}
		} catch (SQLException e) {
			throw new SQLException("Error while getting all episodes", e);
		}
		return episodes;
	}

	public static Episode getEpisodeById(int id_episode) throws SQLException {
		Episode episode = null;
		String query = "SELECT * FROM episode WHERE id_episode = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_episode);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					episode = new Episode();
					episode.setId_episode(resultSet.getInt("id_episode"));
					episode.setId_saison(resultSet.getInt("id_saison"));
					episode.setId_serie(resultSet.getInt("id_serie"));
					episode.setNum_episode(resultSet.getInt("num_episode"));
					episode.setDate_diffusion(resultSet.getDate("date_diffusion"));
					episode.setSynopsis(resultSet.getString("synopsis"));
					episode.setUrl_episode(resultSet.getString("url_episode"));
					episode.setVues(resultSet.getInt("vues"));
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Error while getting episode by ID", e);
		}
		return episode;
	}

	public static void updateEpisode(Episode episode) throws SQLException {
		String query = "UPDATE episode SET id_saison = ?, id_serie = ?, num_episode = ?, date_diffusion = ?, synopsis = ?, url_episode = ?, vues = ? WHERE id_episode = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, episode.getId_saison());
			preparedStatement.setInt(2, episode.getId_serie());
			preparedStatement.setInt(3, episode.getNum_episode());
			preparedStatement.setDate(4, episode.getDate_diffusion());
			preparedStatement.setString(5, episode.getSynopsis());
			preparedStatement.setString(6, episode.getUrl_episode());
			preparedStatement.setInt(7, episode.getVues());
			preparedStatement.setInt(8, episode.getId_episode());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating episode", e);
		}
	}

	public static void deleteEpisode(int id_episode) throws SQLException {
		String query = "DELETE FROM episode WHERE id_episode = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_episode);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting episode", e);
		}
	}

	public static void addSaison(Saison saison) throws SQLException {
	    String query = "INSERT INTO saison (id_saison, id_serie, num_saison, date_debut, synopsis, url_image, url_video, vues) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    try (Connection conn = getConnection();
	         PreparedStatement preparedStatement = conn.prepareStatement(query)) {
	        
	        preparedStatement.setInt(1, saison.getId_saison());
	        preparedStatement.setInt(2, saison.getId_serie());
	        preparedStatement.setInt(3, saison.getNum_saison());
	        preparedStatement.setDate(4, (Date) saison.getDate_debut());
	        preparedStatement.setString(5, saison.getSynopsis());
	        preparedStatement.setString(6, saison.getUrl_image());
	        preparedStatement.setString(7, saison.getUrl_video());
	        preparedStatement.setInt(8, saison.getVues());

	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        throw new SQLException("Error while adding saison", e);
	    }
	}


	public static Saison getSaisonById(int id_saison) throws SQLException {
		String query = "SELECT * FROM saison WHERE id_saison = ?";
		Saison saison = null;

		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_saison);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					saison = new Saison();
					saison.setId_saison(resultSet.getInt("id_saison"));
					saison.setId_serie(resultSet.getInt("id_serie"));
					saison.setNum_saison(resultSet.getInt("num_saison"));
					saison.setDate_debut(resultSet.getDate("date_debut"));
					saison.setSynopsis(resultSet.getString("synopsis"));
					saison.setUrl_image(resultSet.getString("url_image"));
					saison.setUrl_video(resultSet.getString("url_video"));
					saison.setVues(resultSet.getInt("vues"));
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Error while retrieving saison", e);
		}

		return saison;
	}

	public static void updateSaison(Saison saison) throws SQLException {
		String query = "UPDATE saison SET id_serie = ?, num_saison = ?, date_debut = ?, synopsis = ?, url_image = ?, url_video = ?, vues = ? WHERE id_saison = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, saison.getId_serie());
			preparedStatement.setInt(2, saison.getNum_saison());
			preparedStatement.setDate(3, new java.sql.Date(saison.getDate_debut().getTime()));
			preparedStatement.setString(4, saison.getSynopsis());
			preparedStatement.setString(5, saison.getUrl_image());
			preparedStatement.setString(6, saison.getUrl_video());
			preparedStatement.setInt(7, saison.getVues());
			preparedStatement.setInt(8, saison.getId_saison());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating saison", e);
		}
	}

	public static void deleteSaison(int id_saison) throws SQLException {
		String query = "DELETE FROM saison WHERE id_saison = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_saison);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting saison", e);
		}
	}

	public static List<Saison> getAllSeasons() throws SQLException {
	    List<Saison> saisons = new ArrayList<>();
	    String query = "SELECT * FROM saison";

	    try (Connection conn = getConnection();
	            PreparedStatement preparedStatement = conn.prepareStatement(query);
	            ResultSet resultSet = preparedStatement.executeQuery()) {

	        while (resultSet.next()) {
	            Saison saison = new Saison();
	            saison.setId_saison(resultSet.getInt("id_saison"));
	            saison.setId_serie(resultSet.getInt("id_serie"));
	            saison.setNum_saison(resultSet.getInt("num_saison"));
	            saison.setDate_debut(resultSet.getDate("date_debut"));
	            saison.setSynopsis(resultSet.getString("synopsis"));
	            saison.setUrl_image(resultSet.getString("url_image"));
	            saison.setUrl_video(resultSet.getString("url_video"));
	            saison.setVues(resultSet.getInt("vues"));

	            saisons.add(saison);
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Error while getting all saisons", e);
	    }
	    
	    System.out.println("Fetched saisons: " + saisons);

	    return saisons;
	}

	public static void addSerie(Serie serie) throws SQLException {
		String query = "INSERT INTO serie (id_serie,nom, annee_sortie, url_image, url_video, vues, id_genre, id_langue, id_pays_origine, id_producteur, synopsis) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setInt(1, serie.getId_serie());
			preparedStatement.setString(2, serie.getNom());
			preparedStatement.setInt(3, serie.getAnnee_sortie());
			preparedStatement.setString(4, serie.getUrl_image());
			preparedStatement.setString(5, serie.getUrl_video());
			preparedStatement.setInt(6, serie.getVues());
			preparedStatement.setInt(7, serie.getId_genre());
			preparedStatement.setInt(8, serie.getId_langue());
			preparedStatement.setInt(9, serie.getId_pays_origine());
			preparedStatement.setInt(10, serie.getId_producteur());
			preparedStatement.setString(11, serie.getSynopsis());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding Serie", e);
		}
	}
	public static List<Serie> getAllSeries() throws SQLException {
        List<Serie> series = new ArrayList<>();
        
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM serie");

            while (resultSet.next()) {
                Serie serie = new Serie();
                serie.setId_serie(resultSet.getInt("id_serie"));
                serie.setNom(resultSet.getString("nom"));
                serie.setAnnee_sortie(resultSet.getInt("annee_sortie"));
                serie.setUrl_image(resultSet.getString("url_image"));
                serie.setUrl_video(resultSet.getString("url_video"));
                serie.setVues(resultSet.getInt("vues"));
                serie.setId_genre(resultSet.getInt("id_genre"));
                serie.setId_langue(resultSet.getInt("id_langue"));
                serie.setId_pays_origine(resultSet.getInt("id_pays_origine"));
                serie.setId_producteur(resultSet.getInt("id_producteur"));
                serie.setSynopsis(resultSet.getString("synopsis"));

                series.add(serie);
            }
        }
        
        return series;
    }

	public static Serie getSerie(int id_serie) throws SQLException {
		String query = "SELECT * FROM serie WHERE id_serie = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_serie);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Serie serie = new Serie();
				serie.setId_serie(resultSet.getInt("id_serie"));
				serie.setNom(resultSet.getString("nom"));
				serie.setAnnee_sortie(resultSet.getInt("annee_sortie"));
				serie.setUrl_image(resultSet.getString("url_image"));
				serie.setUrl_video(resultSet.getString("url_video"));
				serie.setVues(resultSet.getInt("vues"));
				serie.setId_genre(resultSet.getInt("id_genre"));
				serie.setId_langue(resultSet.getInt("id_langue"));
				serie.setId_pays_origine(resultSet.getInt("id_pays_origine"));
				serie.setId_producteur(resultSet.getInt("id_producteur"));
				serie.setSynopsis(resultSet.getString("synopsis"));

				return serie;
			}
		} catch (SQLException e) {
			throw new SQLException("Error while retrieving Serie", e);
		}

		return null;
	}

	public static void updateSerie(Serie serie) throws SQLException {
		String query = "UPDATE serie SET nom = ?, annee_sortie = ?, url_image = ?, url_video = ?, vues = ?, id_genre = ?, id_langue = ?, id_pays_origine = ?, id_producteur = ?, synopsis = ? WHERE id_serie = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setString(1, serie.getNom());
			preparedStatement.setInt(2, serie.getAnnee_sortie());
			preparedStatement.setString(3, serie.getUrl_image());
			preparedStatement.setString(4, serie.getUrl_video());
			preparedStatement.setInt(5, serie.getVues());
			preparedStatement.setInt(6, serie.getId_genre());
			preparedStatement.setInt(7, serie.getId_langue());
			preparedStatement.setInt(8, serie.getId_pays_origine());
			preparedStatement.setInt(9, serie.getId_producteur());
			preparedStatement.setString(10, serie.getSynopsis());
			preparedStatement.setInt(11, serie.getId_serie());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating Serie", e);
		}
	}

	public static void deleteSerie(int id_serie) throws SQLException {
		String query = "DELETE FROM serie WHERE id_serie = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_serie);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting Serie", e);
		}
	}

	public static void addUtilisateur(Utilisateur utilisateur) throws SQLException {
		String query = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, date_de_naissance, type) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setString(1, utilisateur.getNom());
			preparedStatement.setString(2, utilisateur.getPrenom());
			preparedStatement.setString(3, utilisateur.getEmail());
			preparedStatement.setString(4, utilisateur.getMot_de_passe());
			preparedStatement.setDate(5, new java.sql.Date(utilisateur.getDate_de_naissance().getTime()));
			preparedStatement.setString(6, utilisateur.getType());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding utilisateur", e);
		}
	}

	public static void addRoleFilm(Role_film roleFilm) throws SQLException {
		String query = "INSERT INTO role_film (id_acteur, id_film, role_type,url_image) VALUES (?, ?, ?,?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, roleFilm.getId_acteur());
			preparedStatement.setInt(2, roleFilm.getId_film());
			preparedStatement.setString(3, roleFilm.getRole_type());
			preparedStatement.setString(4, roleFilm.getUrl_image());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding role_film", e);
		}
	}


	public static void updateRoleFilm(Role_film roleFilm, Role_film old_film) throws SQLException {
	    String query = "UPDATE role_film SET id_acteur = ?, id_film = ?, role_type = ?, url_image = ? WHERE id_acteur = ? AND id_film = ?";
	    
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
            
	        pstmt.setInt(1, roleFilm.getId_acteur());
	        pstmt.setInt(2, roleFilm.getId_film());
	        System.out.println("1- "+roleFilm.getId_acteur());
	        System.out.println("2- "+roleFilm.getId_film());
	        pstmt.setString(3, roleFilm.getRole_type());
	        pstmt.setString(4, roleFilm.getUrl_image());
	        pstmt.setInt(5, old_film.getId_acteur());
	        System.out.println("1- "+roleFilm.getId_acteur());
	        System.out.println("2- "+roleFilm.getId_film());
	        pstmt.setInt(6, old_film.getId_film());
	        System.out.println("SQL Query: " + pstmt.toString());
	        System.out.println("Updating roleFilm: " + roleFilm); 
	        
	        int rowsAffected = pstmt.executeUpdate();
	        System.out.println("Rows affected: " + rowsAffected); 
	    } catch (SQLException e) {
	        System.err.println("SQLException: " + e.getMessage());
	        System.err.println("SQLState: " + e.getSQLState());
	        System.err.println("VendorError: " + e.getErrorCode());
	        throw e;
	    }
	}






	public static void deleteRoleFilm(int id_acteur, int id_film) throws SQLException {
		String query = "DELETE FROM role_film WHERE id_acteur = ? AND id_film = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_acteur);
			preparedStatement.setInt(2, id_film);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting role_film", e);
		}
	}

	public static void addRoleSerie(Role_serie roleSerie) throws SQLException {
		String query = "INSERT INTO role_serie (id_acteur,id_serie ,id_saison, role_type,url_image) VALUES (?, ?, ?,?,?)";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setInt(1, roleSerie.getId_acteur());
			preparedStatement.setInt(2, roleSerie.getId_serie());
			preparedStatement.setInt(3, roleSerie.getId_saison());
			preparedStatement.setString(4, roleSerie.getRole_type());
			preparedStatement.setString(5, roleSerie.getUrl_image());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while adding role_serie", e);
		}
	}

	public static void updateRoleSerie(Role_serie roleSerie) throws SQLException {
		String query = "UPDATE role_serie SET id_acteur = ? ,id_serie = ? ,id_saison = ? ,role_type = ? ,url_image = ? WHERE id_acteur = ? AND id_serie = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, roleSerie.getId_acteur());
			preparedStatement.setInt(2, roleSerie.getId_serie());
			preparedStatement.setInt(3, roleSerie.getId_saison());
			preparedStatement.setString(4, roleSerie.getRole_type());
			preparedStatement.setString(5, roleSerie.getUrl_image());
			preparedStatement.setInt(6, roleSerie.getId_acteur());
			preparedStatement.setInt(7, roleSerie.getId_serie());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while updating role_serie", e);
		}
	}

	public static void deleteRoleSerie(int id_acteur, int id_serie) throws SQLException {
		String query = "DELETE FROM role_serie WHERE id_acteur = ? AND id_serie = ?";
		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {

			preparedStatement.setInt(1, id_acteur);
			preparedStatement.setInt(2, id_serie);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error while deleting role_serie", e);
		}
	}
	public static List<Role_serie> getAllRoleSeries() throws SQLException {
        String query = "SELECT * FROM role_serie";
        List<Role_serie> roleSeries = new ArrayList<>();
        
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id_acteur = resultSet.getInt("id_acteur");
                int id_serie = resultSet.getInt("id_serie");
                int id_saison = resultSet.getInt("id_saison");
                String role_type = resultSet.getString("role_type");
                String url_image = resultSet.getString("url_image");

                Role_serie roleSerie = new Role_serie(id_acteur, id_serie,id_saison, role_type, url_image);
                roleSeries.add(roleSerie);
            }
        }
        
        return roleSeries;
    }

    public static void deleteRoleSerie(Role_serie roleSerie) throws SQLException {
        String query = "DELETE FROM role_serie WHERE id_acteur = ? AND id_serie = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roleSerie.getId_acteur());
            preparedStatement.setInt(2, roleSerie.getId_serie());
            preparedStatement.executeUpdate();
        }
    }
    
    public static List<Role_film> getAllRoleFilms() throws SQLException {
        String query = "SELECT * FROM role_film";
        List<Role_film> roleFilms = new ArrayList<>();
        
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id_acteur = resultSet.getInt("id_acteur");
                int id_film = resultSet.getInt("id_film");
                String role_type = resultSet.getString("role_type");
                String url_image = resultSet.getString("url_image");

                Role_film roleFilm = new Role_film(id_acteur, id_film, role_type, url_image);
                roleFilms.add(roleFilm);
            }
        }
        
        return roleFilms;
    }

    public static void deleteRoleFilm(Role_film roleFilm) throws SQLException {
        String query = "DELETE FROM role_film WHERE id_acteur = ? AND id_film = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roleFilm.getId_acteur());
            preparedStatement.setInt(2, roleFilm.getId_film());
            preparedStatement.executeUpdate();
        }
    }
    public ObservableList<Utilisateur> getAllUtilisateurs() throws SQLException {
        ObservableList<Utilisateur> utilisateurList = FXCollections.observableArrayList();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur")) {

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId_utilisateur(rs.getInt("id_utilisateur"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setMot_de_passe(rs.getString("mot_de_passe"));
                utilisateur.setDate_de_naissance(rs.getDate("date_de_naissance"));
                utilisateur.setType(rs.getString("type"));
                utilisateurList.add(utilisateur);
            }
        }
        return utilisateurList;
    }

}
