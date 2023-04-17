package org.fsb.FlixFlow.Models;

public class preferences_producteur {
	private int id_preferences_producteur;
	private int id_utilisateur;
	private int id_producteur;

	public preferences_producteur() {
		super();
	}

	public preferences_producteur(int id_preferences_producteur, int id_utilisateur, int id_producteur) {
		super();
		this.id_preferences_producteur = id_preferences_producteur;
		this.id_utilisateur = id_utilisateur;
		this.id_producteur = id_producteur;
	}

	public int getId_preferences_producteur() {
		return id_preferences_producteur;
	}

	public void setId_preferences_producteur(int id_preferences_producteur) {
		this.id_preferences_producteur = id_preferences_producteur;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public int getId_producteur() {
		return id_producteur;
	}

	public void setId_producteur(int id_producteur) {
		this.id_producteur = id_producteur;
	}

}
