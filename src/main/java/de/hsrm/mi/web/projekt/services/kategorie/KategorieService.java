package de.hsrm.mi.web.projekt.services.kategorie;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.web.projekt.entities.Kategorie.Kategorie;

public interface KategorieService {
    
    List<Kategorie> holeAlleKategorien();
    Optional<Kategorie> holeKategorieMitId(long id);
    Kategorie speichereKategorie(Kategorie k);
    void loescheKategorie(long id);
    int getAnzahlFragen(Kategorie kategorie);
    }