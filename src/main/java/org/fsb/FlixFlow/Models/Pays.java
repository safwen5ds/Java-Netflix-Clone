package org.fsb.FlixFlow.Models;

public class Pays {
	private int id_pays;
	private String nom;

	public Pays() {
		super();
	}

	public Pays(int id_pays, String nom) {
		super();
		this.id_pays = id_pays;
		this.nom = nom;
	}

	public int getId_pays() {
		return id_pays;
	}

	public String getNom() {
		return nom;
	}

	public void setId_pays(int id_pays) {
		this.id_pays = id_pays;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}