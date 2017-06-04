<template>
	<div class="register">
		<h1>Register form tbh fam</h1>
		<p>Account Username:</p>
		<input type="text" name="username" placeholder="Username" v-model="username">
		<br />
		<p>Password</p>
    	<input type="password" name="password" placeholder="Password" v-model="password">
    	<br />
    	<p>Repeat Password</p>
    	<input type="password" name="password" placeholder="Password" v-model="passwordRepeat">
    	<br />
    	<input type="button" name="register-button" value="Register" @click="doRegister">
    	<br />
    	<p>{{ data }}</p>
	</div>
</template>

<script>
import axios from 'axios'
import router from '../router'

export default {
	name: 'register',

	data() {
		return {
	        username: '',
	        password: '',
	        passwordRepeat: '',
	        data: ''
	      }
	},

	methods: {
		doRegister() {
	      if( this.password == this.passwordRepeat ) {
	      	axios.post( '/api/user/register', {
	            username: this.username,
	            password: this.password
	          })
	           .then( ( response ) => {
	            this.data = response.data.data;
	            if( response.data.resolved ) {
	            	router.push( '/main' );
	            }
	           })
	           .catch( ( err ) => {
	            this.data = 'Error: ' + err;
	           });
	      } else {
	      	this.data = "Passwords do not match!";
	      	this.password = '';
	      	this.passwordRepeat = '';
	      }
	    },
	}
}
</script>