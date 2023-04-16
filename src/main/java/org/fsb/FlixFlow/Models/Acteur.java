package org.fsb.FlixFlow.Models;



public class Acteur  {
    private int id_acteur ;
    private String nom;
	public Acteur() {
		super();
	}
	public Acteur(int id_acteur, String nom) {
		super();
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

}