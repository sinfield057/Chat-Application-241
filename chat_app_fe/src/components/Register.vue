<template>
	<div class="register">
		
		<div class ="data_con">
			<v-card class="blue-grey lighten-4 elevation-0 pt-5 pb-5">
			<h3>Register form </h3>	
				<v-card-text class = "data_register">
				<v-container fluid>
					<v-layout row>
					<v-flex xs8>
						<v-text-field
						name="Username"
							type="text"				
							label ="Username"
							class="input-group "
							v-model="username"
												
						></v-text-field>
					</v-flex>
					</v-layout>
					<v-layout row>
					<v-flex xs8>
						<v-text-field
							name="Password"
							label ="Password"
							type="password"			
							class="input-group "
							v-model="password"	
							
						></v-text-field>
					</v-flex>
					</v-layout>
					<v-layout row>
					<v-flex xs8>
						<v-text-field
							name="RepeatPassword"
							type="password"				
							label ="RepeatPassword"
							class="input-group "
							v-model="passwordRepeat"
													
						></v-text-field>
					</v-flex>
					</v-layout>        
				</v-container>
				</v-card-text>
				<v-btn  class="blue darken-1 white--text mt-3" name="registe-button" value ="Register" @click.native="doRegister">Register</v-btn>
			</v-card>			
		</div>
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
	.data_con{
		width: 50%;  
		height: 70%;
		justify-content: center;
		margin: auto;
	
	}
    .data-register{
		justify-content: center;
		margin: auto;
	}
</style>