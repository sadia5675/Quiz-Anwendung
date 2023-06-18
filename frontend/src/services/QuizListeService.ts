import { ref, computed, readonly } from 'vue';
import { type IQuizInfo } from '@/services/backendapitypen';
import {useInfo} from '@/services/InfoService';

const { setInfo } = useInfo();

export const quizListe = ref<IQuizInfo[]>([]);

export async function updateQuizInfoListe() {
  try {
    console.log('Fetching quiz data...'); // Zwischenmeldung: Abfrage von Quiz-Daten

    const response = await fetch('/rest/api/quiz');

    if (!response.ok) {
      throw new Error(response.statusText);
    }

    console.log('Quiz data fetched successfully.'); // Zwischenmeldung: Quiz-Daten erfolgreich abgerufen

    const data = await response.json();
    quizListe.value = data;
  } catch (error: any) {
    setInfo(error.message);
    console.error('Error fetching quiz data:', error); // Zwischenmeldung: Fehler beim Abrufen von Quiz-Daten
  }
}

export function useQuizService() {
    // Simulierte Daten für die Quiz-Liste
    const quizListe = ref<IQuizInfo[]>([
      { id: 1, name: 'Was mit Tieren', nFragen: 5 },
      { id: 2, name: 'Sachen, Dinge', nFragen: 3 },
      { id: 3, name: 'Orte', nFragen: 7 },
    ]);
  
    // Suchfeld-Status
    const suchbegriff = ref('');
  
    // Gefilterte Liste basierend auf Suchbegriff
    const gefilterteListe = computed(() => {
      const filter = suchbegriff.value.toLowerCase().trim();
      if (!filter) {
        return quizListe.value;
      }
      return quizListe.value.filter((quiz) =>
        quiz.name.toLowerCase().includes(filter)
      );
    });
  
    // Funktion zum Zurücksetzen des Suchbegriffs
    function resetSuchbegriff() {
      suchbegriff.value = '';
    }
  
    return {
      quizinfoliste: readonly(gefilterteListe),
      suchbegriff,
      resetSuchbegriff,
      updateQuizInfoListe
    };
  }