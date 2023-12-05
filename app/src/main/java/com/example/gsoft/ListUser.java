package com.example.gsoft;

public class ListUser {

public String type;
public String nom;





    public ListUser(String nom, String type) {
        this.nom=nom;
        this.type=type;
    }


public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }
}
