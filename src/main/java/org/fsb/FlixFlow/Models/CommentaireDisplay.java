package org.fsb.FlixFlow.Models;

import javafx.beans.property.StringProperty;

public class CommentaireDisplay {
    private String prenom;
    private String commentaire;

    public CommentaireDisplay(String prenom, String commentaire) {
        this.prenom = prenom;
        this.commentaire = commentaire;
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

   
}
