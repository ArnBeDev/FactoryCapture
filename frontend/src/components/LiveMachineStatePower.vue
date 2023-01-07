<template>
  <div style=" width: 200 vh; height: 75vh">


<div id="123">

</div>



  </div>
</template>

<script>
import Chart from "chart.js/auto";
import axios from 'axios';


let chart = null;



let barChartData = {
  type: "bar",
  methods: {},
  data: {
    labels: [1, 2, 3],
    datasets: [
      {
        label: "Working",
        data: [12, null, null],
        fill: true,
        showLine: true,
        borderWidth: 1,
        backgroundColor: '#00bb7e',
        borderColor: '#00bb7e',
      },
      {
        label: "Idle",
        data: [null, null, 20],
        fill: true,
        showLine: true,
        borderWidth: 1,
        backgroundColor: '#2f4860',
        borderColor: '#2f4860',
      },
      {
        label: "Error",
        data: [null, 12, null],
        fill: true,
        showLine: true,
        borderWidth: 1,
        backgroundColor: '#900b0a',
        borderColor: '#5c0002',
      }


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
  name: "LiveMachineStatePowerGraph",

  data() {
    return {
      loaded: false,
      machines: [],
      ids: [],
      err: '',

      idlePower: [],
      errorPower: [],
      workingPower: []
    };

  },
  mounted() {
    let ctx = document.getElementById("123");
    chart = new Chart(ctx, barChartData);


    myChartData.update("none");
    this.receiveData();



  },

  methods: {

    async receiveData() {
      setInterval(() => {
        axios.get('http://192.168.2.102:8080/api/live/').then((response) => { this.machines = response.data; this.createChartData(); }).catch(err => { this.err = err })
      }, 6000);


    },
    createChartData() {


      this.ids = [];
      this.idlePower = [];
      this.errorPower = [];
      this.workingPower = [];

      this.machines.sort(compare);

      this.machines.forEach(machine => {
        this.ids.push(machine.id);

        if (machine.stateCode == 2) {
          //working
          this.workingPower.push(machine.power);
          this.errorPower.push(0);
          this.idlePower.push(0);


        } else if (machine.stateCode > 2) {
          //idle
          this.workingPower.push(0);
          this.errorPower.push(machine.power);
          this.idlePower.push(0);


        } else {
          //error
          this.workingPower.push(0);
          this.errorPower.push(0);
          this.idlePower.push(machine.power);

        }


      });



      barChartData.data.labels = this.ids;
      barChartData.data.datasets[0].data = this.workingPower;
      barChartData.data.datasets[1].data = this.idlePower;
      barChartData.data.datasets[2].data = this.errorPower;



      chart.update("none");


    },

    compare(a, b) {
      if (a.id < b.id) {
        return -1;
      }
      if (a.id > b.id) {
        return 1;
      }
      return 0;

    }


  }
};
</script>