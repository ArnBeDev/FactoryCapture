<template>
    <h1> Graph</h1>
     <p>{{ responseData }}</p>
        <canvas id="piestate"></canvas>
    
</template>

<script>
import { Chart } from 'chart.js/auto';
import axios from 'axios';
import { ref } from 'vue';
let chartData = {

    type: 'pie',
    data: {
        labels: ['wait for data'],
        datasets: [{
            data: [1],
            backGroundColor: ['#5c0002']
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
            chart :null
        }

    },

    created() {


    },
    mounted() {
        let ctx = document.getElementById('piestate');
        this.chart = new Chart(ctx, chartData);
        this.getData();
    },


    methods: {

        async getData() {
            axios.get('http://192.168.2.102:8080/api/live/').then((response) => { this.responseData = response.data; this.processData(); }).catch((error) => this.error = error)

        },

        processData() {
            let countIdleEmpty = 0;
            let countIdleOccupied = 0;
            let countWorking = 0;
            let countError1 = 0;
            let countError2 = 0;

            this.responseData.array.forEach(element => {
                if (element.stateCode == 0) {
                    //Idle
                    countIdleEmpty++;
                } else if (element.stateCode == 1) {
                    countIdleOccupied++;
                }
                else if (element.stateCode == 2) {
                    countWorking++;
                } else if (element.stateCode == 3) {
                    // normal error
                    countError1++;
                } else if (element.stateCode == 4) {
                    //fatal error
                    countError2++;
                }
            });
            this.createChartData(countIdleEmpty, countIdleOccupied, countWorking, countError1, countError2)

        },

        createChartData(countIdleE, countIdleO, countW, countE1, countE2) {
            let data = {
                labels: ['Waiting for next Production Part', 'Work Done, waiting for next Machine to be Ready', 'Working', 'Error', 'Fatal Error'],
                datasets: [
                    {
                     
                        data: [countIdleE,countIdleO,countW,countE1,countE2],
                        backGroundColor: ['#ffea00','#f0c419','#ffea00','#ff2d00','#5c0002']
                    }
                 
                ]
            };

            this.chart.data =data;

            this.chart.update();
           


        }

    }



}


</script>