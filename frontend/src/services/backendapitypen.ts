//public record QuizInfoDTO(long id, String name, int nFragen) {}
//public record FrageDTO(long frageid, String fragetext, List<String> alleantworten, int punkte, String katname) {}
//public record QuizDTO(long id, String titel, int punktesumme, List<FrageDTO> fragen) {}

// Record für Quiz-Liste
export interface IQuizInfo {
  id: number
  name: string
  nFragen: number
}

// Record für Frage-Informationen
export interface IFrage {
  frageid: number
  fragetext: string
  alleantworten: string[]
  punkte: number
  katname: string
}

// Record für Quiz-DTO
export interface IQuiz {
  id: number
  titel: string
  punktesumme: number
  fragen: IFrage[]
}

// Record für checkQuiz
export interface checkQuiz {
  qid: number
  antworten: antwort[]
}

//Record für die antwort
export interface antwort {
  fid: number 
  antwort: String
}

// Record für die response von checkQuiz Methode
export interface checkQuizResponse {
  qid: number
  antworten: antwortResponse[]
}

export interface antwortResponse {
  qid: number
  richtig: boolean
}

export interface IFrontendNachrichtEvent {
  nachrichtentyp: string;
  id: string;
  operation: string;
}
