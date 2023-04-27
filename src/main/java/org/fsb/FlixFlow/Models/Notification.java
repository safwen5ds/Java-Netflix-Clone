package org.fsb.FlixFlow.Models;

import java.sql.Date;

public class Notification {
	private java.sql.Date dateDiffusion;
	private int idEpisode;
	private int idSerie;
	private int Num_saison;
	private int numEpisode;

	private String serieNom;

	public Notification() {

	}

	public Notification(int idEpisode, int idSerie, int numEpisode, Date dateDiffusion, String serieNom,
			int num_saison) {
		this.idEpisode = idEpisode;
		this.idSerie = idSerie;
		this.numEpisode = numEpisode;
		this.dateDiffusion = dateDiffusion;
		this.serieNom = serieNom;
		Num_saison = num_saison;
	}

	public Date getDateDiffusion() {
		return dateDiffusion;
	}

	public int getIdEpisode() {
		return idEpisode;
	}

	public int getIdSerie() {
		return idSerie;
	}

	public int getNum_saison() {
		return Num_saison;
	}

	public int getNumEpisode() {
		return numEpisode;
	}

	public String getSerieNom() {
		return serieNom;
	}

	public void setDateDiffusion(Date dateDiffusion) {
		this.dateDiffusion = dateDiffusion;
	}

	public void setIdEpisode(int idEpisode) {
		this.idEpisode = idEpisode;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	public void setNum_saison(int num_saison) {
		Num_saison = num_saison;
	}

	public void setNumEpisode(int numEpisode) {
		this.numEpisode = numEpisode;
	}

	public void setSerieNom(String serieNom) {
		this.serieNom = serieNom;
	}
}
