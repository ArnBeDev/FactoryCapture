import { createRouter, createWebHistory } from 'vue-router'

import AppLayout from '@/layout/AppLayout.vue';


const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',

      component: AppLayout,
      children:[
        {
        path:'/',
        name:'dashboard',
        component: () => import('@/views/StartDashboard.vue')
      },{
        path:'/live',
        name:'live',
        component: () => import('@/views/LiveView.vue')
      },
      {
        path:'/machines',
        name:'live',
        component: () => import('@/views/MachinesView.vue')
      },
      {
        path:'/factory',
        name:'factory',
        component: () => import('@/views/FactoryView.vue')
      },
      {
        path:'/produce',
        name:'produce',
        component: () => import('@/views/ProduceView.vue')
      },
      {
        path:'/errors',
        name:'errors',
        component: () => import('../views/AboutView.vue')
      }
      ]
    },
   
  ]
})

export default router
