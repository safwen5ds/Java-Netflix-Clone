package org.fsb.FlixFlow.Models;

public class Score_saison {
	private int id_utilisateur;
	private int id_saison;
	private int score;
	public Score_saison() {
		super();
	}
	public Score_saison(int id_utilisateur, int id_saison, int score) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_saison = id_saison;
		this.score = score;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}


}
