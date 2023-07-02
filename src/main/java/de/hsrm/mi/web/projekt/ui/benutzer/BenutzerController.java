package de.hsrm.mi.web.projekt.ui.benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import jakarta.validation.Valid;

@Controller
public class BenutzerController {
    
    @Autowired
    private BenutzerService benutzerService;

    public void initBenutzerFormulierernFormular(Model m) {
        BenutzerFormulierernFormular benutzerFormular = new BenutzerFormulierernFormular();
        m.addAttribute("kategorieFormular", benutzerFormular);
    }


    @ModelAttribute("benutzer")
    public Benutzer initBenutzer() {
        return new Benutzer();
    }


    @GetMapping("/registrieren")
    public String anzeigenBenutzerForm(Model m) {
        m.addAttribute("benutzerFormular", new BenutzerFormulierernFormular());
        return "registrieren";
    }

    @PostMapping("/registrieren")
    public String registrierenBenutzer(@Valid @ModelAttribute("benutzerFormular") BenutzerFormulierernFormular benutzerFormular,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registrieren";
        }

        // Prüfung, ob der Benutzername schon vergeben ist
        if (!benutzerService.istBenutzernameVerfuegbar(benutzerFormular.getBenutzername())) {
            bindingResult.rejectValue("benutzername", "error.benutzername", "Benutzername bereits vergeben");
            return "registrieren";
        }

        if (!benutzerFormular.getLosung().equals(benutzerFormular.getLosungBestaetigen())) {
        bindingResult.rejectValue("losungBestaetigung", "error.losungBestaetigen", "Passwörter weichen voneinander ab");
        return "registrieren";
    }

        benutzerService.erstelleBenutzer(benutzerFormular.getBenutzername(), benutzerFormular.getLosung());

        return "redirect:/quiz";
    }











}
