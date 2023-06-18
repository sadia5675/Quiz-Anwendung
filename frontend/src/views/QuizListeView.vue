<template>
    <div>
      <input type="text" v-model="suchbegriff" placeholder="Suche nach Quiz" />
      <button @click="resetSuchbegriff">Reset</button>
      <ul>
        <li v-for="quiz in quizinfoliste" :key="quiz.id">
          <router-link :to="'/quiz/' + quiz.id">{{ quiz.name }}</router-link>
          ({{ quiz.nFragen }} Fragen)
        </li>
      </ul>
      <button @click="updateQuizInfoListe">Quizliste aktualisieren</button>
    </div>
  </template>




<script setup lang="ts">

import { useQuizService } from '../services/QuizListeService';

const { quizinfoliste, suchbegriff, resetSuchbegriff, updateQuizInfoListe } = useQuizService();

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