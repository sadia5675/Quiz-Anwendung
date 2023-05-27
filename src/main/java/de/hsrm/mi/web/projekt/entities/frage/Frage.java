package de.hsrm.mi.web.projekt.entities.frage;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity //gibt an, dass die Klasse eine JPA-Entität ist und in einer Datenbank gespeichert werden kann
public class Frage {
    @Id //eindeutige Kennung (Primärschlüssel)
    @GeneratedValue // ID-Wert automatisch vergeben lassen
    private long id;

    @Version//JPA kann Kollisionen bei gleichzeitigen Änderungen an einer Entität erkennen und behandeln
    private long version;

    @NotNull//nicht null sein darf
    private String fragetext;

    @NotNull
    private String richtigeAntwort;

    @NotNull
    private int punktzahl;

    @NotNull
    private String kategorie;

    @ElementCollection //dass es sich um eine Sammlung von Elementen handelt, die in einer separaten Tabelle in der Datenbank gespeichert werden sollen
    private List<String> falscheAntworten;
    
    //Standardkonstruktor um die Erstellung von Frage-Objekten zu ermöglichen
    public Frage() {}
    
    //Werte direkt beim Erstellen der Frage festzulegen
    public Frage(String fragetext, String richtigeAntwort, int punktzahl, String kategorie, List<String> falscheAntworten) {
        this.fragetext = fragetext;
        this.richtigeAntwort = richtigeAntwort;
        this.punktzahl = punktzahl;
        this.kategorie = kategorie;
        this.falscheAntworten = falscheAntworten;
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public List<String> getFalscheAntworten() {
        return falscheAntworten;
    }

    public void setFalscheAntworten(List<String> falscheAntworten) {
        this.falscheAntworten = falscheAntworten;
    }
}