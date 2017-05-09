import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Main from '@/components/Main'
import Register from '@/components/Register'
import CreateRoom from '@/components/CreateRoom'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
    	path: '/main',
    	name: 'Main',
    	component: Main
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/createRoom',
      name: 'CreateRoom',
      component: CreateRoom
    }
  ]
})
