package com.example.raniaapp;

import android.provider.ContactsContract;
import android.widget.Spinner;

public class KundenEmployee {

    String VorNachnameKey, EmailKey, HandyKey, Behandlungs, Termins;

    public KundenEmployee(String VorNachnameKey, String EmailKey, String HandyKey, String Behandlungs, String Termins){
        this.VorNachnameKey = VorNachnameKey;
        this.EmailKey = EmailKey;
        this.HandyKey = HandyKey;
        this.Behandlungs = Behandlungs;
        this.Termins = Termins;

    }

    public String getVorNachnameKey(){
        return VorNachnameKey;
    }
    public void setVorNachnameKey(){
        this.VorNachnameKey = VorNachnameKey;
    }

    public String getEmailKey(){
        return EmailKey;
    }
    public void setEmailKey(){
        this.EmailKey = EmailKey;
    }

    public String getHandyKey(){return HandyKey;}
    public void setHandyKey(){this.HandyKey = HandyKey;}

    public String getBehandlungs(){
        return Behandlungs;
    }
    public void setBehandlungs(){
        this.Behandlungs = Behandlungs;
    }

    public String getTermins(){
        return Termins;
    }
    public void setTermins(){
        this.Termins = Termins;
    }
}
