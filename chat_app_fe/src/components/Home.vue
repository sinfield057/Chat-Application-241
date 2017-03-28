<template>
  <div class="home">
  	
    <h1>Chat App</h1>

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

export default {
  name: 'home',

  data() {
      return {
        username: '',
        password: '',
        data: 'Aici o sa apara raspunsul de la API'
      }
  },
  
  methods: {

    doLogin() {
      const self = this;

      axios.post( '/api/user/login', {
            username: self.username,
            password: self.password
          })
           .then( ( response ) => {
            self.data = response.data.data;
           })
           .catch( ( err ) => {
            self.data = 'Error: ' + err;
           });
    },

    doRegister() {
      const self = this;

      axios.post( '/api/user/register', {
            username: self.username,
            password: self.password
          })
           .then( ( response ) => {
            self.data = response.data.data;
           })
           .catch( ( err ) => {
            self.data = 'Error: ' + err;
           });
    }

  },
}
</script>