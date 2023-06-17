import { ref, readonly } from 'vue';
import { type IQuiz, type IFrage } from '@/services/backendapitypen';


export function useQuiz() {
    
    const quiz = ref<IQuiz>({
        id: 1,
        titel: 'Allgemeines Wissen',
        punktesumme: 14,
        fragen: [
          {
            frageid: 1,
            fragetext: 'Was ist der Hauptstadt von Thailand?',
            alleantworten: ['Tokyo', 'Bangkok', 'Dakka', 'China'],
            punkte: 4,
            katname: 'Geografie',
          },
          {
            frageid: 2,
            fragetext: 'Wie viele Aufgabenblätter gibt es dieses Semester im Web?',
            alleantworten: ['11', '12', 'unendlich'],
            punkte: 10,
            katname: 'Uni',
          },
          {
            frageid: 3,
            fragetext: 'Wer bist du?',
            alleantworten: ['Mensch', 'Affe', 'KI', 'keine Ahnung'],
            punkte: 10,
            katname: 'Uni',
          }
        ],
      });

    

    return {
        quiz: readonly(quiz),
      };

  }