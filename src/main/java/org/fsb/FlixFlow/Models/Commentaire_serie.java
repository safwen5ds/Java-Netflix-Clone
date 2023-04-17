package org.fsb.FlixFlow.Models;

public class Commentaire_serie {
	private int id_utilisateur;
	private int id_serie;
	private String contenu;
	private String Nom_User;

	public Commentaire_serie() {
		super();
	}

	public Commentaire_serie(int id_utilisateur, int id_serie, String contenu, String nom_User) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_serie = id_serie;
		this.contenu = contenu;
		Nom_User = nom_User;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public int getId_serie() {
		return id_serie;
	}

	public void setId_serie(int id_serie) {
		this.id_serie = id_serie;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getNom_User() {
		return Nom_User;
	}

	public void setNom_User(String nom_User) {
		Nom_User = nom_User;
	}

}
