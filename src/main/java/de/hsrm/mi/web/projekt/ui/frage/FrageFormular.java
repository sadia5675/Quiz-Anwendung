package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;



public class FrageFormular {

    //@Valid
    @NotBlank(message="Kategorie darf nicht leer sein")
    private String kategorie;
    
    @NotEmpty(message="Fragetext darf nicht leer sein")
    @Size(min = 5, max = 80, message="Laenge von {min} bis {max}")
    private String fragetext;

    @NotBlank
    @Size(min = 1, max = 80, message="Laenge von {min} bis {max}")
    private String richtigeAntwort;

    @Min(0) @Max(17) 
    private int punktzahl;

    @Size(min = 1, message="Du sollst mindestens 1 Falscheantwort haben")
    private ArrayList<String> falscheAntworten = new ArrayList<>();

    
    public List<String> getKategorien() {
        List<String> kategorien = new ArrayList<>();
        kategorien.add(" ");
        kategorien.add("Allgemeines");
        kategorien.add("Zahlen");
        kategorien.add("Sachen");
        kategorien.add("Orte");
        kategorien.add("Ereignisse");
        return kategorien;
    }

    public String getKategorie() {
        return kategorie;
    }
    
     public void setKategorie(String kategorie) {
        this.kategorie = kategorie; 
    }


    public int getPunktzahl() {
        return punktzahl;
    }

    public void setPunktzahl(int punktzahl) {
        this.punktzahl = punktzahl;
    }


    public String getFragetext() {
        return fragetext;
    }


    public void setFragetext(String fragetext) {
        this.fragetext = fragetext;
    }

    public String getRichtigeAntwort() {
        return richtigeAntwort;
    }

    public void setRichtigeAntwort(String richtigeAntwort) {
        this.richtigeAntwort = richtigeAntwort;
    }

    public List<String> getFalscheAntworten() {
        return falscheAntworten;
    }

    public void addFalscheAntwort(String antwort) {
        falscheAntworten.add(antwort);
    }
}
