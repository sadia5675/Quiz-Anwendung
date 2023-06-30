import { ref, readonly } from 'vue';
import { type IQuiz, type IFrage , type checkQuiz, type antwort} from '@/services/backendapitypen';
import {useInfo} from '@/services/InfoService';

const { setInfo } = useInfo();

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
      console.log(`checkQuiz responsedata = ${JSON.stringify(data)}`);

      /*convertErgebnisToMap(data);
      const d = convertErgebnisToMap(data);
      console.log(`checkQuiz d=${JSON.stringify(d)}`)
      setAntwortStatus(d);
      */


      return data;
    } catch (error: any) {
      setInfo(error.message);
      return null;
    }

    /*
    function convertErgebnisToMap(ergebnisData: any): Map<number, string> {
      const antwortStatusMap = new Map<number, string>();
      for (const ergebnis of ergebnisData.ergebnisse) {
        const antwortStatusText = ergebnis.richtig ? 'richtig' : 'falsch';
        antwortStatusMap.set(ergebnis.fid, antwortStatusText);
      }
      //console.log(JSON.stringify(antwortStatusMap.toString()));
      console.log('antwortStatusMap:', antwortStatusMap);
      return antwortStatusMap;
    }

    /*
    function convertErgebnisToMap(ergebnisData: any): Map<number, string> {
      const antwortStatusMap = new Map<number, string>();
      for (const ergebnis of ergebnisData.ergebnisse) {
        const antwortStatusText = ergebnis.richtig ? 'richtig' : 'falsch';
        antwortStatusMap.set(ergebnis.fid, antwortStatusText);
      }
      return antwortStatusMap;
    }
    
    function setAntwortStatus(antwortStatusMap: Map<number, string>) {
      antwortStatus.value = antwortStatusMap;
    }
    */



  }