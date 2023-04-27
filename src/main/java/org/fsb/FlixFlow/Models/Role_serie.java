package org.fsb.FlixFlow.Models;

public class Role_serie {
	private int id_acteur;
	private int id_saison;
	private int id_serie;
	private String role_type;
	private String url_image;

	public Role_serie(int id_acteur, int id_serie, int id_saison, String role_type, String url_image) {
		super();
		this.id_acteur = id_acteur;
		this.id_serie = id_serie;
		this.id_saison = id_saison;
		this.role_type = role_type;
		this.url_image = url_image;
	}

	public int getId_acteur() {
		return id_acteur;
	}

	public int getId_saison() {
		return id_saison;
	}

	public int getId_serie() {
		return id_serie;
	}

	public String getRole_type() {
		return role_type;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setId_acteur(int id_acteur) {
		this.id_acteur = id_acteur;
	}

	public void setId_saison(int id_saison) {
		this.id_saison = id_saison;
	}

	public void setId_serie(int id_serie) {
		this.id_serie = id_serie;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	@Override
	public String toString() {
		return "Role_serie [id_acteur=" + id_acteur + ", id_serie=" + id_serie + ", id_saison=" + id_saison
				+ ", role_type=" + role_type + ", url_image=" + url_image + "]";
	}

}
