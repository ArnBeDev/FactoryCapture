<template>
    <h1> Graph</h1>
    <canvas id="piestate"></canvas>
</template>

<script>
import { Chart } from 'chart.js';

let chartData = null;
let chart = null;

export default {
    name: "PieGraphState",
    data() {
        return{
            responseData :[],
            error: ''
        }
     
    },

    created() {


    },
    mounted() {
       
        this.getData();
    },


    methods: {

        async getData() {
            Axios.getData('http://192.168.2.102:8080/api/live/').then((response) => { this.responseData = response.data; this.processData(); }).catch((error) => this.error = error)

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
                        label: 'Idle, wait for next part',
                        data: countIdleE,
                        backGroundColor: '#ffea00'
                    },
                    {
                        label: 'Idle, wait for next machine',
                        data: countIdleO,
                        backGroundColor: '#f0c419'
                    },
                    {
                        label: 'Working',
                        data: countW,
                        backGroundColor: '#ffea00'
                    },
                    {
                        label: 'Normal Error',
                        data: countE1,
                        backGroundColor: '#ff2d00'
                    },
                    {
                        label: 'Fatal Error',
                        data: countE2,
                        backGroundColor: '#5c0002'
                    }
                ]
            };

            chartData = {

                type: 'pie',
                data: data,
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

            }

            let ctx = document.getElementById('piestate');
            chart = new Chart(ctx, chartData);



        }

    }



}


</script>