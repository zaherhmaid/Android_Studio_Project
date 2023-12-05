package com.example.gsoft;

import java.util.Date;
public class Listecaisse {
    public String id;
    public String nom;
    public String recette;
    public Date daterec;
    public String emplacement;


    public Listecaisse( String nom , String recette, String emplacement,Date daterec ) {
        this.nom = nom;
        this.recette = recette;
        this.emplacement=emplacement;
        this.daterec=daterec;

    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRecette() {
        return recette;
    }

    public void setRecette(String recette) {
        this.recette = recette;
    }

    public Date getDaterec() {
        return daterec;
    }

    public void setDaterec(Date daterec) {
        this.daterec = daterec;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }
}


