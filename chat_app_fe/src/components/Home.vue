<template>
  <div class="home" @keyup.enter="doLogin">
  	<div class="bg"></div>
    <v-card class = "login-card white pb-5 elevation-20">
      <div class = "title pa-4" >
        <span class="black--text display-3">Chat App</span>
      </div>
      <div class = "log_reg mt-3">
        <v-text-field
                name="Username"
                label="Username"
                value="Input text0"
                class="input-group--focused mb-0 "
                v-model="username"
              > </v-text-field>
               <v-text-field
                name="Password"
                type="password"
                label="Password"
                value="Input text0"
                class="input-group--focused mb-0 "
                v-model="password"
              > </v-text-field>
      </div>
      <v-btn  class="blue darken-1 white--text mt-1" name="login-button"  @click.native="doLogin">Login</v-btn>
      <v-btn  class="grey lighten-1 mt-1" name="register-button"  @click.native="doRegister">Register</v-btn>
      <v-alert error v-bind:value="true" id="alert" v-show="data.length!==0">
  			{{data}}
  		</v-alert>
    </v-card>
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
      document.getElementById('alert').style.visibility = 'visible';
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
<style scoped>
  .login-card{
    max-width:50%;
    margin:0 auto;
    z-index: 999;
    position: relative;
    top:10vh;
  }
  .title{
    display: flex;
    justify-content: center;
  }
  .home{
    margin-top:-10px;
  }
  .bg{
    z-index: 1;
    position: fixed;
    left: 0;
    right: 0;
    background-image:url('../assets/logo2.png');
    background-position-x: center;
    background-position-y: 25%;
    width: 100%;
    height: 100%;
  }
  .log_reg{
    width:50%;
    display: flex;
    justify-content: center;
    flex-direction:column;
    margin: auto;
  }
  #alert{
  		visibility: hidden;
      width:70%;
  }
</style>