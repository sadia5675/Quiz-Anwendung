package de.hsrm.mi.web.projekt.ui.frage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/frage")//Gibt an, dass alle Handler-Methoden in diesem Controller auf den Pfad /frage mappen werden (URLs mit /frage), an diesen Controller weitergeleitet werden //wird auf die gesamten FrageController angewendet
@SessionAttributes("frageformular")//gibt an welche Attribute auf Serverseite gespeichert und über mehrere Anfragen hinweg verfügbar sein sollen // hier wird das Attribut mit "${frageformular}" in der Sitzung beibehalten
public class FrageController {
    private static final int MAX_FALSCH = 4;
    private static final Logger logger = LoggerFactory.getLogger(FrageController.class); //Ausgabe wie syso

    //Diese Methode wird jedes Mal aufgerufen, wenn der Controller eine Anforderung empfängt 
    //Dadurch können andere Methoden im Controller auf das FrageFormular-Objekt zugreifen, indem sie den Parameter @ModelAttribute("frageformular") FrageFormular formular in ihren Methodenaufrufen deklarieren
    @ModelAttribute("frageformular") 
    public void initFrageFormular(Model m){ //ie Übermittlung von Daten zwischen der Controller-Schicht: addAttribute() und der View-Schicht : th:text="${key}"
        FrageFormular formular = new FrageFormular();
        m.addAttribute("frageformular", formular);
        
    }

    //wenn eine GET-Anfrage an die URL "/frage/{fragenr}" gesendet wird, wird die Methode aufgerufen
    @GetMapping("{fragenr}") 
    public String anzeigen(@PathVariable String fragenr, //@PathVariable um eine Variable aus der URL-Anfrage zu extrahieren und sie als Methodeparameter zu übergeben
                            Model m, 
                            @ModelAttribute("frageformular") FrageFormular formular){ 

        m.addAttribute("fragenr",fragenr);
        m.addAttribute("maxfalsch", MAX_FALSCH);
        return "fragebearbeiten"; // gibt die View mit dem Namen "fragebearbeiten" zurück
    }
         
    //Verarbeitungsmethode für eine HTTP-POST-Anfrage an die URL "/frage/{fragenr}" //kann in der Anfrage enthaltene Variable zugreifen
    @PostMapping("{fragenr}")
    public String formular_post(@PathVariable String fragenr,
                                Model m,
                                @ModelAttribute("frageformular") FrageFormular formular,
                                @RequestParam(required = false) String neuerEintrag){ //@RequestParam(required = false) gibt an, dass der Parameter nicht erforderlich ist//Wenn der Parameter in der Anfrage enthalten ist, wird er an den Controller-Methodenparameter gebunden // view: name="name"
        
        //Mit removeIf() wird jedes Element aus der Liste entfernt, das leer ist               
        formular.getFalscheAntworten().removeIf(eintrag -> eintrag.isEmpty());
        // prüft, ob die Anzahl der falschen Antworten im Frageformular nicht bereits das maximale Limit erreicht hat
        if (formular.getFalscheAntworten().size() != MAX_FALSCH){
            if (neuerEintrag != null && !neuerEintrag.isEmpty()) { //prüft, ob die Variable neuerEintrag einen Wert hat, der nicht null und nicht leer ist
                formular.addFalscheAntwort(neuerEintrag);
            }
           
        }

        m.addAttribute("fragenr",fragenr);      
        m.addAttribute("maxfalsch", MAX_FALSCH);

        logger.info("falsche Antworten = {}", formular.getFalscheAntworten());
        
        return "fragebearbeiten";
    }


}