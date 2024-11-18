import { Waterfall, LazyImg } from 'vue-waterfall-plugin-next';
import 'vue-waterfall-plugin-next/dist/style.css';

export default defineNuxtPlugin(nuxtApp => {
  console.log('888888888-----vue-waterfall-plugin-next');
  nuxtApp.vueApp.component('Waterfall', Waterfall);
  nuxtApp.vueApp.component('LazyImg', LazyImg);
});
