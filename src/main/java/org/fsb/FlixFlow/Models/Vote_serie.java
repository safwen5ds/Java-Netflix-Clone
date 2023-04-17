package org.fsb.FlixFlow.Models;

public class Vote_serie {
	private int id_utilisateur;
	private int id_serie;

	public Vote_serie() {
		super();
	}

	public Vote_serie(int id_utilisateur, int id_serie) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_serie = id_serie;
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

}
