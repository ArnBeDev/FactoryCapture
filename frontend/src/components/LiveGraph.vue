<template>
  <div  style=" width: 200 vh; height: 75vh" >
  
 
     <canvas id="LiveDataChart"></canvas>

 
  
</div>
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
       }, 6000);


  },
  processData(){

    console.log("hallo");
    this.devices.sort(this.compare);
    this.verbrauch =[];
   this.ids=[];

    this.devices.forEach(device => {
      this.ids.push(device.id);
      this.verbrauch.push(device.power);
    });


    barChartData.data.labels = this.ids;
    barChartData.data.datasets[0].data= this.verbrauch;

    myChartData.data.datasets[0].data= this.verbrauch;
    myChartData.labels= this.ids;
   


   
    
   
    myChartData.update("none");
  
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