package org.fsb.FlixFlow.Models;

public class Vote_film {
	private int id_utilisateur;
	private int id_film;
	public Vote_film() {
		super();
	}
	public Vote_film(int id_utilisateur, int id_film) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_film = id_film;
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


}
