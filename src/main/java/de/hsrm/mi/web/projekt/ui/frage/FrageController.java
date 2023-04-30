package de.hsrm.mi.web.projekt.ui.frage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/frage")//wenn URL-Pfad "/frage" sendet, wird sie von diesen Controller bearbeitet
public class FrageController {

    private static final Logger logger = LoggerFactory.getLogger(FrageController.class); //Ausgabe wie syso

    @GetMapping("{fragenr}") //GET-Anfragen an eine URL die mit /{fragenr} endet die{} bedeutet das es eine Pfadvarible ist
    public String anzeigen(@PathVariable String fragenr, Model m ){ // nimmt fragenr entgegen...
        logger.info("Frage Nr. {} wird angezeigt.", fragenr);
        m.addAttribute("fragenr",fragenr);//...und fügt es der Model instanz (Model wird verwendet um Daten an eine View weiterzuleiten)
        return "fragebearbeiten";// gibt die view zurück (fragebearbeiten.html)
    }
}
