package de.hsrm.mi.web.projekt.services.quizdienste;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import de.hsrm.mi.web.projekt.entities.Kategorie.Kategorie;
import de.hsrm.mi.web.projekt.entities.Kategorie.KategorieRepository;
import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.entities.frage.FrageRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Optional;

@Service
public class FrageQuelleServiceTheTriviaAPI implements FrageQuelleService {

    public record frageRecord(String question, String correctAnswer, int points, Kategorie category,
            List<String> incorrectAnswers) {
    }

    @Autowired
    Validator vali;

    @Autowired
    private FrageRepository frageRepository;
    @Autowired
    private KategorieRepository kategorieRepository;

    @Override
    public List<Frage> generiereNeueFragen(int n) {

        List<Frage> neueFragen = new ArrayList<>();

        WebClient webClient = WebClient.create("https://the-trivia-api.com/api");
        var antwort = webClient.get().uri("/questions?limit=" + n)
                .retrieve() // Abruf
                .bodyToFlux(frageRecord.class) // Antwort auf Zieltyp abbilden
                .collectList()
                .block(); // warten, bis Ergebnis vorliegt, und zurückgeben

        if (antwort != null) {
            for (frageRecord frageRec : antwort) {
                Frage checkFrage = new Frage();
                checkFrage.setFragetext(frageRec.question);
                checkFrage.setKategorie(frageRec.category);
                checkFrage.setPunktzahl(1);
                checkFrage.setFalscheAntworten(frageRec.incorrectAnswers);
                checkFrage.setRichtigeAntwort(frageRec.correctAnswer);

                // stellt sicher, dass jede Verletzung nur einmal in der Sammlung vorkommt, unabhängig davon, wie oft sie gemeldet wird
                Set<ConstraintViolation<Frage>> violations = vali.validate(checkFrage);

                if (!violations.isEmpty()) {
                    for (ConstraintViolation<Frage> violation : violations) {
                        System.out.println("Fehler: " + violation.getMessage());
                        System.out.println("Property: " + violation.getPropertyPath());
                        System.out.println("Invalid Value: " + violation.getInvalidValue());
                        continue;
                    }
                } else {

                    // Überprüfung, ob die Kategorie bereits in der Datenbank existiert
                    Optional<Kategorie> vorhandeneKategorie = kategorieRepository
                            .findByName(checkFrage.getKategorie().getName());
        
                    if (vorhandeneKategorie.isPresent()) {
                        System.out.println(frageRec.category + " existiert bereits in der Datenbank");
                        continue;
                    } else {
                        // Kategorie existiert noch nicht in der Datenbank
                        Kategorie neueKategorie = new Kategorie();
                        neueKategorie.setName(frageRec.category.getName());
                        neueKategorie.setBeschreibung(frageRec.category.getName());//beschreibung durfte nich null sein XD
                        kategorieRepository.save(neueKategorie);

                        //neue Kategorie der Frage geben
                        checkFrage.setKategorie(neueKategorie);
                    }
                    // Überprüfung, ob die Frage bereits in der Datenbank existiert
                    Optional<Frage> vorhandeneFrage = frageRepository.findByFragetext(checkFrage.getFragetext());

                    if (vorhandeneFrage.isPresent()) {
                        System.out.println(frageRec.question + " existiert bereits in der Datenbank");
                        continue;
                    } else {
                        // Frage existiert noch nicht in der Datenbank
                        frageRepository.save(checkFrage);
                    }
                }
                neueFragen.add(checkFrage);
            }
        }
        return neueFragen;

    }

}
