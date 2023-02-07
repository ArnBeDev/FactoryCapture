<template>

    <div class="flex justify-content-between align-items-center mb-5">
        <h5>In Production</h5>

    </div>

    <div v-if="inProduction == null">
        <h5>Nothing in Production</h5>
    </div>
    <div v-else>

        <br>
        <ul class="list-none p-0 m-0" v-for="part in inProduction" :key="part.part">

            <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                <div>
                    <span class="text-900 font-medium mr-2 mb-1 md:mb-0">Part number: {{ part.part }}</span>
                    <div class="mt-1 text-600">Lane: {{ part.line }}</div>
                </div>
                <div class="mt-2 md:mt-0 flex align-items-center">
                    <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">

                        <span v-if="part.progress < 33">
                            <div class="bg-orange-500 h-full" :style="{ width: part.progress + '%' }"></div>


                        </span>
                        <span v-else-if="part.progress < 75">
                            <div class="bg-cyan-500 h-full" :style="{ width: part.progress + '%' }"></div>
                        </span>
                        <span v-else>
                            <div class="bg-green-500 h-full" :style="{ width: part.progress + '%' }"></div>
                        </span>



                    </div>



                    <span :class="{
                        'text-orange-500 ml-3 font-medium': part.progress < 33, 'text-cyan-500 ml-3 font-medium': part.progress < 75,
                        'text-green-500 ml-3 font-medium': part.progress > 74
                    }">{{ part.progress }}%</span>

                </div>
            </li>


        </ul>




    </div>





</template>

<script setup>
import { onMounted, reactive } from '@vue/runtime-core';
import Axios from 'axios';

let responseData = [];

let machines = [];
let loaded = false;

let inProduction =reactive([]);
let error;

let debug = [];


onMounted(() => {

    getMachineData();


});
async function getMachineData() {


    Axios.get('http://192.168.2.102:8080/api/live/').then((response) => { responseData = response.data; processProductionParts(); }).catch(err => { error = err });




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

            if (machine.workingOn == element) {
                m.push(machine.id)
            }


        });


        onMachines.push({ machines: m });


    });


    // get the Proudction Line for each part
    // line1 : id <16
    //line 2 : id>15




    onMachines.forEach(element => {
        if (element.machines[0] < 16) {
            onLine.push(1);

        } else {
            onLine.push(2);
        }

    });



    // get the Current Progress for each part based on machines that parts already passed

    onMachines.forEach(element => {

        let summe = 0;

        element.machines.forEach(e => {
            summe += e;

        });

        let schnitt = summe / element.machines.length;

        if (schnitt < 16) {
            if (schnitt == 15) {
                progress.push(0.95);
            } else {
                progress.push(schnitt / 15);
            }
        } else {
            schnitt -= 15;

            if (schnitt == 14) {
                progress.push(0.95)
            } else {
                progress.push(schnitt / 14);
            }


        }





    });



    for (let i = 0; i < parts.length; i++) {
        let newPart = {
            part: parts[i],
            machines: onMachines[i].machines,
            progress: Math.floor(progress[i] * 100),
            line: onLine[i]
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







</script>