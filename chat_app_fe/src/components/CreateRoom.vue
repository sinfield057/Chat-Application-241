<template>
	<div class="createRoom">
		<v-card >
			<v-card-row class="blue darken-1 white--text mt-3">
				<v-card-title class="card-title">
					<v-card-column class="text-md-center display-2 ml-2"> Create Room</v-card-column>				
				</v-card-title>
			</v-card-row>			
			
			<v-card-text class ="grey lighten-4">	
			<v-text-field
				name="Room Name"
				label ="Room Name"
				type="text"			
				class="input-group body-1"
				v-model="name"								
			></v-text-field>
			<v-divider></v-divider>
			<br />	
				<v-text-field
				name="Room Description"
				label ="Room Description"
				type="text"			
				class="input-group body-1"
				v-model="description"
				multi-line			
				rows = "3"
			></v-text-field>		
			<br />
			<v-divider></v-divider>
			<input type="checkbox" name="create-room-checkbox" v-model="isPublic" >Public room</input>
			<br />
			<v-btn  class="blue darken-1 white--text mt-3" name="create-room" value ="Create Room" @click.native="createRoom">Create Room</v-btn>
			<br />
			<router-link :to=" 'main' ">Back to main</router-link>
			<v-card-text>
		</v-card >
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

		getData() {
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
					username: this.username,
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
		this.getData();
	}
}
	
</script>
<style scoped>
.createRoom{
		width: 50%;  
		height: 70%;
		justify-content: center;
		margin: auto;
	
	}
</style>