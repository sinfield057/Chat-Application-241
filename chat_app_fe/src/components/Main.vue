<template>
	<div class="main">
		<div class="user-info-container">
			<h3>Welcome {{ username }}!</h3>
			<p>Your userId is: {{ userId }}</p>

			<input type="button" name="logout-button" value="Logout" @click="logout">	
			<router-link :to=" 'createRoom' ">Create Room</router-link>
		</div>
		
		<br />
		<span>Moderated Rooms</span>
		<input type="button" v-if="moderatedRooms.length != 0" v-bind:value="toggleModeratedValue" @click="toggleModerated">
		<div class="room-list">
			<p v-if="moderatedRooms.length == 0">You aren't moderating any rooms!</p>
			<ul id="moderated-rooms" v-bind:class="{ 'hide': hideModerated }">
				<li v-for="room in moderatedRooms">
				<router-link :to="{ path: 'roomChat/' + room.name }">
					<room-card :room="room"></room-card>
				</router-link>
					<p v-if="room.requests.length">Requests: </p>
					<ul v-if="room.requests">
						<li v-for="request in room.requests">
							{{ request }}
							</br>
							<button v-on:click="acceptRequest(room.name, request)">Accept request</button>
							<button v-on:click="declineRequest(room.name, request)">Decline request</button>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<br />
		<br />

		<span>Joined Rooms</span>
		<input type="button" v-if="joinedRooms.length != 0" v-bind:value="toggleJoinedValue" @click="toggleJoined">
		<div class="room-list">
			<p v-if="joinedRooms.length == 0">You haven't joined any rooms yet!</p>
			<ul id="joined-rooms" v-bind:class="{ 'hide': hideJoined }">
				<li v-for="room in joinedRooms">
					<router-link :to="{ path: 'roomChat/' + room.name }">
						<room-card :room="room"></room-card>
					</router-link>
				</li>
			</ul>
		</div>
		<br />
		<br />

		<span>Available Rooms</span>
		<input type="button" v-if="availableRooms.length != 0" v-bind:value="toggleAvailableValue" @click="toggleAvailable">
		<div class="room-list">
			<p v-if="availableRooms.length == 0">There are no new rooms to join!</p>
			<ul id="available-rooms" v-bind:class="{ 'hide': hideAvailable }">
				<li v-for="room in availableRooms">
					<room-card :room="room"></room-card>
					<br/>
					<button v-if="room.admin" @click="requestAccessToRoom(room.name)" :disabled="~room.requests.indexOf(userId) ? true : false">
						{{ ~room.requests.indexOf(userId) ? "Waiting for request to be accepted" : "Request access" }}
					</button>
					<button v-else @click="joinRoom(room.name)">Join room</button>
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
			hideModerated: false,
			toggleModeratedValue: 'Hide',
			hideJoined: false,
			toggleJoinedValue: 'Hide',
			hideAvailable: false,
			toggleAvailableValue: 'Hide',
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
	    	this.hideModerated = !this.hideModerated;
	    	this.toggleModeratedValue = this.hideModerated ? 'Show' : 'Hide';
	    },

	    toggleJoined() {
	    	this.hideJoined = !this.hideJoined;
	    	this.toggleJoinedValue = this.hideJoined ? 'Show' : 'Hide';
	    },

	    toggleAvailable() {
	    	this.hideAvailable = !this.hideAvailable;
	    	this.toggleAvailableValue = this.hideAvailable ? 'Show' : 'Hide';
	    },

			joinRoom(name) {
				axios.post('/api/room/joinRoom', {
					name: name
				})
				.then((response) => {
					if (response.data.resolved) {
						const room = this.rooms.find((room) => {
							return room.name == name;
						});

						room.users.push(this.userId);
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
					if (response.data.resolved) {
						const room = this.rooms.find((room) => {
							return room.name == name;
						});

						if (room && room.requests) {
							room.requests.push(this.userId);
						}
					} else {
						this.data = response.data.data;
					}
				});
			},

			acceptRequest(name, requesterId) {
				axios.post('/api/room/acceptRequest', {
					name: name,
					requesterId: requesterId
				})
				.then((response) => {
					if (response.data.resolved) {
						const room = this.rooms.find((room) => {
							return room.name == name;
						});

						if (room && room.requests) {
							const index = room.requests.find((request) => {
								return request == requesterId;
							});

							room.requests.splice(index, 1);
						}
						
					} else {
						this.data = response.data.data;
					}
				});
			},

			declineRequest(name, requesterId) {
				axios.post('/api/room/declineRequest', {
					name: name,
					requesterId: requesterId
				})
				.then((response) => {
					if (response.data.resolved) {
						const room = this.rooms.find((room) => {
							return room.name == name;
						});

						if (room && room.requests) {
							const index = room.requests.find((request) => {
								return request == requesterId;
							});

							room.requests.splice(index, 1);
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
	.room-list ul {
		list-style: none;
		margin-bottom: 50px;
	}

	.hide {
		display: none;
	}

	.user-info-container {
		margin: 20px;
	}
</style>