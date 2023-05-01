package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;

public class FrageFormular {

    private String kategorie;
    private String fragetext;
    private String richtigeAntwort;
    private int punktzahl;


    //Konstruktor
    public FrageFormular(String kategorie,String fragetext,String richtigeAntwort,int punktzahl) {
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


    public int getPunktzahl() {
        return punktzahl;
    }

    public void setPunktzahl(int punktzahl) {
        this.punktzahl = punktzahl;
    }




}
