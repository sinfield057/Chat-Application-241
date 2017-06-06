<template>
	<div class="main">
		<div class="user-info-container">
			<h1>Welcome {{ username }}!</h1>
			<p>Your userId is: {{ userId }}</p>

			<input type="button" name="logout-button" value="Logout" @click="logout">	
			<router-link :to=" 'createRoom' ">Create Room</router-link>
		</div>
		
		<br />
		<span>Moderated Rooms</span>
		<input type="button" v-if="moderatedRooms.length != 0" v-bind:value="toggleModeratedValue" @click="toggleModerated">
		<div class="room-list">
			<p v-if="moderatedRooms.length == 0">You aren't moderating any rooms!</p>
			<ul id="moderated-rooms" v-bind:class="{ 'show': showModerated }">
				<li v-for="room in moderatedRooms">
					<room-card :room="room"></room-card>
				</li>
			</ul>
		</div>
		<br />
		<br />

		<span>Joined Rooms</span>
		<input type="button" v-if="joinedRooms.length != 0" v-bind:value="toggleJoinedValue" @click="toggleJoined">
		<div class="room-list">
			<p v-if="joinedRooms.length == 0">You haven't joined any rooms yet!</p>
			<ul id="joined-rooms" v-bind:class="{ 'show': showJoined }">
				<li v-for="room in joinedRooms">
					<room-card :room="room"></room-card>
				</li>
			</ul>
		</div>
		<br />
		<br />

		<span>Available Rooms</span>
		<input type="button" v-if="availableRooms.length != 0" v-bind:value="toggleAvailableValue" @click="toggleAvailable">
		<div class="room-list">
			<p v-if="availableRooms.length == 0">There are no new rooms to join!</p>
			<ul id="available-rooms" v-bind:class="{ 'show': showAvailable }">
				<li v-for="room in availableRooms">
					<room-card :room="room"></room-card>
					<br/>
					<button v-on:click="requestAccessToRoom(room.name)" v-if="room.admin">Request access</button>
					<button v-on:click="joinRoom(room.name)" v-else>Join room</button>
				</li>
			</ul>
		</div>

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
			rooms: [],
			showModerated: true,
			toggleModeratedValue: 'Show',
			showJoined: true,
			toggleJoinedValue: 'Show',
			showAvailable: true,
			toggleAvailableValue: 'Show',
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
	    },

	    toggleModerated() {
	    	this.showModerated = !this.showModerated;
	    	this.toggleModeratedValue = this.showModerated ? 'Show' : 'Hide';
	    },

	    toggleJoined() {
	    	this.showJoined = !this.showJoined;
	    	this.toggleJoinedValue = this.showJoined ? 'Show' : 'Hide';
	    },

	    toggleAvailable() {
	    	this.showAvailable = !this.showAvailable;
	    	this.toggleAvailableValue = this.showAvailable ? 'Show' : 'Hide';
	    },

			joinRoom(name) {
				axios.post('/api/room/joinRoom', {
					name: name
				})
				.then((response) => {
					if (response.data.resolved) {
						router.push('/');
					} else {
						this.data = response.data.data;
					}
				});
			},

			requestAccessToRoom(name) {
				axios.post('/api/room/requestAccess', {
					name: name,
					requesterId: this.userId
				})
				.then((response) => {
					console.log("Response", response.data.data);
					if (response.data.resolved) {
						var room = this.rooms.find((room) => {
							return room.name == name;
						});
						console.log("Room", room);

						if (room && room.requests) {
							room.requests.push(requesterId);
						}
					} else {
						this.data = response.data.data;
					}
				});
			}

	},

	computed: {
		moderatedRooms: function() {
			return this.rooms.filter( ( room ) => {
				return room.admin == this.userId;
			} );
		},

		joinedRooms: function() {
			return this.rooms.filter((room) => {
				if (~room.users.indexOf(this.userId)) {
					if (room.admin) {
						return !(room.admin == this.userId);
					} else {
						return true;
					}
				}
			});
		},

		availableRooms: function() {
			var rooms = this.rooms.filter( ( room ) => {
				return room.admin != this.userId &&
					   room.users.indexOf( this.userId ) == -1;
			} );
			console.log(rooms);
			return rooms;
		}

	},

	beforeMount() {
		this.getSessionInfo();
		this.getRooms();
	}
}
	
</script>

<style>
	.room-list ul {
		list-style: none;
		margin-bottom: 50px;
	}

	.show {
		display: none;
	}

	.user-info-container {
		margin: 20px;
	}
</style>