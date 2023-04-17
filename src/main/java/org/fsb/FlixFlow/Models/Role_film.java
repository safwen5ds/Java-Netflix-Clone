package org.fsb.FlixFlow.Models;

public class Role_film {
	private int id_acteur;
	private int id_film;
	private String role_type;

	public Role_film() {
		super();
	}

	public Role_film(int id_acteur, int id_film, String role_type) {
		super();
		this.id_acteur = id_acteur;
		this.id_film = id_film;
		this.role_type = role_type;
	}

	public int getId_acteur() {
		return id_acteur;
	}

	public void setId_acteur(int id_acteur) {
		this.id_acteur = id_acteur;
	}

	public int getId_film() {
		return id_film;
	}

	public void setId_film(int id_film) {
		this.id_film = id_film;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

}
