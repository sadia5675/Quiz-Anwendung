package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;

public class FrageFormular {
    private String kategorie;
    private String fragetext;
    private String richtigeAntwort;
    private int punktzahl;
    private List<String> falscheAntworten = new ArrayList<>();

    
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

    public void addFalscheAntwort(String antwort) {
        falscheAntworten.add(antwort);
    }

    public void removeFalscheAntwort(int index) {
        falscheAntworten.remove(index);
    }

    public int getFalscheAntwortIndex(String antwort){
        return falscheAntworten.indexOf(antwort);
    }

    public List<String> getfalscheAntworten() {
        return falscheAntworten;
    }

    public String getKategorie() {
        return kategorie;
    }
    
    public String getFragetext() {
        return fragetext;
    }


    public String getRichtigeAntwort() {
        return richtigeAntwort;
    }


    public int getPunktzahl() {
        return punktzahl;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie; 
    }

    public void setFragetext(String fragetext) {
        this.fragetext = fragetext;
    }

    public void setRichtigeAntwort(String richtigeAntwort) {
        this.richtigeAntwort = richtigeAntwort;
    }

    public void setPunktzahl(int punktzahl) {
        this.punktzahl = punktzahl;
    }

    public List<String> getFalscheAntworten() {
        return falscheAntworten;
    }

    public void setFalscheAntworten(List<String> falscheAntworten) {
        this.falscheAntworten = falscheAntworten;
    }
}
