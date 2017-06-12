<template>
	<div class="register">
		<div class="bg"></div>
		<div class ="data_con">
			<v-card class="register-card white py-4 elevation-20">
				<div class = "title" >
	       			<span class="black--text display-3">Register Form</span>
	      		</div>
				<div class = "log_reg mt-2">
					<v-text-field
						name="Username"
						type="text"
						label ="Username"
						class="input-group "
						v-model="username"
					></v-text-field>
					<v-text-field
						name="Password"
						label ="Password"
						type="password"
						class="input-group "
						v-model="password"
					></v-text-field>
					<v-text-field
						name="RepeatPassword"
						type="password"
						label ="Repeat Password"
						class="input-group "
						v-model="passwordRepeat"
					></v-text-field>
				</div>
				<v-btn  class="blue darken-1 white--text mt-1" name="registe-button" value ="Register" @click.native="doRegister">Register</v-btn>
				<v-alert error v-bind:value="true" id="alert" v-show="data.length!==0">
					{{data}}
				</v-alert>
			</v-card>
		</div>
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
		doLogin() {
      axios.post( '/api/user/login', {
            username: this.username,
            password: this.password
          })
           .then( ( response ) => {
            this.data = response.data.data;
            if ( response.data.resolved == true ) {
              router.push('/main');
            }
           })
           .catch( ( err ) => {
            this.data = 'Error: ' + err;
           });
    },

		doRegister() {
			document.getElementById('alert').style.visibility = 'visible';
			if ( this.password == this.passwordRepeat ) {
				axios.post( '/api/user/register', {
					username: this.username,
					password: this.password
				})
				.then( ( response ) => {
					this.data = response.data.data;

					if ( response.data.resolved ) {
						this.doLogin();
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
<style scoped>
    .register{
		margin-top:-10px;
	}
	#alert{
		visibility: hidden;
	}
	.log_reg{
	    width:50%;
	    display: flex;
	    justify-content: center;
	    flex-direction:column;
	    margin: auto;
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
 	.register-card{
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
  	#alert{
  		visibility: hidden;
      	width:70%;
  	}
</style>