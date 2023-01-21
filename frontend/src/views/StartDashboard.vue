<script setup>
import { onMounted, reactive, ReactiveEffect, ref, watch } from 'vue';
import { useLayout } from '@/layout/composables/layout';
import LiveChart from '@/components/LiveGraph.vue';
import LiveMachine from '../components/LiveMachineStatePower.vue';
import Axios from 'axios';
const { isDarkTheme, contextPath } = useLayout();

const products = ref(null);

let machines = [];
let loaded = false;

let err;




let liveData;

const lineData = reactive({
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
        {
            label: 'First Dataset',
            data: [65, 59, 80, 81, 56, 55, 40],
            fill: false,
            backgroundColor: '#2f4860',
            borderColor: '#2f4860',
            tension: 0.4
        },
        {
            label: 'Second Dataset',
            data: [28, 48, 40, 19, 86, 27, 90],
            fill: false,
            backgroundColor: '#00bb7e',
            borderColor: '#00bb7e',
            tension: 0.4
        }
    ]
});
const items = ref([
    { label: 'Add New', icon: 'pi pi-fw pi-plus' },
    { label: 'Remove', icon: 'pi pi-fw pi-minus' }
]);
const lineOptions = ref(null);


onMounted(() => {


    getData();
});


async function getData() {


    Axios.get('http://192.168.2.102:8080/api/live/').then((response) => { this.machines = response.data; createChartData(); }).catch(err => { this.err = err });




}

function createChartData() {

    this.loaded = true;
    let ids = [];
    let idlePower = [];
    let errorPower = [];
    let workingPower = [];

    this.machines.sort(this.compare);

    this.machines.forEach(machine => {
        ids.push(machine.id);

        if (machine.stateCode == 2) {
            //working
            workingPower.push(machine.power)
            errorPower.push(null);
            idlePower.push(null);


        } else if (machine.stateCode < 2) {
            //idle
            workingPower.push(null)
            errorPower.push(machine.power);
            idlePower.push(null);


        } else {
            //error
            workingPower.push(null)
            errorPower.push(null);
            idlePower.push(machine.power);

        }


    })


    this.liveData = {
        data: {


            labels: ids,
            datasets: [


                { //working
                    label: 'working machines',
                    data: workingPower,
                    fill: true,
                    backgroundColor: '#00bb7e',
                    borderColor: '#00bb7e',

                }, {
                    //idle
                    label: 'idle machines',
                    data: idlePower,
                    fill: true,
                    backgroundColor: '#2f4860',
                    borderColor: '#2f4860',
                }, {
                    //error
                    label: 'error machines',
                    data: errorPower,
                    fill: true,
                    backgroundColor: '#900b0a',
                    borderColor: '#5c0002'
                }]

        }


    }





    this.loaded = true;

}


function compare(a, b) {
    if (a.id < b.id) {
        return -1;
    }
    if (a.id > b.id) {
        return 1;
    }
    return 0;

}



const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};
const applyLightTheme = () => {
    lineOptions.value = {
        plugins: {
            legend: {
                labels: {
                    color: '#495057'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: '#495057'
                },
                grid: {
                    color: '#ebedef'
                }
            },
            y: {
                ticks: {
                    color: '#495057'
                },
                grid: {
                    color: '#ebedef'
                }
            }
        }
    };
};

const applyDarkTheme = () => {
    lineOptions.value = {
        plugins: {
            legend: {
                labels: {
                    color: '#ebedef'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: '#ebedef'
                },
                grid: {
                    color: 'rgba(160, 167, 181, .3)'
                }
            },
            y: {
                ticks: {
                    color: '#ebedef'
                },
                grid: {
                    color: 'rgba(160, 167, 181, .3)'
                }
            }
        }
    };
};

watch(
    isDarkTheme,
    (val) => {
        if (val) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    },
    { immediate: true }
);
</script>

<template>
    <div class="grid">
        <div class="col-6">



            <div class="card">
                <h5>Sales Overview {{ loaded }}</h5>
                <Chart type="line" :data="lineData" :options="lineOptions" />




            </div>

        </div>



<div class="col-6">
    <div class="card">
                <h5> Current power per Machine</h5>
                <LiveMachine />
            </div>


</div>
           




        </div>










</template>

