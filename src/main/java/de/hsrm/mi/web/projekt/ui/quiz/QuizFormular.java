package de.hsrm.mi.web.projekt.ui.quiz;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.entities.quiz.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class QuizFormular {
    
    @NotBlank
    private String titel;

    @NotEmpty
    private List<Frage> ausgewaehlteFragen = new ArrayList<>();

    public String getTitel() {
        return titel;
    }
    
    public void setTitel(String titel) {
        this.titel = titel;
    }
    
    public List<Frage> getAusgewaehlteFragen() {
        return ausgewaehlteFragen;
    }
    
    public void setAusgewaehlteFragen(List<Frage> ausgewaehlteFragen) {
        this.ausgewaehlteFragen = ausgewaehlteFragen;
    }
    
    //Die Methode toQuiz kopiert die Daten aus dem QuizFormular-Objekt in das übergebene Quiz-Objekt
    public void toQuiz(Quiz q) {
        q.setTitel(this.titel);
        q.setFragen(ausgewaehlteFragen);
        }
    
    //Die Methode fromQuiz kopiert die Daten aus dem übergebenen Quiz-Objekt in das QuizFormular-Objekt
    public void fromQuiz(Quiz q) {
        this.titel = q.getTitel();
        this.ausgewaehlteFragen = q.getFragen();
    }
}
