<template>
	<div class="main">
		<h1>Welcome {{ username }}!</h1>
		<p>Your userId is: {{ userId }}</p>
		<p>{{ data }}</p>
		<input type="button" name="logout-button" value="Logout" @click="logout">	
	</div>
</template>

<script>
import axios from 'axios'
import router from '../router'
import session from 'vue-cookie'

export default {
	name: 'main',

	data() {
		return {
			username: '',
			sessionValid: false,
			userId: '',
			data: ''
		}
	},

	methods: {
		logout() {
	    	const self = this;

	    	axios.get( '/api/user/logout' )
	    		 .then( ( response ) => {
	    		 	if ( response.data.resolved ) {
	    		 		router.push( '/' );
	    		 	} else {
	    		 		self.data = router.data.data;
	    		 		router.push( '/' );
	    		 	}
	    		 } );
	    },

		getSessionInfo() {
	      const self = this;

	      axios.get( '/api/user/validate' )
	           .then( ( response ) => {
	              if ( response.data.resolved ) {
	                self.sessionValid = true;
	                self.userId = response.data.userId;
	                self.username = response.data.username;
	              } else {
	              	self.logout();
	              }
	           } );
	    },

	    
	},

	beforeMount() {
		this.getSessionInfo();
	}
}
	
</script>