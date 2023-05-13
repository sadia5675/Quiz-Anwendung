package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.web.projekt.validators.Verschieden;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FrageFormular {

    @NotBlank(message="{frageformular.fehler.leer}")
    private String kategorie;
    
    @NotBlank(message="{frageformular.fragetext}")
    @Size(min = 5, max = 80, message="{frageformular.fehler.laenge}")
    private String fragetext;

    @NotBlank(message="{frageformular.fehler.leer}")
    @Size(min = 1, max = 80, message="{frageformular.fehler.laenge}")
    private String richtigeAntwort;

    @Min(value = 0, message = "{frageformular.fehler.negativPunkte}")
    @Max(value = 17, message = "{frageformular.fehler.kleinerAlsPunkte}") 
    private int punktzahl;

    @Verschieden
    @Size(min=1, message="{frageformular.fehler.falschantworten.leer}")
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
