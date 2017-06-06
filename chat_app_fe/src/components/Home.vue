<template>
  <div class="home">
  	<img src="../assets/logo2.png" id="logo">

    <h3>Chat App</h3>

    <input type="text" name="username" placeholder="Username" v-model="username">
    <input type="password" name="password" placeholder="Password" v-model="password">
    <br />
    <br />
    <input type="button" name="login-button" value="Login" @click="doLogin">
    <input type="button" name="register-button" value="Register" @click="doRegister">
    <p>{{ data }}</p>
  </div>
</template>


<script>
import axios from 'axios'
import router from '../router'
import session from 'vue-cookie'

export default {
  name: 'home',

  data() {
      return {
        username: '',
        password: '',
        sessionValid: false,
        data: '',
        moderatedRooms: []
      }
  },
  
  methods: {

    doLogin() {
      const self = this;
      axios.post( '/api/user/login', {
            username: this.username,
            password: this.password
          })
           .then( ( response ) => {
            this.data = response.data.data;
            if ( response.data.resolved == true ) {
              self.$socket.emit( 'connect', this.username );
              
              self.$options.sockets.welcome = ( data ) => {
                console.log( data );
              }
              
              router.push('/main');   
            }
           })
           .catch( ( err ) => {
            this.data = 'Error: ' + err;
           });
    },

    doRegister() {
      router.push('/register');
    },

    checkSession() {
      axios.get( '/api/user/validate' )
           .then( ( response ) => {
              if ( response.data.resolved == true ) {
                router.push( '/main' );
              }
           } );
    },

  },
  beforeMount() {
    this.checkSession();
  }
}
</script>