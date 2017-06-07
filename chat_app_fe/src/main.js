// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuetify from 'vuetify';
import VueSocketio from 'vue-socket.io'
import Vuex from 'vuex'
import { store } from './store';


Vue.use( VueSocketio, 'http://localhost:8000');

Vue.use(Vuetify);
/* eslint-disable no-new */



new Vue({
  el: '#app',
  store: store,
  router,
  template: '<App/>',
  components: { App },
  sockets: {
  	connect: function() {
  		console.log( "connected" );
  	},
  	disconnect: function() {
  		console.log( "disconnected" );
  	},
  	customEmit: function( val ) {
  		console.log( val );
  	},
    emitMessage: function( payload ) {

    }
  },
})
