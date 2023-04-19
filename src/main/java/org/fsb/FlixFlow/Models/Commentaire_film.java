package org.fsb.FlixFlow.Models;

public class Commentaire_film {
	private int id_utilisateur;
	private int id_film;
	private String contenu;
	private String Nom_User;
	private int comment_id;
	public Commentaire_film() {
		super();
	}
	public Commentaire_film(int id_utilisateur, int id_film, String contenu, String nom_User, int comment_id) {
		
		this.id_utilisateur = id_utilisateur;
		this.id_film = id_film;
		this.contenu = contenu;
		Nom_User = nom_User;
		this.comment_id = comment_id;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public int getId_film() {
		return id_film;
	}
	public void setId_film(int id_film) {
		this.id_film = id_film;
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
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
    
	

}
