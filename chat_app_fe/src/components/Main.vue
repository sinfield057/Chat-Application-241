<template>
	<div class="main">
		<h1>Welcome {{ username }}!</h1>
		<p>Your userId is: {{ userId }}</p>
		<br />
		<p>Moderated Rooms</p>
		<ul id="moderated-rooms" class="room-list">
			<li v-for="room in moderatedRooms">
				<room-card :room="room"></room-card>
			</li>
		</ul>
		<br />
		<br />

		<p>Joined Rooms</p>
		<ul id="joined-rooms" class="room-list">
			<li v-for="room in joinedRooms">
				<room-card :room="room"></room-card>
			</li>
		</ul>
		<br />
		<br />

		<p>Available Rooms</p>
		<ul id="available-rooms" class="room-list">
			<li v-for="room in availableRooms">
				<room-card :room="room"></room-card>
			</li>
		</ul>

		<input type="button" name="logout-button" value="Logout" @click="logout">	
		<router-link :to=" 'createRoom' ">Create Room</router-link>
	</div>
</template>

<script>
import axios from 'axios'
import router from '../router'
import session from 'vue-cookie'
import RoomCard from './subcomponents/RoomCard'

export default {
	name: 'main',

	components: {
		'room-card': RoomCard
	},

	data() {
		return {
			username: '',
			sessionValid: false,
			userId: '',
			data: '',
			rooms: []
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

	    getRooms() {
	      const self = this;

	      axios.get( '/api/room/getRooms' )
	           .then( ( response ) => {
	              if( response.data.resolved == true ) {
	                self.rooms = response.data.data;
	              } else {
	                self.rooms = response.data.data;
	              }
	           } ); 
	    }

	},

	computed: {
		moderatedRooms: function() {
			return this.rooms.filter( ( room ) => {
				return room.admin == this.userId;
			} );
		},

		joinedRooms: function() {
			return this.rooms.filter( ( room ) => {
				return room.admin != this.userId &&
					   room.users.indexOf( this.userId ) != -1;
			} );
		},

		availableRooms: function() {
			return this.rooms.filter( ( room ) => {
				return room.admin != this.userId &&
					   room.users.indexOf( this.userId ) == -1;
			} );
		}

	},

	beforeMount() {
		this.getSessionInfo();
		this.getRooms();
	}
}
	
</script>

<style>
	.room-list {
		list-style: none;
	}
</style>