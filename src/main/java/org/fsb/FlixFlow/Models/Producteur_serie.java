package org.fsb.FlixFlow.Models;

public class Producteur_serie {
	private int id_producteur_serie;
	private int id_serie;

	public Producteur_serie() {
		super();
	}

	public Producteur_serie(int id_producteur_serie, int id_serie) {
		super();
		this.id_producteur_serie = id_producteur_serie;
		this.id_serie = id_serie;
	}

	public int getId_producteur_serie() {
		return id_producteur_serie;
	}

	public int getId_serie() {
		return id_serie;
	}

	public void setId_producteur_serie(int id_producteur_serie) {
		this.id_producteur_serie = id_producteur_serie;
	}

	public void setId_serie(int id_serie) {
		this.id_serie = id_serie;
	}

}
