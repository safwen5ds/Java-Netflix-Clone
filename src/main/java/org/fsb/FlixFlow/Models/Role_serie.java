package org.fsb.FlixFlow.Models;

public class Role_serie {
	private int id_acteur;
	private int id_saison ;
	private String role_type ;
	public Role_serie() {
		super();
	}
	public Role_serie(int id_acteur, int id_saison, String role_type) {
		super();
		this.id_acteur = id_acteur;
		this.id_saison = id_saison;
		this.role_type = role_type;
	}
	public int getId_acteur() {
		return id_acteur;
	}
	public void setId_acteur(int id_acteur) {
		this.id_acteur = id_acteur;
	}
	public int getId_saison() {
		return id_saison;
	}
	public void setId_saison(int id_saison) {
		this.id_saison = id_saison;
	}
	public String getRole_type() {
		return role_type;
	}
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

}
