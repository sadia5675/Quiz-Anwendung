package de.hsrm.mi.web.projekt.services.quizdienste;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import de.hsrm.mi.web.projekt.entities.Kategorie.Kategorie;
import de.hsrm.mi.web.projekt.entities.Kategorie.KategorieRepository;
import de.hsrm.mi.web.projekt.entities.frage.FrageRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Optional;

@Service
public class FrageQuelleServiceTheTriviaAPI implements FrageQuelleService {

    public record Frage(String fragetext, int punktzahl, Kategorie kategorie) {
    }

    @Autowired
    Validator vali;

    @Autowired private FrageRepository frageRepository;
    @Autowired private KategorieRepository kategorieRepository;

    @Override
    public List<Frage> generiereNeueFragen(int n) {

        List<Frage> neueFragen = new ArrayList<>();

        WebClient webClient = WebClient.create("https://the-trivia-api.com/api");
        var antwort = webClient.get().uri("/questions?limit=" + n)
                .retrieve() // Abruf
                .bodyToFlux(Frage.class) // Antwort auf Zieltyp abbilden
                .collectList()
                .block(); // warten, bis Ergebnis vorliegt, und zurückgeben
                
        if (antwort != null) {
            System.out.println(antwort); // Ausgabe der Antwort vor der Schleife
            for (Frage a : antwort) {
                System.out.println(a.kategorie);
                System.out.println(a.fragetext);
                System.out.println(a.punktzahl);
            }

            for (Frage frage : antwort) {
                Set<ConstraintViolation<Frage>> violations = vali.validate(frage); // stellt sicher, dass jede
                                                                                   // Verletzung
                                                                                   // nur einmal in der Sammlung
                                                                                   // vorkommt,
                                                                                   // unabhängig davon, wie oft sie
                                                                                   // gemeldet
                                                                                   // wird

                if (!violations.isEmpty()) {
                    for (ConstraintViolation<Frage> violation : violations) {
                        System.out.println("Fehler: " + violation.getMessage());
                        System.out.println("Property: " + violation.getPropertyPath());
                        System.out.println("Invalid Value: " + violation.getInvalidValue());
                        continue;
                    }
                } else {
                    Optional<Frage> vorhandeneFrage = frageRepository
                            .findByFragetext(frage.fragetext);

                    Optional<Kategorie> vorhandeneKategorie = kategorieRepository.findByName(frage.kategorie.getName());



                    // Überprüfung, ob die Kategorie bereits in der Datenbank existiert
                    if (vorhandeneKategorie.isPresent()) {
                        System.out.println(frage.kategorie + " existiert bereits in der Datenbank");
                        continue;
                    } else {
                        // Kategorie existiert noch nicht in der Datenbank
                        Kategorie neueKategorie = new Kategorie();
                        neueKategorie.setName(frage.kategorie.getName());
                        neueKategorie.setBeschreibung(frage.kategorie.getName());
                        kategorieRepository.save(neueKategorie);
                    }


                    // Überprüfung, ob die Frage bereits in der Datenbank existiert
                    if (vorhandeneFrage.isPresent()) {
                        System.out.println(frage.fragetext + " existiert bereits in der Datenbank");
                        continue;
                    } else {
                        // Frage existiert noch nicht in der Datenbank
                        Frage neueFrage = new Frage(frage.fragetext, 1, frage.kategorie);
                        frageRepository.save(neueFrage);
                    }
                }
                neueFragen.add(frage);
            }
        }
        return neueFragen;

    }

}
