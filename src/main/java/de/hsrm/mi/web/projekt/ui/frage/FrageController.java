package de.hsrm.mi.web.projekt.ui.frage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/frage")//wenn URL-Pfad "/frage" sendet, wird sie von diesen Controller bearbeitet
@SessionAttributes("frageformular")//"fragenformular" wird als Attribut in Session-Kontext gespeichert
public class FrageController {

    //private static final Logger logger = LoggerFactory.getLogger(FrageController.class); //Ausgabe wie syso

    //siehe Folie 63... // die Initialisierung
    @ModelAttribute("frageformular")//Hier wird ein Attribut "fragenformular" im Model erzeugt und mit dem zurückgegebenen Objekt aus der Methode befüllt
    public void initFrageFormular(Model m){
        FrageFormular formular = new FrageFormular();
       
       /*  formular.setKategorie("");
        formular.setFragetext("");
        formular.setRichtigeAntwort("");                  <----- das ganze brauchen wir nicht weil es automatisch schon leer ist. 
        formular.setPunktzahl(0);
        */ 
        m.addAttribute("frageformular", formular);
        
    }

    @GetMapping("{fragenr}") 
    public String anzeigen(@PathVariable String fragenr, Model m ){ 
        m.addAttribute("fragenr",fragenr);
        return "fragebearbeiten";
    }
         

    @PostMapping("{fragenr}")
    public String formular_post(Model m, 
                                @ModelAttribute("frageformular") FrageFormular formular){
        
                                    
       //logger zeigt uns die ganzen Sachen die wir eingegeben haben
        //logger.info("Kategorie: {}, Fragetext: {}, Richtige Antwort: {}, Punktzahl: {}",
                //FrageFormular.getKategorien(), fragetext, richtigeAntwort, punktzahl);                            

        return "fragebearbeiten";// gibt die view zurück (fragebearbeiten.html)
    }


}