package org.fsb.FlixFlow.Models;
import java.sql.Date;

public class Notification {
    private int idEpisode;
    private int idSerie;
    private int numEpisode;
    private java.sql.Date dateDiffusion;
    private String serieNom;
    public Notification(int idEpisode, int idSerie, int numEpisode, java.sql.Date dateDiffusion, String serieNom) {
        this.idEpisode = idEpisode;
        this.idSerie = idSerie;
        this.numEpisode = numEpisode;
        this.dateDiffusion = dateDiffusion;
        this.serieNom = serieNom;
    }

    public Notification() {
		// TODO Auto-generated constructor stub
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

    public java.sql.Date getDateDiffusion() {
        return dateDiffusion;
    }

    public void setDateDiffusion(java.sql.Date dateDiffusion) {
        this.dateDiffusion = dateDiffusion;
    }

    public String getSerieNom() {
        return serieNom;
    }

    public void setSerieNom(String serieNom) {
        this.serieNom = serieNom;
    }
}
