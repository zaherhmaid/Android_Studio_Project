package com.example.gsoft;

public class Listearticle {
    public String code;
    public String desig;
    public String stockable;
    public String prix;

    public Listearticle(String code, String desig, String stockable ,String prix){
        this.code = code;
        this.desig = desig;
        this.stockable = stockable;
        this.prix=prix;
    }

    public Listearticle(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    public String getStockable() {
        return stockable;
    }

    public void setStockable(String stockable) {
        this.stockable = stockable;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

}
