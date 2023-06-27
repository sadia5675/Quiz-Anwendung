/* eslint-disable @typescript-eslint/no-unused-vars */
import { ref, computed, readonly } from 'vue';
import { type IQuizInfo, type IFrontendNachrichtEvent } from '@/services/backendapitypen';
import { useInfo } from '@/services/InfoService';
import Stomp, { Client, type Message } from '@stomp/stompjs';

const { setInfo } = useInfo();

export const quizListe = ref<IQuizInfo[]>([]);

export async function updateQuizInfoListe() {
  try {
    const response = await fetch('/api/quiz');

    if (!response.ok) {
      throw new Error(response.statusText);
    }
    
    const data = await response.json();
    quizListe.value = data;
    console.log(data);

  } catch (error: any) {
    setInfo(error.message);
  }
}

export function useQuizService() {
    // Simulierte Daten für die Quiz-Liste
    const quizListe = ref<IQuizInfo[]>([
      { id: 1, name: 'Was mit Tieren', nFragen: 5 },
      { id: 2, name: 'Sachen, Dinge', nFragen: 3 },
      { id: 3, name: 'Orte', nFragen: 7 },
    ]);
  
}


let stompClient: Client | null = null;

export async function startQuizLiveUpdate() {
  if (stompClient !== null) {
    return; // Verbindung existiert bereits, nichts weiter unternehmen
  }

  try {
    // Absolute WS-URL zusammensetzen, Host/Port wie Frontend-Anw.
    const wsurl = `ws://${window.location.host}/stompbroker`;
    const DEST = '/topic/quiz';

    // STOMP-Client erstellen
    stompClient = new Client({ brokerURL: wsurl });

    // WebSocket-Fehler behandeln
    stompClient.onWebSocketError = (event) => {setInfo('WebSocket-Fehler');}

    // STOMP-Fehler behandeln
    stompClient.onStompError = (frame) => {setInfo('STOMP-Fehler');}

    // Callback: erfolgreicher Verbindugsaufbau zu Broker
    stompClient.onConnect = (frame) => {
      // Callback: Nachricht auf DEST empfangen
      stompClient?.subscribe(DEST, (message: Message) => {
        try {
          // Nachricht als JSON-String empfangen und in JavaScript-Objekt umwandeln
          const frontendNachricht: IFrontendNachrichtEvent = JSON.parse(message.body);

          // Bei Typfeld-Inhalt QUIZ die updateQuizInfoListe()-Funktion aufrufen
          if (frontendNachricht.nachrichtentyp === 'QUIZ') {
            updateQuizInfoListe();
          }
        } catch (error: any) {
          setInfo(error.message);
        }
      });
    };

    // Verbindung zum Broker aufbauen
    stompClient.activate();
  } catch (error: any) {
    setInfo(error.message);
  }
}