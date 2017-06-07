<template>
  <div class="home">
  	<img src="../assets/logo2.png" id="logo">

    <h3>Chat App</h3>
    <div class = "log_reg">
      <v-text-field
              name="Username"
              label="Username"
              value="Input text0"
              class="input-group--focused   "
              v-model="username"
            > </v-text-field>
             <v-text-field
              name="Password"
              type="password"
              label="Password"
              value="Input text0"
              class="input-group--focused  "
              v-model="password"
            > </v-text-field>
      </div>   
    
        <v-btn  class="blue darken-1 white--text mt-3" name="login-button"  @click.native="doLogin">Login</v-btn>
        <v-btn  class="grey lighten-1 mt-3" name="register-button"  @click.native="doRegister">Register</v-btn>
    
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
<style scoped>

.log_reg{
  width:30%;
  display: flex;
  justify-content: center;
  flex-direction:column;
  margin: auto;
}
#logo{
  margin-top: -50px;
}

</style>
