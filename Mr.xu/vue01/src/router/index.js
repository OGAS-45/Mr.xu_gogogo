import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import prj from '@/components/prj'
import home from '@/components/home'
import resource from "../components/resource";
import login from "../components/login"
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/user',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    /*{
      path: '/',
      name: 'home',
      component: home,
      //redirect: '/prj',
      children: [
        { path: '/prj', name: 'prj', component: prj },
        { path: '/resour', name: 'resour', component: resource }
      ]
    }*/
    {
      path: '/prj',
      name: 'prj',
      component: prj
    },
    {
      path: '/resour',
      name: 'resour',
      component: resource
    }
  ]
})
