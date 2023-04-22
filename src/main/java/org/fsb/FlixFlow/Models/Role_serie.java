package org.fsb.FlixFlow.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Role_serie {
    private IntegerProperty id_acteur;
    private IntegerProperty id_saison;
    private StringProperty role_type;

    public Role_serie() {
        id_acteur = new SimpleIntegerProperty();
        id_saison = new SimpleIntegerProperty();
        role_type = new SimpleStringProperty();
    }

    public Role_serie(int id_acteur, int id_saison, String role_type) {
        this.id_acteur = new SimpleIntegerProperty(id_acteur);
        this.id_saison = new SimpleIntegerProperty(id_saison);
        this.role_type = new SimpleStringProperty(role_type);
    }

    public int getId_acteur() {
        return id_acteur.get();
    }

    public void setId_acteur(int id_acteur) {
        this.id_acteur.set(id_acteur);
    }

    public IntegerProperty id_acteurProperty() {
        return id_acteur;
    }

    public int getId_saison() {
        return id_saison.get();
    }

    public void setId_saison(int id_saison) {
        this.id_saison.set(id_saison);
    }

    public IntegerProperty id_saisonProperty() {
        return id_saison;
    }

    public String getRole_type() {
        return role_type.get();
    }

    public void setRole_type(String role_type) {
        this.role_type.set(role_type);
    }

    public StringProperty role_typeProperty() {
        return role_type;
    }
}
