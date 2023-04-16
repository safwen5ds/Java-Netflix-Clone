package org.fsb.FlixFlow.Models;

import java.util.Date;


public class Saison {
	private int id_saison;
	private int id_serie ;
	private int num_saison ;
	private Date date_debut;
	private String synopsis;
	private String url_image;
	private String url_video;
	private String Nom_serie;
	private int vues;
	public Saison() {
		super();
	}
	public Saison(int id_saison, int id_serie, int num_saison, Date date_debut, String synopsis, String url_image,
			String url_video, String nom_serie, int vues) {
		super();
		this.id_saison = id_saison;
		this.id_serie = id_serie;
		this.num_saison = num_saison;
		this.date_debut = date_debut;
		this.synopsis = synopsis;
		this.url_image = url_image;
		this.url_video = url_video;
		Nom_serie = nom_serie;
		this.vues = vues;
	}
	public int getId_saison() {
		return id_saison;
	}
	public void setId_saison(int id_saison) {
		this.id_saison = id_saison;
	}
	public int getId_serie() {
		return id_serie;
	}
	public void setId_serie(int id_serie) {
		this.id_serie = id_serie;
	}
	public int getNum_saison() {
		return num_saison;
	}
	public void setNum_saison(int num_saison) {
		this.num_saison = num_saison;
	}
	public Date getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getUrl_image() {
		return url_image;
	}
	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}
	public String getUrl_video() {
		return url_video;
	}
	public void setUrl_video(String url_video) {
		this.url_video = url_video;
	}
	public String getNom_serie() {
		return Nom_serie;
	}
	public void setNom_serie(String nom_serie) {
		Nom_serie = nom_serie;
	}
	public int getVues() {
		return vues;
	}
	public void setVues(int vues) {
		this.vues = vues;
	}




}
