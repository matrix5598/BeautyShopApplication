package com.example.raniaapp;

public class Employee {

    String von, bis, tag, monat, jahr;

    public Employee(String von, String bis, String tag, String monat, String jahr){
        this.von = von;
        this.bis = bis;
        this.tag = tag;
        this.monat = monat;
        this.jahr = jahr;
    }

    public String getVon(){
        return von;
    }
    public void setVon(){
        this.von = von;
    }

    public String getBis(){
        return bis;
    }
    public void setBis(){
        this.bis = bis;
    }

    public String getTag(){
        return tag;
    }
    public void setTag(){
        this.tag = tag;
    }

    public String getMonat(){
        return monat;
    }
    public void setMonat(){
        this.monat = monat;
    }

    public String getJahr(){
        return jahr;
    }
    public void setJahr(){
        this.jahr = jahr;
    }
}
