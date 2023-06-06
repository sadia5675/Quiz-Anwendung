package de.hsrm.mi.web.projekt.ui.frage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.Kategorie.Kategorie;
import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.services.frage.FrageService;
import de.hsrm.mi.web.projekt.services.kategorie.KategorieService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes({"frageformular","frage"})//gibt an welche Attribute auf Serverseite gespeichert und über mehrere Anfragen hinweg verfügbar sein sollen // hier wird das Attribut mit "${frageformular}" in der Sitzung beibehalten
public class FrageController {
    private static final int MAX_FALSCH = 4;
    private static final Logger logger = LoggerFactory.getLogger(FrageController.class); //Ausgabe wie syso
    
    //Instanz von FrageServiceImpl automatisch erstellt 
    @Autowired private FrageService frageService;

    @Autowired
    private KategorieService kategorieService;

    
    //Diese Methode wird jedes Mal aufgerufen, wenn der Controller eine Anforderung empfängt 
    //Dadurch können andere Methoden im Controller auf das FrageFormular-Objekt zugreifen, indem sie den Parameter @ModelAttribute("frageformular") FrageFormular formular in ihren Methodenaufrufen deklarieren
    @ModelAttribute("frageformular") 
    public void initFrageFormular(Model m){ //ie Übermittlung von Daten zwischen der Controller-Schicht: addAttribute() und der View-Schicht : th:text="${key}"
        FrageFormular formular = new FrageFormular();
        m.addAttribute("frageformular", formular);
    }

    @ModelAttribute("frage")
    public Frage initFrage() {
        return new Frage();
    }

    //----------------------------------------------GET MAPPING---------------------------------------------------------------------------------

    //sortierte Liste aller Fragen nach Kategorie und Punktzahl zu erhalten und in frageliste.html anzuzeigen 
    @GetMapping("/frage")
    public String fragenListe(Model m) {
        List<Frage> fragen = frageService.holeAlleFragen();
        m.addAttribute("fragen", fragen);
        return "frageliste";
    }
    //die Frage mit der angegebenen ID wird gelöscht //"redirect:/frage" wird zurückgegeben, um auf die Frageliste Seite umgeleitet zu werden
    @GetMapping("/frage/{id}/del")
    public String deleteFrage(@PathVariable("id") Long id,
                              Model m){
        try {
            frageService.loescheFrage(id);
        } catch (RuntimeException e) {
            String errorMessage = "Fehler beim Löschen der Frage: " + e.getMessage();
            m.addAttribute("info", errorMessage); 
            logger.error(errorMessage);
        }
        return "redirect:/frage";
    }
    
    //wenn eine GET-Anfrage an die URL "/frage/{fragenr}" gesendet wird, wird die Methode aufgerufen
    @GetMapping("/frage/{fragenr}") 
    public String anzeigen(@PathVariable int fragenr, //@PathVariable um eine Variable aus der URL-Anfrage zu extrahieren und sie als Methodeparameter zu übergeben
                            Model m, 
                            @ModelAttribute("frageformular") FrageFormular formular,
                            @ModelAttribute("frage") Frage frage){ 
        
        if(fragenr == 0){
            formular = new FrageFormular(); // neue leere FrageFormular
            m.addAttribute("frageformular", formular); //frageformular-Session-Attribut wird mit einer neuen leeren Instanz von FrageFormular initialisiert
            m.addAttribute("frage", new Frage()); // neue leere Frage //frage-Session-Attribut wird mit einer neuen leeren Instanz von Frage initialisiert
        }

        if(fragenr > 0){
            Optional<Frage> optionalFrage = frageService.holeFrageMitId(fragenr);
            if (optionalFrage.isPresent()) {
                frage = optionalFrage.get();//gibt den darin enthaltenen Wert zurück
                formular.fromFrage(frage); // Daten aus der Frage in das FrageFormular übertragen
                m.addAttribute("frage", frage); // Das Frage-Objekt im Session-Attribut frage speichern
            }
        }

        List<Kategorie> kategorien = kategorieService.holeAlleKategorien();
        m.addAttribute("kategorien", kategorien);

        m.addAttribute("fragenr",fragenr);
        m.addAttribute("maxfalsch", MAX_FALSCH);
        return "fragebearbeiten"; // gibt die View mit dem Namen "fragebearbeiten" zurück
    }

    @GetMapping("/frage/quizdienst")
    public String generiereNeueFragenUndRedirect() {
        frageService.generiereNeueFragen(3);
        return "redirect:/frage";
    }

    
    //----------------------------------------------POST MAPPING-------------------------------------------------------------------------------------------

    //Verarbeitungsmethode für eine HTTP-POST-Anfrage an die URL "/frage/{fragenr}" //kann in der Anfrage enthaltene Variable zugreifen
    @PostMapping("/frage/{fragenr}")
    public String formular_post(@PathVariable int fragenr,
                                Model m,
                                @Valid @ModelAttribute("frageformular") FrageFormular formular,
                                BindingResult formularErrors,
                                @ModelAttribute("frage") Frage frage,
                                @RequestParam(required = false) String neuerEintrag
                                ){ //@RequestParam(required = false) gibt an, dass der Parameter nicht erforderlich ist//Wenn der Parameter in der Anfrage enthalten ist, wird er an den Controller-Methodenparameter gebunden // view: name="name"
        
       
        //Mit removeIf() wird jedes Element aus der Liste entfernt, das leer ist               
        formular.getFalscheAntworten().removeIf(eintrag -> eintrag.isEmpty());
        // prüft, ob die Anzahl der falschen Antworten im Frageformular nicht bereits das maximale Limit erreicht hat
        if (formular.getFalscheAntworten().size() != MAX_FALSCH){
            if (neuerEintrag != null && !neuerEintrag.isEmpty()) { //prüft, ob die Variable neuerEintrag einen Wert hat, der nicht null und nicht leer ist
                formular.addFalscheAntwort(neuerEintrag);
            }
           
        }
        List<Kategorie> kategorien = kategorieService.holeAlleKategorien();
        m.addAttribute("kategorien", kategorien);

        m.addAttribute("fragenr",fragenr);      
        m.addAttribute("maxfalsch", MAX_FALSCH);


        if(formularErrors.hasErrors()) {
            logger.info("Errors = {}", formularErrors);
            return "fragebearbeiten";

        }else{
            formular.toFrage(frage);
            try {
                //gespeicherteFrage in das Session-Attribut frage gespeichert, um sicherzustellen, dass es immer die aktuellste Version enthält
                Frage gespeicherteFrage = frageService.speichereFrage(frage);
                m.addAttribute("frage", gespeicherteFrage);
                if (fragenr == 0) {
                    //leitet den Nutzer per Redirect auf die "korrekte" URL /frage/id weiter, um nicht auf der Neuanlegen-URI /frage/0 zu bleiben
                    return "redirect:/frage/" + gespeicherteFrage.getId();
                } else {
                    return "fragebearbeiten";
                }
            } catch (RuntimeException e) {
                String errorMessage = "Fehler beim Speichern der Frage:" + e.getMessage();
                m.addAttribute("info", errorMessage);
                logger.error(errorMessage);
                return "fragebearbeiten";
                }
            }
    }


}