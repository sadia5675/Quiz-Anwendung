
<template>
  <div>
    <header>
      <!-- Hier kann der Inhalt der Kopfzeile platziert werden -->
      <div class="header">
      <h3 class="font">QuizDing</h3>
      <button class="hello-button" @click="setInfo('Hallo')">Hallo</button> <!-- Test-Button zum Setzen der Info-Nachricht -->
      </div>
    </header>

    <div class="info-bar" v-if="readonlyInfonachricht.infonachricht !== ''">
        <span class="font">{{ readonlyInfonachricht.infonachricht}}</span>
        <button class="x-button" @click="clearInfo">x</button>
      </div>

    <!-- Hier wird der Hauptinhalt der Anwendung eingefügt -->
    <main>
      <!-- Der Router wird den eigentlichen Hauptinhalt später einblenden -->
      <RouterView/>
    </main>
  </div>
</template>


<script setup lang="ts">

import { useInfo } from '@/services/InfoService'
import QuizListeView from '@/views/QuizListeView.vue';



const { readonlyInfonachricht, setInfo } = useInfo();

function clearInfo(): void {
  setInfo('');
}
</script>

<style scoped>
/* Styles für die Fehler-Statuszeile */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgb(32, 32, 255);
  padding: 1rem;
  color: white;
}

.info-bar {
  background-color: rgb(255, 162, 215);
  padding: 0.5rem;
  border-radius: 10px;
  display: flex;
  justify-content: space-between;
  margin-left: 1em;
  margin-right:1em;
  align-items: center;
  color: darkred;

}

.x-button {
  background-color: rgb(255, 162, 215);
  color: darkred;
  border: none;
  padding: 0.5rem;
  cursor: pointer;
  border: 2px solid darkred;
  border-radius: 5px;
}

.x-button:hover {
  color:  darkred;
  border: 2px solid  darkred;
}
.font{
  font-family: 'Arial';
  font-size: medium;
  /* Weitere Schriftart-Eigenschaften hier */
}

.hello-button {
  background-color: rgb(32, 32, 255);
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  cursor: pointer;
}

.hello-button:hover {
  color: rgb(204, 204, 204);
}

</style>
