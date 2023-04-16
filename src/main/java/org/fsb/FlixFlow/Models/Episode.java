package org.fsb.FlixFlow.Models;

import java.util.Date;


public class Episode {
    private int id_episode;
    private int id_saison;
    private int id_serie;
    private int num_episode;
    private Date date_diffusion;
    private String synopsis;
    private String url_episode;
    private int Num_saison;
    private String Nom_serie;
    private int vues;
	public Episode() {
		super();
	}
	public Episode(int id_episode, int id_saison, int id_serie, int num_episode, Date date_diffusion, String synopsis,
			String url_episode, int num_saison, String nom_serie, int vues) {
		super();
		this.id_episode = id_episode;
		this.id_saison = id_saison;
		this.id_serie = id_serie;
		this.num_episode = num_episode;
		this.date_diffusion = date_diffusion;
		this.synopsis = synopsis;
		this.url_episode = url_episode;
		Num_saison = num_saison;
		Nom_serie = nom_serie;
		this.vues = vues;
	}
	public int getId_episode() {
		return id_episode;
	}
	public void setId_episode(int id_episode) {
		this.id_episode = id_episode;
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
	public int getNum_episode() {
		return num_episode;
	}
	public void setNum_episode(int num_episode) {
		this.num_episode = num_episode;
	}
	public Date getDate_diffusion() {
		return date_diffusion;
	}
	public void setDate_diffusion(Date date_diffusion) {
		this.date_diffusion = date_diffusion;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getUrl_episode() {
		return url_episode;
	}
	public void setUrl_episode(String url_episode) {
		this.url_episode = url_episode;
	}
	public int getNum_saison() {
		return Num_saison;
	}
	public void setNum_saison(int num_saison) {
		Num_saison = num_saison;
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