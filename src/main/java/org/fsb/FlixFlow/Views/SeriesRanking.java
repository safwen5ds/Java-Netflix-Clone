package org.fsb.FlixFlow.Views;

public class SeriesRanking {
	private int idSerie;
	private String nom;
	private int rank;
	private int views;

	public SeriesRanking(int idSerie, String nom, int views, int rank) {
		this.idSerie = idSerie;
		this.nom = nom;
		this.views = views;
		this.rank = rank;
	}

	public int getIdSerie() {
		return idSerie;
	}

	public String getNom() {
		return nom;
	}

	public int getRank() {
		return rank;
	}

	public int getViews() {
		return views;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setViews(int views) {
		this.views = views;
	}

}