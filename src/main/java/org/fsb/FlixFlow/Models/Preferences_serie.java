package org.fsb.FlixFlow.Models;

public class Preferences_serie {
	private int id_preferences_serie;
	private int id_serie;
	private int id_utilisateur;

	public Preferences_serie() {
		super();
	}

	public Preferences_serie(int id_preferences_serie, int id_utilisateur, int id_serie) {
		super();
		this.id_preferences_serie = id_preferences_serie;
		this.id_utilisateur = id_utilisateur;
		this.id_serie = id_serie;
	}

	public int getId_preferences_serie() {
		return id_preferences_serie;
	}

	public int getId_serie() {
		return id_serie;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_preferences_serie(int id_preferences_serie) {
		this.id_preferences_serie = id_preferences_serie;
	}

	public void setId_serie(int id_serie) {
		this.id_serie = id_serie;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

}
