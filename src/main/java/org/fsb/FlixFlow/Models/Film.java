package org.fsb.FlixFlow.Models;

public class Film {
	private int id_film;
	private String nom;
	private int annee_sortie;
	private String url_film;
	private String url_image;
	private String url_video;
	private int vues;
	private int id_genre;
	private int id_langue;
	private int id_pays_origine;
	private int id_producteur;
	private String synopsis;
	private String Nom_producteur;
	private String Nom_genre;
	private String Nom_pays;
	private String Nom_langue;
	private String Nom_acteur;

	public Film() {
		super();
	}

	public Film(int id_film, String nom, int annee_sortie, String url_film, String url_image, String url_video,
			int vues, int id_genre, int id_langue, int id_pays_origine, int id_producteur, String synopsis,
			String nom_producteur, String nom_genre, String nom_pays, String nom_langue, String nom_acteur) {
		super();
		this.id_film = id_film;
		this.nom = nom;
		this.annee_sortie = annee_sortie;
		this.url_film = url_film;
		this.url_image = url_image;
		this.url_video = url_video;
		this.vues = vues;
		this.id_genre = id_genre;
		this.id_langue = id_langue;
		this.id_pays_origine = id_pays_origine;
		this.id_producteur = id_producteur;
		this.synopsis = synopsis;
		Nom_producteur = nom_producteur;
		Nom_genre = nom_genre;
		Nom_pays = nom_pays;
		Nom_langue = nom_langue;
		Nom_acteur = nom_acteur;
	}

	public int getId_film() {
		return id_film;
	}


	public void setId_film(int id_film) {
		this.id_film = id_film;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getAnnee_sortie() {
		return annee_sortie;
	}

	public void setAnnee_sortie(int annee_sortie) {
		this.annee_sortie = annee_sortie;
	}

	public String getUrl_film() {
		return url_film;
	}

	public void setUrl_film(String url_film) {
		this.url_film = url_film;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	public String getUrl_video() {
		return url_video;
	}

	public void setUrl_video(String url_video) {
		this.url_video = url_video;
	}

	public int getVues() {
		return vues;
	}

	public void setVues(int vues) {
		this.vues = vues;
	}

	public int getId_genre() {
		return id_genre;
	}

	public void setId_genre(int id_genre) {
		this.id_genre = id_genre;
	}

	public int getId_langue() {
		return id_langue;
	}

	public void setId_langue(int id_langue) {
		this.id_langue = id_langue;
	}

	public int getId_pays_origine() {
		return id_pays_origine;
	}

	public void setId_pays_origine(int id_pays_origine) {
		this.id_pays_origine = id_pays_origine;
	}

	public int getId_producteur() {
		return id_producteur;
	}

	public void setId_producteur(int id_producteur) {
		this.id_producteur = id_producteur;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getNom_producteur() {
		return Nom_producteur;
	}

	public void setNom_producteur(String nom_producteur) {
		Nom_producteur = nom_producteur;
	}

	public String getNom_genre() {
		return Nom_genre;
	}

	public void setNom_genre(String nom_genre) {
		Nom_genre = nom_genre;
	}

	public String getNom_pays() {
		return Nom_pays;
	}

	public void setNom_pays(String nom_pays) {
		Nom_pays = nom_pays;
	}

	public String getNom_langue() {
		return Nom_langue;
	}

	public void setNom_langue(String nom_langue) {
		Nom_langue = nom_langue;
	}

	public String getNom_acteur() {
		return Nom_acteur;
	}

	public void setNom_acteur(String nom_acteur) {
		Nom_acteur = nom_acteur;
	}

	@Override
	public String toString() {
		return "Film [id_film=" + id_film + ", nom=" + nom + ", annee_sortie=" + annee_sortie + ", url_film=" + url_film
				+ ", url_image=" + url_image + ", url_video=" + url_video + ", vues=" + vues + ", id_genre=" + id_genre
				+ ", id_langue=" + id_langue + ", id_pays_origine=" + id_pays_origine + ", id_producteur="
				+ id_producteur + ", synopsis=" + synopsis + ", Nom_producteur=" + Nom_producteur + ", Nom_genre="
				+ Nom_genre + ", Nom_pays=" + Nom_pays + ", Nom_langue=" + Nom_langue + ", Nom_acteur=" + Nom_acteur
				+ "]";
	}
	

}
