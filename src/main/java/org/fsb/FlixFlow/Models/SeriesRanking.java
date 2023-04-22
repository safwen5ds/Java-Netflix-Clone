package org.fsb.FlixFlow.Models;

public class SeriesRanking {
    private int idSerie;
    private String nom;
    private int views;
    private int rank;

    public SeriesRanking(int idSerie, String nom, int views, int rank) {
        this.idSerie = idSerie;
        this.nom = nom;
        this.views = views;
        this.rank = rank;
    }

	public int getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

   
}