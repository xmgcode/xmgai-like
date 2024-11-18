// plugins/v3-waterfall.ts
import { defineNuxtPlugin } from '#app'
import V3waterfall from 'v3-waterfall'
import 'v3-waterfall/dist/style.css'

export default defineNuxtPlugin((nuxtApp) => {
  nuxtApp.vueApp.use(V3waterfall)
})

