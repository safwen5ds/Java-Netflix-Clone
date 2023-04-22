package org.fsb.FlixFlow.Models;

import java.sql.Date;

public class Notification {
	private int idEpisode;
	private int idSerie;
	private int numEpisode;
	private java.sql.Date dateDiffusion;
	private String serieNom;

	private int Num_saison;

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

	public int getIdEpisode() {
		return idEpisode;
	}

	public void setIdEpisode(int idEpisode) {
		this.idEpisode = idEpisode;
	}

	public int getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	public int getNumEpisode() {
		return numEpisode;
	}

	public void setNumEpisode(int numEpisode) {
		this.numEpisode = numEpisode;
	}

	public Date getDateDiffusion() {
		return dateDiffusion;
	}

	public void setDateDiffusion(Date dateDiffusion) {
		this.dateDiffusion = dateDiffusion;
	}

	public String getSerieNom() {
		return serieNom;
	}

	public void setSerieNom(String serieNom) {
		this.serieNom = serieNom;
	}

	public int getNum_saison() {
		return Num_saison;
	}

	public void setNum_saison(int num_saison) {
		Num_saison = num_saison;
	}
}
