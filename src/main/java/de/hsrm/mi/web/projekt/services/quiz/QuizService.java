/**
 * @author Sadia Miah
 */
package de.hsrm.mi.web.projekt.services.quiz;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.web.projekt.entities.quiz.Quiz;

public interface QuizService {
    List<Quiz> holeAlleQuiz();
    public Optional<Quiz> holeQuizMitId(long id);
    Quiz speichereQuiz(Quiz q);
    void loescheQuiz(long id);
}
