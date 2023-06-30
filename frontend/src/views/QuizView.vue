<template>
    
    <div>
    <h2>Quiz ID: {{ readonlyQuiz.id }}</h2>
    <h3>{{ readonlyQuiz.titel }}</h3>
    <p>Punktesumme: {{ readonlyQuiz.punktesumme }}</p>

    <ul>
      <li v-for="frage in readonlyQuiz.fragen" :key="frage.frageid">
        <frage-box :class="antwortStatusMap.map.get(frage.frageid)" :frage="frage" :antwortzeit="0" @fragebeantwortet="handleFrageBeantwortet" @zeitvorbei="handleZeitVorbei" ></frage-box>
      </li>
    </ul>
  </div>

</template>
  
  
<script setup lang="ts">

  import { defineProps,onMounted,ref } from 'vue'
  import { updateQuiz,readonlyQuiz,checkQuiz, antwortStatusMap} from '@/services/QuizService';
  import FrageBox from '@/components/FrageBox.vue';
  import { useInfo } from '@/services/InfoService';
  const { setInfo } = useInfo();

  const beantworteteFragen = ref(new Map<number, string>());

  function handleFrageBeantwortet(frageid: number, antwort: string) {
    beantworteteFragen.value.set(frageid, antwort);
  }

  function handleZeitVorbei(frageid: number) {
    
    if (beantworteteFragen.value.size === readonlyQuiz.value.fragen.length) {
      checkQuiz(readonlyQuiz.value.id, beantworteteFragen.value);
   }

  }



  // In deiner router-Konfiguration hat man props: true für die Route /quiz/:quizid festgelegt, 
  // was bedeutet, dass die Route - Parameter als Props an die QuizView.vue - Komponente übergeben werden.
  // Um die Quiz - ID in der Komponente zu erhalten, kannst man props.quizid verwenden
  const props = defineProps({
    quizid: {
      type: String,
      required: true
    }
  });



  //Insgesamt ermöglicht onMounted, dass der Code innerhalb der Funktion automatisch ausgeführt wird,
  //sobald die Komponente bereit ist.Es ist eine praktische Möglichkeit,
  //asynchrone Aufrufe oder Initialisierungslogik in Vue - Komponenten zu handhaben.
  //hier: um das Quiz zu aktualisieren
  onMounted(async () => {
    try {
      const quizId = parseInt(props.quizid);
      await updateQuiz(quizId);
    } catch (error: any) {
      setInfo(error.message);
    }
    });

</script>

  
<style scoped>

.richtig {
  border: 2px solid;
  border-color: green;
}

.falsch {
  border: 2px solid;
  border-color: red;
}


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

    
    .richtig-beantwortet {
      border: 2px solid green;
    }

    .falsch-beantwortet {
      border: 2px solid red;
    }


</style>