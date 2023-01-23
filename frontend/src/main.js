import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config';
import '@/assets/styles.scss';
import Chart from 'primevue/chart';
import Card from 'primevue/card';
import InputText from 'primevue/inputtext';
import Dropdown from 'primevue/dropdown';
import Button from 'primevue/button';
import ProgressSpinner from 'primevue/progressspinner';
import Calendar from 'primevue/calendar';
import StyleClass from 'primevue/styleclass';
import InputNumber from 'primevue/inputnumber';
import axios from 'axios';

const app = createApp(App)



app.use(router)
app.use(PrimeVue, { ripple: true });


app.directive('styleclass', StyleClass);

app.component('Axios' ,axios);
app.component('ProgressSpinner',ProgressSpinner);
app.component('InputNumber', InputNumber);
app.component('Button',Button);
app.component('Dropdown', Dropdown);
app.component('InputText', InputText);
app.component('Calendar', Calendar);;


app.component('Chart', Chart);
app.component('Card', Card);


app.mount('#app')
