package org.fsb.FlixFlow.Models;

public class Genre {
	private int id_genre;
	private String nom;

	public Genre() {
		super();
	}

	public Genre(int id_genre, String nom) {
		super();
		this.id_genre = id_genre;
		this.nom = nom;
	}

	public int getId_genre() {
		return id_genre;
	}

	public String getNom() {
		return nom;
	}

	public void setId_genre(int id_genre) {
		this.id_genre = id_genre;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
