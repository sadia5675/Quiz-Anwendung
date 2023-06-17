import { createRouter, createWebHistory } from 'vue-router'
import QuizListeView from '@/views/QuizListeView.vue'
import QuizView from '@/views/QuizView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: QuizListeView
    },

    {
      path: '/quiz/:quizid',
      name: 'quiz',
      component: QuizView,
      props: true
    },

    {
      path: '/about',
      name: 'about',
      component: {}
    }
  ]
})

export default router
