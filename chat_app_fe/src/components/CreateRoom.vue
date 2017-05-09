<template>
	<div class="createRoom">
		<h1>Create Room</h1>
		<input type="text" name="room-name" placeholder="Enter room name" v-model="name">
		<textarea name="create-room-button" v-model="description"></textarea>
		<input type="button" name="create-room" value="Create room" @click="createRoom">
		<input type="button" name="logout-button" value="Logout" @click="logout">	
		<router-link :to=" 'main' ">Back to main</router-link>
	</div>
</template>

<script>
import axios from 'axios'
import router from '../router'
import session from 'vue-cookie'

export default {
	name: 'createRoom',

	data() {
		return {
			username: '',
			sessionValid: false,
			userId: '',
			data: '',
			name: '',
			description: ''
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

	    createRoom() {
	    	const self = this;

	    	axios.post( '/api/room/createRoom', {
	    		userId: self.userId,
	    		name: self.name,
	    		description: self.description
	    	} )
	    	.then( ( response ) => {
	    		if( response.data.resolved ) {
	    			self.data = response.data.data;
	    		} else {
	    			self.data = response.data.data;
	    		}
	    	} );
	    }

	    
	},

	beforeMount() {
		this.getSessionInfo();
	}
}
	
</script>