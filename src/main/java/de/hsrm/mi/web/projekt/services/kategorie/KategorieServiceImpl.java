package de.hsrm.mi.web.projekt.services.kategorie;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.Kategorie.Kategorie;
import de.hsrm.mi.web.projekt.entities.Kategorie.KategorieRepository;

@Service
public class KategorieServiceImpl implements KategorieService {

    private final KategorieRepository kategorieRepository;

    @Autowired
    public KategorieServiceImpl(KategorieRepository kategorieRepository) {
        this.kategorieRepository = kategorieRepository;
    }


    @Override
    public List<Kategorie> holeAlleKategorien() {

        List<Kategorie> kategorien = kategorieRepository.findAll();
        //LOGGER.info("Alle Kategorien wurden abgerufen."); 
        return kategorien;
    }

    @Override
    public Optional<Kategorie> holeKategorieMitId(long id) {

        Optional<Kategorie> kategorie = kategorieRepository.findById(id);

        if (kategorie.isPresent()) {
           // LOGGER.info("Kategorie mit ID {} wurde gefunden.", id);
        } else {
           // LOGGER.info("Kategorie mit ID {} wurde nicht gefunden.", id);
        }
        
        return kategorie;
    }

    @Override
    public Kategorie speichereKategorie(Kategorie k) {
        
        Kategorie gespeicherteKategorie = kategorieRepository.save(k);
        //LOGGER.info("Kategorie mit ID {} wurde gespeichert.", gespeicherteKategorie.getId());
        return gespeicherteKategorie;
    }

    @Override
    public void loescheKategorie(long id) {
        
        kategorieRepository.deleteById(id);
        //LOGGER.info("Kategorie mit ID {} wurde gelöscht.", id);
    }
    
    
}
