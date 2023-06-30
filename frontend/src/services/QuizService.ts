import { ref, readonly, reactive } from 'vue';
import { type IQuiz, type IFrage , type checkQuiz, type antwort, type checkQuizResponse} from '@/services/backendapitypen';
import {useInfo} from '@/services/InfoService';

const { setInfo } = useInfo();

export const quiz = ref<IQuiz>({
  id: 0,
  titel: '',
  punktesumme:0,
  fragen: [],
  });

export const readonlyQuiz = readonly(quiz);

//export const antwortStatusMap = reactive ({map: new Map <number, string>() })


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
  

  export async function checkQuiz(quizId: number, antworten: Map<number, string>): Promise<any> {
    try {
      const beantworteteFragenArray: antwort[] = Array.from(antworten.entries()).map(([fid, antwort]) => ({
        fid,
        antwort,
      }));
  
      const checkQuizData: checkQuiz = {
        qid: quizId,
        antworten: beantworteteFragenArray,
      };
  
      const response = await fetch(`/api/quiz/check`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(checkQuizData),
      });

      
      console.log(JSON.stringify(checkQuizData));
      if (!response.ok) {
        throw new Error(response.statusText);
      }
  
      const data = await response.json();
      console.log(`QuizService: antwort vom Backend = ${JSON.stringify(data)}`);
      return data as checkQuizResponse;
      
      
    } catch (error: any) {
      setInfo(error.message);
      return null;
    }

  }

    