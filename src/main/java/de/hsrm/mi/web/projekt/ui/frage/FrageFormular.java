package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;

public class FrageFormular {
    private String kategorie;
    private String fragetext;
    private String richtigeAntwort;
    private int punktzahl;
    private ArrayList<String> falscheAntworten = new ArrayList<>();

    
    public List<String> getKategorien() {
        List<String> kategorien = new ArrayList<>();
        kategorien.add("");
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
