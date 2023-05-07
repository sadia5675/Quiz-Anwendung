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
@RequestMapping("/frage")
@SessionAttributes("frageformular")
public class FrageController {
    private static final int MAX_FALSCH = 4;
    private static final Logger logger = LoggerFactory.getLogger(FrageController.class); //Ausgabe wie syso

    @ModelAttribute("frageformular")
    public void initFrageFormular(Model m){
        FrageFormular formular = new FrageFormular();
        m.addAttribute("frageformular", formular);
        
    }

    @GetMapping("{fragenr}") 
    public String anzeigen(@PathVariable String fragenr, Model m){ 
        
        m.addAttribute("fragenr",fragenr);
        m.addAttribute("maxfalsch", MAX_FALSCH);

        return "fragebearbeiten";
    }
         

    @PostMapping("{fragenr}")
    public String formular_post( @PathVariable String fragenr,Model m,
                                @ModelAttribute("frageformular") FrageFormular formular,
                                @RequestParam("neuerEintrag") String neuerEintrag
                                ){
                                
        m.addAttribute("fragenr",fragenr);      
        m.addAttribute("maxfalsch", MAX_FALSCH);

        logger.info("neuerEintrag = {}", neuerEintrag);

        if (formular.getFalscheAntworten().size() < MAX_FALSCH){
            if(!neuerEintrag.isEmpty()){
                formular.addFalscheAntwort(neuerEintrag);
            }
           
        }


        //Schleife, die geht durch alle falsche Antworten durch. falls leer -> soll aus Liste rausnehmen
        int index = 0;
        for (String eintrage:formular.getFalscheAntworten()){
            
            if (eintrage.isEmpty()) {
                formular.removeFalscheAntwort(index);
            }
            System.out.println(eintrage + " index: " + index);
            index++;
        }


        // Vorherige Versuche, Schleife zu machen 
        
       /*  if(neuerEintrag == null){
            formular.removeFalscheAntwort(formular.getFalscheAntwortIndex(null));
        } */
      
        /* 
        List<String> falscheAntworten = formular.getfalscheAntworten();

        for (int i = 0; i < falscheAntworten.size(); i++) {
            String antwort = falscheAntworten.get(i);
            if (antwort == null || antwort.trim().isEmpty()) {
                formular.removeFalscheAntwort(i);
                falscheAntworten.remove(i);
            }
        }
        */
        

        logger.info("falsche Antworten = {}", formular.getFalscheAntworten());
        
        return "fragebearbeiten";
    }


}