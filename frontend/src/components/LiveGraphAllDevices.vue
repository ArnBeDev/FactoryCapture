<template>
  
    
   
       <canvas id="LiveDataChart"></canvas>
  
   <p>{{ devices }}</p>
    

    </template>
    
    <script>
    import Chart from "chart.js/auto";
    import axios from 'axios';
  
  let myChartData=null;
  
  
  
  
    let barChartData = {
      type: "bar",
      methods: {},
      data: {
        labels: [1,2,3],
        datasets: [
          {
            label: "Aktueller Verbrauch",
            data: [12,15,20],
            fill: true,
            showLine: true,
            borderWidth: 1,
            backgroundColor: "rgb(20 , 20, 176, 0.9)",
            borderColor: "rgb(54, 172, 176)",
          },
  
         
        ],
      },
      options: {
        plugins: {
          legend: {
            labels: {
              font: {
                size: 16,
              },
            },
          },
        },
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    };
  
    
    
    export default {
      name: "LiveChart",

      data() {
        return {
          devices:[],
          ids:[],
          err:'',
          verbrauch:[],
          testdata:[],
        };
       
      },
      mounted() {
        let ctx = document.getElementById("LiveDataChart");
        myChartData= new Chart(ctx, barChartData);
  
  
        myChartData.update("none");
        this.receiveData();
        
  
  
      },
  
      methods:{
  
       async receiveData(){
         setInterval(() => {
          axios.get('http://192.168.2.102:8080/api/live/').then((response) => {this.devices=response.data;this.processData();}).catch(err => {this.err=err})
         }, 3000);
  
  
    },
    processData(){
      this.devices.sort(this.compare);
      this.verbrauch =[];
     this.ids=[];

  
  
      this.devices.forEach(element => {
        this.ids.push(element.id);
        this.verbrauch.push(element.power);
      });
  
  
      barChartData.data.labels = this.ids;
      barChartData.data.datasets[0].data= this.verbrauch;

    





      myChartData.update();
    
    },
  
    compare(a,b){
      if(a.id < b.id){
        return -1;
      }
      if(a.id >b.id){
        return 1;
      }
      return 0;
  
    }
  
  
      }
    };
    </script>