<template>
    
    <div>
    <h2>Quiz ID: {{ quizId }}</h2>
    <h3>{{ quiz.titel }}</h3>
    <p>Punktesumme: {{ quiz.punktesumme }}</p>

    <ul>
      <li v-for="frage in quiz.fragen" :key="frage.frageid">
        <frage-box :frage="frage" :antwortzeit="0"></frage-box>
      </li>
    </ul>
  </div>

</template>
  
  
<script setup lang="ts">

  import { defineProps } from 'vue'
  import { useQuiz } from '@/services/QuizService';
  import FrageBox from '@/components/FrageBox.vue';
  
  /* Das habe ich benutzt um die einzelnen Quizzes zu zeigen 
  <template>
  <div>
      <h1>Quiz View</h1>
      <p>Quiz ID: {{ quizid }}</p>
    </div>
  </template>


  const props = defineProps<{
    quizid: string
  }>()

  */
  
  const props = defineProps({
  quizId: {
    type: String,
    required: true,
  },
  });

const { quiz } = useQuiz();

</script>

  
<style scoped>
  div {
      margin: 20px;
      padding: 10px;
      border: 3px solid #ccc;
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

  
</style>