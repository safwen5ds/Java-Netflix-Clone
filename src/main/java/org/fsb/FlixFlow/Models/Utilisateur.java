package org.fsb.FlixFlow.Models;

import java.util.Date;


public class Utilisateur {
	private int id_utilisateur;
	private String nom;
	private String prenom;
	private String email;
	private String mot_de_passe;
	private Date date_de_naissance;
	private String type;
	public Utilisateur() {
		super();
	}
	public Utilisateur(int id_utilisateur, String nom, String prenom, String email, String mot_de_passe,
			Date date_de_naissance, String type) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mot_de_passe = mot_de_passe;
		this.date_de_naissance = date_de_naissance;
		this.type = type;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMot_de_passe() {
		return mot_de_passe;
	}
	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}
	public Date getDate_de_naissance() {
		return date_de_naissance;
	}
	public void setDate_de_naissance(Date date_de_naissance) {
		this.date_de_naissance = date_de_naissance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

   }