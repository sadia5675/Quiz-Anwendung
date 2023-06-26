import { ref, readonly } from 'vue';
import { type IQuiz, type IFrage } from '@/services/backendapitypen';
import {useInfo} from '@/services/InfoService';

const { setInfo } = useInfo();


/*export const fragen = ref<IFrage>({
  frageid: 0,
  fragetext: '',
  alleantworten: [],
  punkte: 0,
  katname: '',
  });*/

export const quiz = ref<IQuiz>({
  id: 0,
  titel: '',
  punktesumme:0,
  fragen: [],
  });

export const readonlyQuiz = readonly(quiz);


export async function updateQuiz(qid: number){
   try {
    const response = await fetch(`/api/quiz/${qid}`);

    if (!response.ok) {
      throw new Error(response.statusText);
    }

    const data = await response.json();
    quiz.value = data;
  
  } catch (error: any) {
    setInfo(error.message);
  }
  }


  export async function checkQuiz(quizId: number, beantworteteFragen: Map<number, string>): Promise<any> {
    try {
      const response = await fetch(`/api/quiz/check`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          quizId,
          beantworteteFragen: Array.from(beantworteteFragen.entries()),
        }),
      });
  
      if (!response.ok) {
        throw new Error(response.statusText);
      }
  
      const data = await response.json();
      return data;
    } catch (error: any) {
      setInfo(error.message);
      return null;
    }
  }