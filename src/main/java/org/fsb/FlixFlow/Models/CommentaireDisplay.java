package org.fsb.FlixFlow.Models;

public class CommentaireDisplay {
	private String prenom;
	private String commentaire;
	private int comment_id;
	public CommentaireDisplay(String prenom, String commentaire, int comment_id) {
		super();
		this.prenom = prenom;
		this.commentaire = commentaire;
		this.comment_id = comment_id;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

}
