package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;

public class FrageFormular {
    private String fragenr;
    private String kategorie;
    private String fragetext;
    private String richtigeAntwort;
    private int punktzahl;


    //Konstruktor
    public FrageFormular(String fragenr, String kategorie,String fragetext,String richtigeAntwort,int punktzahl) {
        this.fragenr= fragenr;
        this.kategorie = kategorie;
        this.fragetext = fragetext;
        this.richtigeAntwort = richtigeAntwort;
        this.punktzahl = punktzahl;

    }

    //ein Getter soll für die Kategorien-Eigenschaft eine Java-Liste mit den Strings "", "Allgemeines", "Zahlen", "Sachen", "Orte", "Ereignisse" zurückliefern
    public static List<String> getKategorien() {
        List<String> kategorien = new ArrayList<>();
        kategorien.add("");
        kategorien.add("Allgemeines");
        kategorien.add("Zahlen");
        kategorien.add("Sachen");
        kategorien.add("Orte");
        kategorien.add("Ereignisse");
        return kategorien;
    }

    public String getFragenr() {
        return fragenr;
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

    public void setFragenr(String fragenr) {
        this.fragenr = fragenr;
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

}
