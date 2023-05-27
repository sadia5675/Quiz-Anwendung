package de.hsrm.mi.web.projekt.entities.Kategorie;
import java.util.ArrayList;
import java.util.List;
import de.hsrm.mi.web.projekt.entities.frage.Frage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

/**
@author Ana-Maria Adanaia
 */

@Entity //gibt an, dass die Klasse eine JPA-Entität ist und in einer Datenbank gespeichert werden kann
public class Kategorie {

    @Id //eindeutige Kennung (Primärschlüssel)
    @GeneratedValue // ID-Wert automatisch vergeben lassen
    private long id;
    
    @Version//JPA kann Kollisionen bei gleichzeitigen Änderungen an einer Entität erkennen und behandeln
    private long version;

    @NotNull
    @Column(unique = true) //Kategorien-Namen müssen eindeutig sein
    private String name;

    @NotNull
    private String beschreibung;

    //zu einer (one) Kategorie gehören beliebig viele (many) Fragen
    @OneToMany(mappedBy = "kategorie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) 
    private List<Frage> fragen = new ArrayList<>();
    

    public Kategorie() {};

    public Kategorie(String name, String beschreibung) {
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    
    public List<Frage> getFragen() {
        return fragen;
    }

    public void setFragen(List<Frage> fragen) {
        this.fragen = fragen;
    }

    public int getAnzahlFragen() {
        return fragen.size();
    }

    public void addFrage(Frage neueFrage) {
        fragen.add(neueFrage);
        neueFrage.setKategorie(this);
    }

    public void removeFrage(Frage frage) {
        fragen.remove(frage);
        frage.setKategorie(null);
    }



}
