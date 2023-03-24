<script setup>
import { onMounted, reactive, ReactiveEffect, ref, watch } from 'vue';
import { useLayout } from '@/layout/composables/layout';
import LiveChart from '@/components/LiveGraph.vue';
import LiveMachine from '../components/LiveMachineStatePower.vue';
import Axios from 'axios';
import { parseStringStyle } from '@vue/shared';
import { useToast } from 'primevue/usetoast';



const toast = useToast();
const { isDarkTheme, contextPath } = useLayout();

const products = ref(null);


let responseData = [];

let machines = [];
let loaded = false;

let inProduction =reactive([]);
let error =ref(null);

let debug = [];


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

    getMachineData();


});


async function getMachineData() {


    Axios.get('http://192.168.2.102:8080/api/live/').then((response) => { responseData = response.data; processProductionParts(); }).catch(err => { error = err; showError('Connection to Backend failed'); } );




}

function processProductionParts() {
    debug = [1, 12, 213]

    let parts = [];
    let progress = [];
    let onMachines = [];
    let onLine = [];


    // get all Parts which are in Production
    responseData.forEach(element => {

        if (element.workingOn > 0) {
            parts.push(element.workingOn);
        }





    });

    parts = [...new Set(parts)]

    // get All Machines that are working on specific part
    parts.forEach(element => {


        let m = [];
        
        responseData.forEach(machine => {

            if(machine.workingOn ==element){
                m.push(machine.id)
            }
         

        });


      onMachines.push({machines:m}); 


    });


        // get the Proudction Line for each part
        // line1 : id <16
        //line 2 : id>15




        onMachines.forEach(element =>{
                if(element.machines[0] <16){
                    onLine.push(1);

                }else {
                    onLine.push(2);
                }

        });



        // get the Current Progress for each part based on machines that parts already passed

        onMachines.forEach(element =>{
            
                let summe=0;

            element.machines.forEach(e =>{
                    summe +=e;

            });

                let schnitt = summe / element.machines.length;

                if(schnitt <16){
                    if(schnitt ==15){
                        progress.push(0.95);
                    }else{
                        progress.push(schnitt/15);
                    }
                }else {
                    schnitt -=15;

                    if(schnitt ==14){
                            progress.push(0.95)
                    }else{
                        progress.push(schnitt/14);
                    }
                

                }





        });



        for(let i =0; i<parts.length;i++){
            let newPart = {
                part:parts[i],
                machines:onMachines[i].machines,
                progress:Math.floor(progress[i]*100),
                line:onLine[i]
            }


            inProduction.push(newPart);

        }

    parts = [...new Set(parts)]
    debug = parts;
    debug = onMachines;
        inProduction.sort(compareProgress);










}


function compareProgress(a, b) {
    if (a.progress > b.progress) {
        return -1;
    }
    if (a.progress < b.progress) {
        return 1;
    }
    return 0;

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

const showError=(message) =>{

    console.log("toast message");
    toast.add({ severity: 'error', summary: 'Error', detail: message, life: 3000 });


};

</script>

<template>
    <div class="grid">
        <div class="col-6">



            <div class="card">
              
                <div class="flex justify-content-between align-items-center mb-5">
                    <h5>In Production</h5>
                    <h3>{{ error }}</h3>
                </div>

            <div v-if="inProduction==null">
                <h5>Nothing in Production</h5>

            </div>
            <div v-else>
               
                <br>
                <ul class="list-none p-0 m-0"  v-for="part in inProduction" :key="part.part">
                
                    <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                        <div> 
                            <span class="text-900 font-medium mr-2 mb-1 md:mb-0">Part number: {{ part.part }}</span>
                            <div class="mt-1 text-600">Lane: {{ part.line }}</div>
                        </div>
                        <div class="mt-2 md:mt-0 flex align-items-center">
                            <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">

                            <span v-if="part.progress <33">
                                <div class="bg-orange-500 h-full" :style="{width:part.progress +'%'}"></div>
                             

                            </span>
                                <span v-else-if="part.progress < 75">
                                    <div class="bg-cyan-500 h-full" :style="{width:part.progress +'%'}"></div>
                                </span>
                                <span v-else>
                                    <div class="bg-green-500 h-full" :style="{width:part.progress +'%'}"></div>
                                </span>

                          

                            </div>


                        
                            <span :class="{'text-orange-500 ml-3 font-medium': part.progress<33,'text-cyan-500 ml-3 font-medium':part.progress <75, 
                             'text-green-500 ml-3 font-medium':part.progress>74 }">{{ part.progress }}%</span>
                           
                        </div>
                    </li>


                </ul>




            </div>

        
            </div>


        </div>



        <div class="col-6">
            <div class="card">
                <h5> Current power per Machine</h5>
                <LiveMachine />

                {{ inProduction }}
            </div>


        </div>





    </div>










</template>

