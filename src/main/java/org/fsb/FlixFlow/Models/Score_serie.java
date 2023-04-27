package org.fsb.FlixFlow.Models;

public class Score_serie {
	private int id_serie;
	private int id_utilisateur;
	private int score;

	public Score_serie() {
		super();
	}

	public Score_serie(int id_utilisateur, int id_serie, int score) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_serie = id_serie;
		this.score = score;
	}

	public int getId_serie() {
		return id_serie;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public int getScore() {
		return score;
	}

	public void setId_serie(int id_serie) {
		this.id_serie = id_serie;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
