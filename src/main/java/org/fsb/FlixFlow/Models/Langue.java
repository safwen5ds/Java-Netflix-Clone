package org.fsb.FlixFlow.Models;


public class Langue {
	private int id_langue;
	private String nom ;
	public Langue() {
		super();
	}
	public Langue(int id_langue, String nom) {
		super();
		this.id_langue = id_langue;
		this.nom = nom;
	}
	public int getId_langue() {
		return id_langue;
	}
	public void setId_langue(int id_langue) {
		this.id_langue = id_langue;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}


}