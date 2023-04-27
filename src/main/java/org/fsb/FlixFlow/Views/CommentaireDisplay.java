package org.fsb.FlixFlow.Views;

public class CommentaireDisplay {
	private int comment_id;
	private String commentaire;
	private String prenom;

	public CommentaireDisplay(String prenom, String commentaire, int comment_id) {
		super();
		this.prenom = prenom;
		this.commentaire = commentaire;
		this.comment_id = comment_id;
	}

	public int getComment_id() {
		return comment_id;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}
