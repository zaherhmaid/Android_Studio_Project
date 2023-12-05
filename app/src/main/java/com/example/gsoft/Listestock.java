package com.example.gsoft;

public class Listestock {

    public String datep;
    public String qte;
    public String p;
    public String remise;


    public Listestock(String datep , String qte , String p , String remise) {
        this.datep = datep;
        this.qte = qte;
        this.p = p;
        this.remise = remise;
    }

    public String getDatep() {
        return datep;
    }

    public void setDatep(String datep) {
        this.datep = datep;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getRemise() {
        return remise;
    }

    public void setRemise(String remise) {
        this.remise = remise;
    }


}