package de.hsrm.mi.web.projekt.entities.benutzer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Benutzer {

    @Id
    @NotNull
    @Size(min = 2, max = 80)
    private String benutzername;

    @NotNull
    @Size(min = 2, max = 80)
    private String losung;

    private String rolle = "USER";

    private int punkte;

    
    
    //Standardkonstruktor um die Erstellung von Benutzer-Objekten zu ermöglichen
    public Benutzer() {}


    public Benutzer(String benutzername, String losung) {
        this.benutzername = benutzername;
        this.losung = losung;
    }


    //-------------Getter & Setter--------------

     public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getLosung() {
        return losung;
    }

    public void setLosung(String losung) {
        this.losung = losung;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }



}
