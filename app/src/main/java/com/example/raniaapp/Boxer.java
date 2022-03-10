package com.example.raniaapp;

public class Boxer {

    private String von;
    private String bis;
    private String tag;
    private String monat;
    private String jahr;

    public Boxer(){

    }

    public Boxer(String von, String bis, String tag, String monat, String jahr){
        this.von = von;
        this.bis = bis;
        this.tag = tag;
        this.monat = monat;
        this.jahr = jahr;
    }

    public String getVon(){
        return von;
    }
    public String getBis(){
        return bis;
    }
    public String getTag(){
        return tag;
    }
    public String getMonat(){
        return monat;
    }
    public String getJahr(){
        return jahr;
    }
}
