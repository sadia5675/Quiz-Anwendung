package de.hsrm.mi.web.projekt.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsrm.mi.web.projekt.entities.frage.Frage;
import de.hsrm.mi.web.projekt.entities.quiz.Quiz;
import de.hsrm.mi.web.projekt.services.quiz.QuizService;

@RestController
public class QuizRestAPIController {

    @Autowired
    private QuizService quizService;

    // Record für Quiz-Liste
    public record QuizInfoDTO(long id, String name, int nFragen) {
    }

    // Record für Frage-Informationen
    public record FrageDTO(long frageid, String fragetext, List<String> alleantworten, int punkte, String katname) {
    }

    // Record für Quiz-DTO
    public record QuizDTO(long id, String titel, int punktesumme, List<FrageDTO> fragen) {
    }

    // Objekt Mapper umwandelt ein Java-Objekt in JSON und anders rum
    private ObjectMapper objectMapper = new ObjectMapper();

    // Link: http://localhost:8080/rest/api/quiz
    @GetMapping("/api/quiz")
    public ResponseEntity<String> getAllQuizzes() throws JsonProcessingException {
        List<Quiz> quizze = quizService.holeAlleQuiz();
        List<QuizInfoDTO> quizList = new ArrayList<>();

        for (Quiz quiz : quizze) {
            QuizInfoDTO quizInfo = new QuizInfoDTO(quiz.getId(), quiz.getTitel(), quiz.getFragen().size());
            quizList.add(quizInfo);
        }
        // DTO-Objekt quizList wird in einen JSON-String umgewandelt
        String json = objectMapper.writeValueAsString(quizList);

        return ResponseEntity.ok(json);
    }

    // Link: http://localhost:8080/rest/quiz/....
    @GetMapping("api/quiz/{id}")
    public ResponseEntity<String> getQuizById(@PathVariable("id") long id) throws JsonProcessingException {
        Optional<Quiz> optionalQuiz = quizService.holeQuizMitId(id);

        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            List<Frage> fragen = quiz.getFragen();

            // Die Summe von Punkten für den Quizz soll berechnet werden
            int punktesumme = berechnePunkteSumme(fragen);

            List<FrageDTO> frageList = new ArrayList<>();

            for (Frage frage : fragen) {
                // Füge alle falsche Antworten ein
                List<String> alleantworten = new ArrayList<>(frage.getFalscheAntworten());
                // Fügt die richtige Antowrt ein
                alleantworten.add(frage.getRichtigeAntwort());
                // Die Liste wird gemischt, damit man nicht weiß welche Antwort richtig ist
                Collections.shuffle(alleantworten);

                FrageDTO frageInfo = new FrageDTO(frage.getId(), frage.getFragetext(), alleantworten,
                        frage.getPunktzahl(), frage.getKategorie().getName());

                frageList.add(frageInfo);
            }

            QuizDTO quizInfo = new QuizDTO(quiz.getId(), quiz.getTitel(), punktesumme, frageList);

            // DTO-Objekt quizInfo wird in einen JSON-String umgewandelt
            String json = objectMapper.writeValueAsString(quizInfo);

            // Es wird ein ResponseEntity-Objekt zurückgegeben, das den JSON-String enthält.
            return ResponseEntity.ok(json); // Das ok bedeutet -> HTTP-Statuscode 200 (OK).
        } else { // notFound() bedeutet -> HTTP-Statuscode 404 (Not Found)
            return ResponseEntity.notFound().build(); // Das build()-Aufruf erzeugt ein ResponseEntity-Objekt ohne
                                                      // Inhalt des Antwortkörpers.
        }
    }

    // Hilfsmethode zur Berechnung der Punktesumme aller Fragen
    private int berechnePunkteSumme(List<Frage> fragen) {
        int summe = 0;
        for (Frage frage : fragen) {
            summe += frage.getPunktzahl();
        }
        return summe;
    }

}
