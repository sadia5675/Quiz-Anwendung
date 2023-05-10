package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FrageFormular {

    @NotBlank(message="Kategorie darf nicht leer sein")
    private String kategorie;
    
    @NotBlank(message="Kategorie darf nicht leer sein")
    @Size(min = 5, max = 80, message="Länge muss von {min} bis {max} sein")
    private String fragetext;

    @NotBlank(message="Kategorie darf nicht leer sein")
    @Size(min = 1, max = 80, message="Die Länge muss von {min} bis {max} sein")
    private String richtigeAntwort;

    @Min(value = 0, message = "Die Punktzahl darf nicht negativ sein.")
    @Max(value = 17, message = "Die Punktzahl darf maximal 17 sein.") 
    private int punktzahl;

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
