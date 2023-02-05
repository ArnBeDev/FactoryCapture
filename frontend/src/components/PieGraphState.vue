<template>
    <h1> Graph</h1>
     <p>{{ amountWorking }}</p>
        <canvas id="piestate"></canvas>
    
</template>

<script>
import { Chart } from 'chart.js/auto';
import axios from 'axios';

let chart =null;

let chartData = {

    type: 'pie',
    methods:{},
    data: {
        labels: ['wait for data', 'other','idk'],
        datasets: [{
            data: [1,2],
            backgroundColor :['#2e7d32','#00305a','#f2b705','#d23600','#d23600'],
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Current Machinestates'
            }
        }
    },

};



export default {
    name: "PieGraphState",
    data() {
        return {
            responseData: [],
            error: '',
            amountWorking:0,
            amountIdleEmpty:0,
            amountIdleWaiting:0,
            amountError:0,
            amountErrorFatal:0,
            
        };

    },

    created() {
        

    },
    mounted() {
        let ctx = document.getElementById('piestate');
        chart = new Chart(ctx, chartData);
        chart.update();
        this.getData();
    },


    methods: {

        async getData() {
            axios.get('http://192.168.2.102:8080/api/live/').then((response) => { this.responseData = response.data; this.processData(); }).catch((error) => this.error = error)

        },

        processData() {
            this.amountWorking =1223;
            this.amountWorking=0;
            this.amountIdleEmpty=0;
            this.amountIdleWaiting=0;
           this.amountError=0;
            this.amountErrorFatal=0;
            

            this.responseData.forEach(element => {
                if (element.stateCode == 0) {
                    //Idle
                    this.amountIdleEmpty++;
                } else if (element.stateCode == 1) {
                    this.amountIdleWaiting++;
                }
                else if (element.stateCode == 2) {
                    this.amountWorking++;
                } else if (element.stateCode == 3) {
                    // normal error
                    this.amountError++;
                } else if (element.stateCode == 4) {
                    //fatal error
                    this.amountErrorFatal++;
                }
            });
            chartData.data.labels = ['Working', 'Idle Empty','Idle Wait','Error' ,  'Fatal Error']
           chartData.data.datasets[0].data = [this.amountWorking,this.amountIdleEmpty,this.amountIdleWaiting,this.amountError,this.amountErrorFatal];

         //  chartData.data.datasets[0].backGroundColor =['#2e7d32','#00305a','#f2b705','#d23600','#d23600'];

           chart.update();
           

        },

        createChartData() {
        
            chartData.data.labels = ['Working', 'Idle Empty','Idle Wait','Error' ,  'Fatal Error']
           chartData.data.datasets[0].data = [this.amountWorking,this.amountIdleEmpty,this.amountIdleWaiting,this.amountError,this.amountErrorFatal];
           chartData.data.datasets[0].backgroundColor =['#2e7d32','#00305a','#f2b705','#d23600','#d23600'];

           chart.update();
           


        }

    }



}


</script>