package de.hsrm.mi.web.projekt.services.frage;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.entities.frage.FrageRepository;
import de.hsrm.mi.web.projekt.services.quizdienste.FrageQuelleServiceTheTriviaAPI;

@Service
public class FrageServiceImpl implements FrageService {
    // um auf die Datenbank zuzugreifen
    private final FrageRepository frageRepository;
    
    private final FrageQuelleServiceTheTriviaAPI frageQuelleService;;

    private static final Logger LOGGER = LoggerFactory.getLogger(FrageServiceImpl.class);

    // Instanz des Repository automatisch erstellt
    @Autowired
    public FrageServiceImpl(FrageRepository frageRepository, FrageQuelleServiceTheTriviaAPI frageQuelleService) {
        this.frageRepository = frageRepository;
        this.frageQuelleService = frageQuelleService;
    }
    

    // um die Ergebnisse nach der Kategorie und dann nach der Punktzahl aufsteigend
    // zu sortieren
    @Override
    public List<Frage> holeAlleFragen() {
        Sort sort = Sort.by("kategorie").ascending().and(Sort.by("punktzahl").ascending());// ascending()->Sortierung in
                                                                                           // aufsteigender Reihenfolge
        List<Frage> fragen = frageRepository.findAll(sort);
        LOGGER.info("Alle Fragen wurden abgerufen.");
        return fragen;
    }

    // sucht eine Frage in der Datenbank anhand der gegebenen ID
    @Override
    public Optional<Frage> holeFrageMitId(long id) {
        Optional<Frage> frage = frageRepository.findById(id);
        if (frage.isPresent()) {// Überprüft, ob das Optional einen Wert enthält
            LOGGER.info("Frage mit ID {} wurde gefunden.", id);
        } else {
            LOGGER.info("Frage mit ID {} wurde nicht gefunden.", id);
        }
        return frage;
    }

    // speichert die übergebene Frage in der Datenbank und gibt das gespeicherte
    // Frage-Objekt zurück
    @Override
    public Frage speichereFrage(Frage f) {
        Frage gespeicherteFrage = frageRepository.save(f);
        LOGGER.info("Frage mit ID {} wurde gespeichert.", gespeicherteFrage.getId());
        return gespeicherteFrage;
    }

    // löscht die Frage mit der angegebenen ID aus der Datenbank
    @Override
    public void loescheFrage(long id) {
        frageRepository.deleteById(id);
        LOGGER.info("Frage mit ID {} wurde gelöscht.", id);
    }

    @Override
    public void generiereNeueFragen(int count) {
        for (int i = 1; i < count; i++) {
        List<Frage> neueFragen = frageQuelleService.generiereNeueFragen(i);
        }
        
    }
}
