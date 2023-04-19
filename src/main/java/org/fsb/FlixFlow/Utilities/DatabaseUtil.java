package org.fsb.FlixFlow.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.fsb.FlixFlow.Controllers.ActorRoleDisplay;
import org.fsb.FlixFlow.Models.Acteur;
import org.fsb.FlixFlow.Models.Commentaire_episode;
import org.fsb.FlixFlow.Models.Commentaire_film;
import org.fsb.FlixFlow.Models.Commentaire_saison;
import org.fsb.FlixFlow.Models.Commentaire_serie;
import org.fsb.FlixFlow.Models.Episode;
import org.fsb.FlixFlow.Models.Film;
import org.fsb.FlixFlow.Models.Saison;
import org.fsb.FlixFlow.Models.Serie;
import org.fsb.FlixFlow.Models.Utilisateur;

import javafx.scene.control.Slider;

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
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(USER_FILE))) {
            writer.write(user.getId_utilisateur() + ",");
            writer.write(user.getNom() + ",");
            writer.write(user.getPrenom() + ",");
            writer.write(user.getEmail() + ",");
            writer.write(user.getMot_de_passe() + ",");
            writer.write(DATE_FORMAT.format(user.getDate_de_naissance()) + ",");
            writer.write(user.getType());
            writer.newLine();
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
	    statement.setInt(2, serieId); // <-- changed from statement.setInt(1, serieId)
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
	public static List<ActorRoleDisplay> getActorRolesForMovie(int filmId) throws SQLException {
	    String query = "SELECT A.*, RF.ROLE_TYPE, RF.URL_IMAGE FROM ACTEUR A " +
	            "JOIN ROLE_FILM RF ON A.ID_ACTEUR = RF.ID_ACTEUR " +
	            "WHERE RF.ID_FILM = ?";
	    Connection connection = getConnection();
	    PreparedStatement statement = connection.prepareStatement(query);
	    statement.setInt(1, filmId);
	    ResultSet resultSet = statement.executeQuery();
	    List<ActorRoleDisplay> actorRoles = new ArrayList<>();
	    while (resultSet.next()) {
	        ActorRoleDisplay actorRole = new ActorRoleDisplay(
	                resultSet.getString("URL_IMAGE"),
	                resultSet.getString("NOM"),
	                resultSet.getString("ROLE_TYPE")
	        );
	        actorRoles.add(actorRole);
	    }
	    return actorRoles;
	}

	public static List<ActorRoleDisplay> getActorRolesForSeries(int serieId) throws SQLException {
	    String query = "SELECT A.*, RS.ROLE_TYPE, RS.URL_IMAGE FROM ACTEUR A " +
	            "JOIN ROLE_SERIE RS ON A.ID_ACTEUR = RS.ID_ACTEUR " +
	            "JOIN SAISON S ON S.ID_SAISON = RS.ID_SAISON " +
	            "WHERE S.ID_SERIE = ?";
	    Connection connection = getConnection();
	    PreparedStatement statement = connection.prepareStatement(query);
	    statement.setInt(1, serieId);
	    ResultSet resultSet = statement.executeQuery();
	    List<ActorRoleDisplay> actorRoles = new ArrayList<>();
	    while (resultSet.next()) {
	        ActorRoleDisplay actorRole = new ActorRoleDisplay(
	                resultSet.getString("URL_IMAGE"),
	                resultSet.getString("NOM"),
	                resultSet.getString("ROLE_TYPE")
	        );
	        actorRoles.add(actorRole);
	    }
	    return actorRoles;
	}
	public static Acteur extractActeurFromResultSet(ResultSet resultSet) throws SQLException {
	    int id = resultSet.getInt("ID_ACTEUR");
	    String nom = resultSet.getString("NOM");
	    String url_image = resultSet.getString("URL_IMAGE");

	    Acteur acteur = new Acteur(id, nom, url_image);
	    return acteur;
	}

	public static List<Acteur> getActorsByFilmId(int filmId) throws SQLException {
	    Connection connection = getConnection();

	    String query = "SELECT A.* FROM ACTEUR A " +
	            "JOIN ROLE_FILM RF ON A.ID_ACTEUR = RF.ID_ACTEUR " +
	            "WHERE RF.ID_FILM = ?";
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

	    String query = "SELECT A.* FROM ACTEUR A " +
	            "JOIN ROLE_SERIE RS ON A.ID_ACTEUR = RS.ID_ACTEUR " +
	            "JOIN SAISON S ON S.ID_SAISON = RS.ID_SAISON " +
	            "WHERE S.ID_SERIE = ?";
	    PreparedStatement statement = connection.prepareStatement(query);
	    statement.setInt(1, serieId);
	    ResultSet resultSet = statement.executeQuery();
	    List<Acteur> actors = new ArrayList<>();
	    while (resultSet.next()) {
	        actors.add(extractActeurFromResultSet(resultSet));
	    }
	    return actors;
	}
	
	// UserManager.java

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
	public static void addPreferenceFilm(int userId, int filmId) throws SQLException {
	    String sql = "INSERT INTO PREFERENCES_FILM (ID_UTILISATEUR, ID_FILM) VALUES (?, ?)";
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, filmId);
	        preparedStatement.executeUpdate();
	    }
	}

	public static void addPreferenceSerie(int userId, int serieId) throws SQLException {
	    String sql = "INSERT INTO PREFERENCES_SERIE (ID_UTILISATEUR, ID_SERIE) VALUES (?, ?)";
	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, serieId);
	        preparedStatement.executeUpdate();
	    }
	}
	public static void submitRating(Slider ratingSlider, boolean isMovie, int mediaid) {
		int score = (int) ratingSlider.getValue();
		Utilisateur user = DatabaseUtil.readUserFromFile();
	    int userId = user.getId_utilisateur();
	    int mediaId = mediaid;

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




}
