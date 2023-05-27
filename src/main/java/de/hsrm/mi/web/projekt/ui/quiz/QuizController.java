/**
 * @author Sadia Miah
 */

package de.hsrm.mi.web.projekt.ui.quiz;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.entities.quiz.Quiz;
import de.hsrm.mi.web.projekt.services.frage.FrageService;
import de.hsrm.mi.web.projekt.services.quiz.QuizService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes({"quizFormular","quiz"})
public class QuizController {

    @Autowired private QuizService quizService;
    @Autowired private FrageService frageService;

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class); 
    

    @ModelAttribute("quizFormular") 
    public void initQuizFormular(Model m){ 
        QuizFormular formular = new QuizFormular();
        m.addAttribute("quizFormular", formular);
    }

    @ModelAttribute("quiz")
    public Quiz initQuiz() {
        return new Quiz();
    }

    @GetMapping("/quiz")
    public String getQuizList(Model m) {
        List<Quiz> quizList = quizService.holeAlleQuiz();
        m.addAttribute("quizList", quizList);
        return "quizliste";
    }    

    @GetMapping("/quiz/{quiznr}")
    public String showQuizForm(@PathVariable int quiznr,
                                Model m, 
                                @ModelAttribute("quizFormular") QuizFormular formular,
                                @ModelAttribute("quiz") Quiz quiz){
        

        List<Frage> fragen = frageService.holeAlleFragen();
        m.addAttribute("fragen", fragen);                            

        if(quiznr == 0){
            formular = new QuizFormular(); 
            m.addAttribute("quizFormular", formular); 
            m.addAttribute("quiz", new Quiz());
        }

        if(quiznr > 0){
            Optional<Quiz> optionalQuiz = quizService.holeQuizMitId(quiznr);
            if (optionalQuiz.isPresent()) {
                quiz = optionalQuiz.get();
                formular.fromQuiz(quiz); 
                m.addAttribute("quiz", quiz); 
              
            }
        }

        return "quizbearbeiten";
    }

    @GetMapping("/quiz/{id}/del")
    public String deleteQuiz(@PathVariable("id") Long id){
        quizService.loescheQuiz(id);
        return "redirect:/quiz";
    }

    @PostMapping("/quiz/{quiznr}")
    public String submitQuizForm(@PathVariable long quiznr,
                                Model m,
                                @Valid @ModelAttribute("quizFormular") QuizFormular formular,
                                BindingResult formularErrors,
                                @ModelAttribute("quiz") Quiz quiz
                                ) {

        List<Frage> fragen = frageService.holeAlleFragen();
        m.addAttribute("fragen", fragen);

        if (formularErrors.hasErrors()) {
            logger.info("Errors = {}", formularErrors);
            return "quizbearbeiten";
        
        }else{
            formular.toQuiz(quiz);
            try{
                Quiz gespeicherteQuiz = quizService.speichereQuiz(quiz);
                m.addAttribute("quiz", gespeicherteQuiz);
                if (quiznr == 0) {
                    return "redirect:/quiz/" + gespeicherteQuiz.getId();
                } else {
                    return "quizbearbeiten";
                }

            }catch (RuntimeException e) {
                String errorMessage = "Fehler beim Speichern vom quiz: " + e.getMessage();
                m.addAttribute("info", errorMessage);
                logger.error(errorMessage);
                return "quizbearbeiten";
            }
        }
        }
    
    }
