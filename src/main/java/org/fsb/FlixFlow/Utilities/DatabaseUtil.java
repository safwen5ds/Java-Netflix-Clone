package org.fsb.FlixFlow.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.fsb.FlixFlow.Models.Commentaire_episode;
import org.fsb.FlixFlow.Models.Commentaire_film;
import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Commentaire_serie;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Models.Serie;

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
		String query = "SELECT * FROM SERIE ORDER ";
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

		if (resultSet.next()) {
			Commentaire_episode c = new Commentaire_episode();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_episode(resultSet.getInt("id_episode"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
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

		if (resultSet.next()) {
			Commentaire_saison c = new Commentaire_saison();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_saison(resultSet.getInt("id_saison"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
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

		if (resultSet.next()) {
			Commentaire_serie c = new Commentaire_serie();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_serie(resultSet.getInt("id_serie"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
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

		if (resultSet.next()) {
			Commentaire_film c = new Commentaire_film();
			c.setId_utilisateur(resultSet.getInt("id_utilisateur"));
			c.setId_film(resultSet.getInt("id_film"));
			c.setContenu(resultSet.getString("contenu"));
			c.setNom_User(resultSet.getString("user_name"));
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

	public static boolean checkUserCredentials(String email, String password) {

		String query = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<Episode> getEpisodeByIds(int saisonId, int serieId) throws SQLException {
		String query = "select episode.*, serie.nom as serie_name from episode join saison on episode.id_saison = saison.id_saison join serie on saison.id_serie = serie.id_serie where episode.id_saison = ? and episode.id_serie = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, saisonId);
		statement.setInt(1, serieId);
		ResultSet resultSet = statement.executeQuery();
		List<Episode> c1 = new ArrayList<>();
		if (resultSet.next()) {
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

}
