package de.hsrm.mi.web.projekt.ui.frage;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.validators.Verschieden;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FrageFormular {

    @NotBlank
    private String kategorie;
    
    @NotBlank
    @Size(min = 5, max = 80)
    private String fragetext;

    @NotBlank
    @Size(min = 1, max = 80)
    private String richtigeAntwort;

    @Min(value = 0)
    @Max(value = 17) 
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
    
    public void setFalscheAntworten(ArrayList<String> falscheAntworten) {
        this.falscheAntworten = falscheAntworten;
    }

    //Die Methode toFrage kopiert die Daten aus dem FrageFormular-Objekt in das übergebene Frage-Objekt
    public void toFrage(Frage f) {
        f.setKategorie(this.kategorie);
        f.setFragetext(this.fragetext);
        f.setPunktzahl(this.punktzahl);
        f.setRichtigeAntwort(this.richtigeAntwort);
        f.setFalscheAntworten(this.falscheAntworten);
    }
    
    //Die Methode fromFrage kopiert die Daten aus dem übergebenen Frage-Objekt in das FrageFormular-Objekt
    public void fromFrage(Frage f) {
        this.kategorie = f.getKategorie();
        this.fragetext = f.getFragetext();
        this.punktzahl = f.getPunktzahl();
        this.richtigeAntwort = f.getRichtigeAntwort();
        this.falscheAntworten = new ArrayList<>(f.getFalscheAntworten());
}

}
