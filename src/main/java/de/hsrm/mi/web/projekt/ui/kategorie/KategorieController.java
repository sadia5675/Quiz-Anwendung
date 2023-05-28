package de.hsrm.mi.web.projekt.ui.kategorie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import de.hsrm.mi.web.projekt.entities.Kategorie.Kategorie;
import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.services.frage.FrageService;
import de.hsrm.mi.web.projekt.services.kategorie.KategorieService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
@author Ana-Maria Adanaia
 */

 
@Controller
@SessionAttributes({"kategorieFormular","kategorie"})
public class KategorieController {

    @Autowired private KategorieService kategorieService;
    @Autowired private FrageService frageService;

    private static final Logger logger = LoggerFactory.getLogger(KategorieController.class); 

    @ModelAttribute("kategorieFormular") 
    public void initFrageFormular(Model m){ 
        KategorieFormular formular = new KategorieFormular();
        m.addAttribute("kategorieFormular", formular);
    }


    // mit der @ModelAttribute-Annotation wird Kategorie initialisiert und in der Sitzung beibehalten
    @ModelAttribute("kategorie")
    public Kategorie initKategorie() {
        return new Kategorie();
    }


    //----------------------------------------------GET MAPPING---------------------------------------------------------------------------------


    // Wenn diese URL aufgerufen wird, werden alle Kategorien aus der Datenbank abgerufen und dem Model hinzugefügt. 
    @GetMapping("/kategorie")
    public String kategorienListe(Model m) {
        List<Kategorie> kategorien = kategorieService.holeAlleKategorien(); //  Die Liste der Kategorien wird dem Model mit dem Attributsnamen "kategorien" hinzugefügt
        m.addAttribute("kategorien", kategorien);
        return "kategorieliste";  // Anschließend wird die View "kategorien" zurückgegeben
    }

    @GetMapping("/kategorie/{id}/del")
    public String deleteKategorie(@PathVariable("id") Long id,Model m){
       
        try {
            kategorieService.loescheKategorie(id);
            
        } catch (RuntimeException e) {
            String errorMessage = "Fehler beim Löschen der Kategorie: " + e.getMessage();
            m.addAttribute("info", errorMessage); logger.error(errorMessage);
        }
            return "redirect:/kategorie";
    }

    // Wert der {id} aus der URL-Anfrage wird extrahiert und kann in der Methode weiter verwendet werden
    @GetMapping("/kategorie/{id}")
    public String anzeigen(@PathVariable long id, 
                           Model m,
                           @ModelAttribute("kategorieFormular") KategorieFormular formular,
                           @ModelAttribute("kategorie") Kategorie kategorie) {
    
    List<Frage> fragen = frageService.holeAlleFragen();
    m.addAttribute("fragen", fragen); 

    if (id == 0) {
        formular = new KategorieFormular(); // Neue leere KategorieFormular wird erstellt und der Variable "formular" zugewiesen
        m.addAttribute("kategorieFormular", formular); //"formular" wird als Attribut mit dem Namen "kategorieFormular" dem Model m hinzugefügt. Damit kann das Formular in der View verwendet werden.
        m.addAttribute("kategorie", new Kategorie()); // Neue leere Kategorie wird erstellt und mit dem Namen "kategorie" dem Model m hinzugefügt
    }
    
    if (id > 0) {
        Optional<Kategorie> optionalKategorie = kategorieService.holeKategorieMitId(id); //die Kategorie mit der angegebenen id wird in einem Optional-Typ "optionalKategorie" gespeichert.
        if (optionalKategorie.isPresent()) { //Es wird überprüft, ob die optionalKategorie (mit der angegebenen id) existiert 
            kategorie = optionalKategorie.get(); // Der Wert wird geholt und der Variable kategorie zugewiesen 
            formular.fromKategorie(kategorie); // Die Daten aus der kategorie werden über die Methode fromKategorie(kategorie) in das KategorieFormular übertragen
            m.addAttribute("kategorie", kategorie); // Die Kategorie wird als Attribut mit dem Namen "kategorie" dem Model m hinzugefügt
        }
    }
    
   
    return "kategoriebearbeiten"; // Die View mit dem Namen "kategoriebearbeiten" zurückgeben
}



//----------------------------------------------POST MAPPING---------------------------------------------------------------------------------------

    
    @PostMapping("/kategorie/{id}")
    public String formular_post(@PathVariable long id,
                                Model m,
                                @Valid @ModelAttribute("kategorieFormular") KategorieFormular formular,
                                BindingResult formularErrors,
                                @ModelAttribute("kategorie") Kategorie kategorie
                                ) {

        
        List<Frage> fragen = frageService.holeAlleFragen();
        m.addAttribute("fragen", fragen); 

        if (formularErrors.hasErrors()) {
            logger.info("Errors = {}", formularErrors);
            return "kategorieBearbeiten";
        } else {
            formular.toKategorie(kategorie);
            try {
                Kategorie gespeicherteKategorie = kategorieService.speichereKategorie(kategorie);
                m.addAttribute("kategorie", gespeicherteKategorie);
                if (id == 0) {
                    return "redirect:/kategorie/" + gespeicherteKategorie.getId();
                } else {
                    return "kategorieBearbeiten";
                }
            } catch (RuntimeException e) {
                String errorMessage = "Fehler beim Speichern der Kategorie: " + e.getMessage();
                m.addAttribute("info", errorMessage);
                logger.error(errorMessage);
                return "kategorieBearbeiten";
            }
        }
    }
    

}