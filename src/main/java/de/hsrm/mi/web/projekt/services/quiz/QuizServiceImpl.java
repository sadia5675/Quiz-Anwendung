/**
 * @author Sadia Miah
 */
package de.hsrm.mi.web.projekt.services.quiz;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.configuration.messaging.FrontendNachrichtEvent;
import de.hsrm.mi.web.projekt.configuration.messaging.FrontendNachrichtService;
import de.hsrm.mi.web.projekt.configuration.messaging.Nachrichtentyp;
import de.hsrm.mi.web.projekt.configuration.messaging.Operation;
import de.hsrm.mi.web.projekt.entities.quiz.Quiz;
import de.hsrm.mi.web.projekt.entities.quiz.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

    // um auf die Datenbank zuzugreifen
    private final QuizRepository quizRepository;
    private final FrontendNachrichtService frontendNachrichtService;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizServiceImpl.class);

    // Instanz des FrageRepository automatisch erstellt
    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, FrontendNachrichtService frontendNachrichtService) {
        this.quizRepository = quizRepository;
        this.frontendNachrichtService = frontendNachrichtService;
    }

    @Override
    public List<Quiz> holeAlleQuiz() {
        List<Quiz> quiz = quizRepository.findAll();
        LOGGER.info("Alle Quizze wurden abgerufen.");
        return quiz;
    }

    @Override
    public Optional<Quiz> holeQuizMitId(long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            LOGGER.info("Quiz mit ID {} wurde gefunden.", id);
        } else {
            LOGGER.info("Quiz mit ID {} wurde nicht gefunden.", id);
        }
        return quiz;
    }

    @Override
    public Quiz speichereQuiz(Quiz q) {
        Quiz gespeicherteQuiz = quizRepository.save(q);
        LOGGER.info("Quiz mit ID {} wurde gespeichert.", gespeicherteQuiz.getId());

        // Nachricht an Frontend senden
        FrontendNachrichtEvent event = new FrontendNachrichtEvent(Nachrichtentyp.QUIZ,
                String.valueOf(gespeicherteQuiz.getId()),
                Operation.UPDATE);
        frontendNachrichtService.sendEvent(event);

        return gespeicherteQuiz;
    }

    @Override
    @PreAuthorize("hasRole('CHEF')")
    public void loescheQuiz(long id) {
        quizRepository.deleteById(id);
        LOGGER.info("Quiz mit ID {} wurde gelöscht.", id);

        // Nachricht an Frontend senden
        FrontendNachrichtEvent event = new FrontendNachrichtEvent(Nachrichtentyp.QUIZ, String.valueOf(id),
                Operation.DELETE);
        frontendNachrichtService.sendEvent(event);
    }

}
