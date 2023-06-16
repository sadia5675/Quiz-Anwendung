import { ref, computed, readonly } from 'vue';
import { type IQuizInfo } from '@/services/backendapitypen';



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
    };
  }