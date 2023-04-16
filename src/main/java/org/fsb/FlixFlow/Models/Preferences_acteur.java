package org.fsb.FlixFlow.Models;

public class Preferences_acteur {
	private int id_preferences_acteur;
	private int id_utilisateur;
	private int id_acteur;
	public Preferences_acteur() {
		super();
	}
	public Preferences_acteur(int id_preferences_acteur, int id_utilisateur, int id_acteur) {
		super();
		this.id_preferences_acteur = id_preferences_acteur;
		this.id_utilisateur = id_utilisateur;
		this.id_acteur = id_acteur;
	}
	public int getId_preferences_acteur() {
		return id_preferences_acteur;
	}
	public void setId_preferences_acteur(int id_preferences_acteur) {
		this.id_preferences_acteur = id_preferences_acteur;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public int getId_acteur() {
		return id_acteur;
	}
	public void setId_acteur(int id_acteur) {
		this.id_acteur = id_acteur;
	}


}
