package de.hsrm.mi.web.projekt.ui.kategorie;
import de.hsrm.mi.web.projekt.entities.Kategorie.Kategorie;
import jakarta.validation.constraints.NotBlank;

/**
@author Ana-Maria Adanaia
 */

public class KategorieFormular {

    @NotBlank
    private String name;
    
    @NotBlank
    private String beschreibung;

    
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

    //Die Methode toFrage kopiert die Daten aus dem FrageFormular-Objekt in das übergebene Frage-Objekt
    public void toKategorie(Kategorie k) {
        k.setName(this.name);
        k.setBeschreibung(this.beschreibung);
    }
    
    //Die Methode fromFrage kopiert die Daten aus dem übergebenen Frage-Objekt in das FrageFormular-Objekt
    public void fromKategorie(Kategorie k) {

        this.name = k.getName();
        this.beschreibung = k.getBeschreibung();
    
    }

}
