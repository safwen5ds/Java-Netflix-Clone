package org.fsb.FlixFlow.Models;

public class Vote_saison {
	private int id_utilisateur;
	private int id_saison;
	public Vote_saison() {
		super();
	}
	public Vote_saison(int id_utilisateur, int id_saison) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_saison = id_saison;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public int getId_saison() {
		return id_saison;
	}
	public void setId_saison(int id_saison) {
		this.id_saison = id_saison;
	}


}
