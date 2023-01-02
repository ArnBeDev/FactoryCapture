import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config';
import '@/assets/styles.scss';
import Chart from 'primevue/chart';
const app = createApp(App)

app.use(router)
app.use(PrimeVue, { ripple: true });
app.component('Chart', Chart);

app.mount('#app')
