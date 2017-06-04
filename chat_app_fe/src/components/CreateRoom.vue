<template>
	<div class="createRoom">
		<h1>Create Room</h1>
		<br />
		<p>Room Name:</p>
		<input type="text" name="room-name" placeholder="Enter room name" v-model="name">
		<br />
		<p>Room Description</p>
		<textarea name="create-room-button" v-model="description"></textarea>
		<br />
		<br />
		<input type="button" name="create-room" value="Create room" @click="createRoom">
<!-- 		<input type="button" name="logout-button" value="Logout" @click="logout">	 -->
		<br />
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
	    	axios.get( '/api/user/logout' )
	    		 .then( ( response ) => {
	    		 	if ( response.data.resolved ) {
	    		 		router.push( '/' );
	    		 	} else {
	    		 		this.data = router.data.data;
	    		 		router.push( '/' );
	    		 	}
	    		 } );
	    },

		getSessionInfo() {
	      axios.get( '/api/user/validate' )
	           .then( ( response ) => {
	              if ( response.data.resolved ) {
	                this.sessionValid = true;
	                this.userId = response.data.userId;
	                this.username = response.data.username;
	              } else {
	              	this.logout();
	              }
	           } );
	    },

	    createRoom() {
	    	axios.post( '/api/room/createRoom', {
	    		userId: this.userId,
	    		name: this.name,
	    		description: this.description
	    	} )
	    	.then( ( response ) => {
	    		if( response.data.resolved ) {
	    			this.data = response.data.data;
	    			// router.push( '/main' );
	    		} else {
	    			this.data = response.data.data;
	    		}
	    	} );
	    }

	    
	},

	beforeMount() {
		this.getSessionInfo();
	}
}
	
</script>