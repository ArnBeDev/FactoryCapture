<template>



    <div>
     

        <div class="grid">
            <div class="col-6">
            
                <div class="card">
                    <InProductionList/>
                </div>
              
            </div>
            <div class="col-6">
  <div class="card">
    <PieGraphState/>
  </div>
               

            </div>
        </div>
      
    </div>
 
</template>
<script setup>
import PieGraphState from '../components/PieGraphState.vue';
import InProductionList from '../components/InProductionList.vue';
</script>
