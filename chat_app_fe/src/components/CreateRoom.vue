<template>
	<div class="createRoom">
		<h3>Create Room</h3>
		<br />
		<p>Room Name:</p>
		<v-text-field
			name="Room Name"
			label ="Room Name"
			type="text"			
			class="input-group "
			v-model="name"								
		></v-text-field>		
		<br />
		<p>Room Description</p>
			<v-text-field
			name="Room Description"
			label ="Room Description"
			type="text"			
			class="input-group "
			v-model="description"
			multi-line			
		></v-text-field>		
		<br />
		<input type="checkbox" name="create-room-checkbox" v-model="isPublic">Public room</input>
		<br />
	  	<v-btn  class="blue darken-1 white--text mt-3" name="create-room" value ="Create Room" @click.native="createRoom">Create Room</v-btn>
	
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
			data: '',
			name: '',
			description: '',
			isPublic: false
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
	                this.username = response.data.username;
	              } else {
	              	this.logout();
	              }
	           } );
	    },

	    createRoom() {
	    	axios.post( '/api/room/createRoom', {
	    		name: this.name,
	    		description: this.description,
					isPublic: this.isPublic
	    	} )
	    	.then( ( response ) => {
	    		if( response.data.resolved ) {
	    			this.data = response.data.data;
	    			router.push( '/main' );
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