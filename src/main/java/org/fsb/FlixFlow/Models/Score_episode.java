package org.fsb.FlixFlow.Models;

public class Score_episode {
	private int id_utilisateur;
	private int id_episode;
	private int score;

	public Score_episode() {
		super();
	}

	public Score_episode(int id_utilisateur, int id_episode, int score) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_episode = id_episode;
		this.score = score;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public int getId_episode() {
		return id_episode;
	}

	public void setId_episode(int id_episode) {
		this.id_episode = id_episode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
