package org.fsb.FlixFlow.Models;

public class Acteur {
	private int id_acteur;
	private String nom;
	private String url_image;

	public Acteur() {
		super();
	}

	public Acteur(int id_acteur, String nom) {
		this.id_acteur = id_acteur;
		this.nom = nom;

	}

	public int getId_acteur() {
		return id_acteur;
	}

	public void setId_acteur(int id_acteur) {
		this.id_acteur = id_acteur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

}