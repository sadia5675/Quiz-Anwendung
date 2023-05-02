package de.hsrm.mi.web.projekt.ui.frage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/frage")//wenn URL-Pfad "/frage" sendet, wird sie von diesen Controller bearbeitet
@SessionAttributes("fragenformular")//"fragenformular" wird als Attribut in Session-Kontext gespeichert
public class FrageController {

    private static final Logger logger = LoggerFactory.getLogger(FrageController.class); //Ausgabe wie syso

    //siehe Folie 63... // die Initialisierung
    @ModelAttribute("fragenformular")//Hier wird ein Attribut "fragenformular" im Model erzeugt und mit dem zurückgegebenen Objekt aus der Methode befüllt
    public FrageFormular getFrageFormular(){
        return new FrageFormular("", "", "", "", 0);
    }

    @GetMapping("{fragenr}") //GET-Anfragen an eine URL die mit /{fragenr} endet die{} bedeutet das es eine Pfadvarible ist
    public String anzeigen(HttpSession session,@PathVariable String fragenr, Model m, // nimmt fragenr entgegen...
                        @ModelAttribute("fragenformular") FrageFormular frageFormular ){ 

        frageFormular.setFragenr(fragenr);
        m.addAttribute("fragenr",fragenr);//...und fügt es der Model instanz (Model wird verwendet um Daten an eine View weiterzuleiten)
       
        return "fragebearbeiten";// gibt die view zurück (fragebearbeiten.html)
    }

    @PostMapping("{fragenr}")
    public String formular_post(Model m, HttpSession session,
                                @PathVariable("fragenr") String fragenr, 
                                @RequestParam("kategorie") String kategorie,
                                @RequestParam("fragetext") String fragetext,
                                @RequestParam("richtigeAntwort") String richtigeAntwort,
                                @RequestParam("punktzahl") int punktzahl,
                                @ModelAttribute("fragenformular") FrageFormular frageFormular){
        
        frageFormular.setKategorie(kategorie);
        frageFormular.setFragetext(fragetext);
        frageFormular.setRichtigeAntwort(richtigeAntwort);
        frageFormular.setPunktzahl(punktzahl);
        session.setAttribute("fragenformular",frageFormular);// speichern vom aktualisierten Formular im Session-Kontext
        
       //logger zeigt uns die ganzen Sachen die wir eingegeben haben
        logger.info("Kategorie: {}, Fragetext: {}, Richtige Antwort: {}, Punktzahl: {}",
                kategorie, fragetext, richtigeAntwort, punktzahl);                            

        return "fragebearbeiten";// gibt die view zurück (fragebearbeiten.html)
    }


}