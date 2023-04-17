package org.fsb.FlixFlow.Models;

public class Producteur {
	private int id_producteur;
	private String nom;

	public Producteur() {
		super();
	}

	public Producteur(int id_producteur, String nom) {
		super();
		this.id_producteur = id_producteur;
		this.nom = nom;
	}

	public int getId_producteur() {
		return id_producteur;
	}

	public void setId_producteur(int id_producteur) {
		this.id_producteur = id_producteur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}