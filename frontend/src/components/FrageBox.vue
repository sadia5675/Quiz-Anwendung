<template>
   <div class="frage">
        <div class="frage-Zeile">
            
            <p> {{ frage.fragetext }}</p>
            <p> [{{ frage.punkte }} Punkte] <button @click="antworten()"> {{ counter }} Sekunden</button> </p>
            
        </div>
    
        <div v-if="isAnswering">
            
            <div v-for="antwort, index in frage.alleantworten">
            <label :for="'antwort-' + index"> {{ antwort }} </label>
            <input :id="'antwort-' + index" type="radio" :value="index" v-model="ausgewaehlteAntwort" :disabled="antwortDisabled" @change="emitFrageBeantwortet">
            </div>
        </div>
    </div>   
</template>
  
<script setup lang="ts">
    
import { defineProps, ref, watch, defineEmits } from 'vue';


    const props = defineProps({
    frage: {
        type: Object,
        required: true,
    },
    antwortzeit: {
        type: Number,
        default: 8
    },
    });

    
    const counter = ref();
    const antwortDisabled = ref(false); // Wenn answerDisabled auf true gesetzt ist, wird der Radiobutton deaktiviert und kann nicht ausgewählt werden.
    const ausgewaehlteAntwort = ref(null); // Hier wird die ausgewählte Antwort der Frage gespeichert. Wenn der User eine Antwort auswählt, wird der Wert des entsprechenden Radio-Buttons an selectedAnswer gebunden.
    const isAnswering = ref(false);

    let timerId: number;

    function antworten() {
        if (!isAnswering.value) {
        isAnswering.value = true;
        setAnswerTime();
        startTimer();
        }
    }
    
    // Startet den Timer für die Antwortzeit
    const startTimer = () => {
    timerId = setInterval(() => {
        counter.value--;
        if (counter.value === 0) {
        clearInterval(timerId);
        antwortDisabled.value = true;
        emitZeitVorbei();
        }
    }, 1000);
    };


    // (Falls keine Antwortzeit oder Antwortzeit=0 übergeben) Setzt die Antwortzeit = Anzahl der Antwortmöglichkeiten * 2
    const setAnswerTime = () => {
    if (props.antwortzeit === 0) {
        const antwortenCount = props.frage.alleantworten.length;
        counter.value = antwortenCount * 2;
    } else {
        counter.value = props.antwortzeit;
    }
    };

    
    const emit = defineEmits<{
        fragebeantwortet: [ frageid: number,antwort: string ];
        zeitvorbei: [ frageid: number];
        }>();
    

    function emitFrageBeantwortet() {
        const antwort = ausgewaehlteAntwort.value !== null ? props.frage.alleantworten[ausgewaehlteAntwort.value] : '';
        const payload = { frageid: props.frage.frageid, antwort };
        emit('fragebeantwortet', props.frage.frageid, antwort);
    }

    function emitZeitVorbei() {
        emit('zeitvorbei', props.frage.frageid);
    }

</script>




<style scoped>
.frage{
    background-color: rgb(248, 247, 247);
    border-radius: 10px;
    border-color: none;
}


.frage-Zeile {
  display: flex;
  justify-content: space-between;
  
}

button {
  color: green;
  background-color: white;
  border: 1px solid green;
  padding: 6px 12px;
  border-radius: 4px;
  margin-left: 20px;
}

button:hover {
  background-color: green;
  color: white;
}

</style>