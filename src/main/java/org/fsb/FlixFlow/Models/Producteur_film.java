package org.fsb.FlixFlow.Models;

public class Producteur_film {
	private int id_producteur_film;
	private int id_film ;
	public Producteur_film() {
		super();
	}
	public Producteur_film(int id_producteur_film, int id_film) {
		super();
		this.id_producteur_film = id_producteur_film;
		this.id_film = id_film;
	}
	public int getId_producteur_film() {
		return id_producteur_film;
	}
	public void setId_producteur_film(int id_producteur_film) {
		this.id_producteur_film = id_producteur_film;
	}
	public int getId_film() {
		return id_film;
	}
	public void setId_film(int id_film) {
		this.id_film = id_film;
	}

}
