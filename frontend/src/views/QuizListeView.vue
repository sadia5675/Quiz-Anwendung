<template>
    <div>
      <input type="text" v-model="suchbegriff" placeholder="Suche nach Quiz" />
      <button @click="resetSuchbegriff">Reset</button>
      <ul>
        <li v-for="quiz in gefilterteListe" :key="quiz.id">
          <router-link :to="'/quiz/' + quiz.id">{{ quiz.name }}</router-link>
          ({{ quiz.nFragen }} Fragen)
        </li>
      </ul>
    </div>
  </template>


<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { quizListe, updateQuizInfoListe, startQuizLiveUpdate } from '../services/QuizListeService';

updateQuizInfoListe();

const suchbegriff = ref('');

// Code für den Suchfeld
const gefilterteListe = computed(() => {
  const filter = suchbegriff.value.toLowerCase().trim();
  if (!filter) {
    return quizListe.value; // Wenn im Suchfeld nichts steht soll die Liste einfach angezeigt werden
  }
  return quizListe.value.filter((quiz) => 
    quiz.name.toLowerCase().includes(filter)
  );
});

function resetSuchbegriff() {
  suchbegriff.value = '';
}

onMounted(() => {
  startQuizLiveUpdate().catch((error) => {
    console.error('WebSocket-Fehler:', error);
    // Behandeln Sie den Fehler entsprechend Ihrer Anforderungen,
    // z. B. durch Anzeigen einer Fehlermeldung im UI oder erneuten Verbindungsaufbau.
  });
});

</script>


<style scoped>
div {
    margin: 20px;
    padding: 10px;
    border: 1px solid #ccc;
    background-color: #f5f5f5;
  }

  input[type="text"] {
    padding: 5px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }

button {
    padding: 5px 10px;
    font-size: 16px;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    border-radius: 4px;
    cursor: pointer;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    margin-bottom: 10px;
  }


</style>