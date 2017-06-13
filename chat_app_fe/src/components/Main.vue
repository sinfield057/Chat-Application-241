<template>
	<div class="main">
		<div class="user-info-container cyan lighten-3 pa-2">
			<div class = "ml-3">
				<h5 class = "mb-0 white--text">Welcome {{ username }}!</h5>
			</div>
			<div>
				<v-btn  class="red lighten-2 white--text" name="logout-button"  @click.native="logout">Logout</v-btn>
				<v-btn  class="green lighten-1 white--text" name="create-room" @click.native = "createRoom">Create Room</v-btn>
			</div>
		</div>

		<br />
		<div class = "list-heading text-xs-left">
			<span class="headline">Moderated Rooms</span>
			<hr>
			<input type="button" v-if="moderatedRooms.length != 0" v-bind:value="toggleModeratedValue" @click="toggleModerated">
		</div>
		<div class="room-list grey lighten-4">
			<p v-if="moderatedRooms.length == 0" class="pa-4">You aren't moderating any rooms!</p>
			<ul id="moderated-rooms" v-bind:class="{ 'hide': hideModerated }">
				<li v-for="room in moderatedRooms">
				<room-card :room="room" :username = "username"></room-card>
					<p v-if="room.requests.length" class ="headline pa-2 mb-0">Requests: </p>
					<ul v-if="room.requests" class = "mb-0">
						<li v-for="request in room.requests">
							<span class = "title">User: {{ request }}</span>
							<v-btn @click.native="acceptRequest(room.name,request)" class="blue darken-1 white--text">Accept request</v-btn>
							<v-btn @click.native="declineRequest(room.name,request)" class="grey lighten-1">Decline request</v-btn>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<br />
		<br />
		<div class = "list-heading text-xs-left">
			<span class="headline">Joined Rooms</span>
			<hr>
			<input type="button" v-if="joinedRooms.length != 0" v-bind:value="toggleJoinedValue" @click="toggleJoined">
		</div>
		<div class="room-list grey lighten-4">
			<p v-if="joinedRooms.length == 0" class="pa-4">You haven't joined any rooms yet!</p>
			<ul id="joined-rooms" v-bind:class="{ 'hide': hideJoined }">
				<li v-for="room in joinedRooms">
					<room-card :room="room" :username = "username"></room-card>
				</li>
			</ul>
		</div>
		<br />
		<br />
		<div class = "list-heading text-xs-left">
			<span class = "headline">Available Rooms</span>
			<hr>
			<input type="button" v-if="availableRooms.length != 0" v-bind:value="toggleAvailableValue" @click="toggleAvailable">
		</div>
		<div class="room-list grey lighten-4">
			<p v-if="availableRooms.length == 0" class="pa-4">There are no new rooms to join!</p>
			<ul id="available-rooms" v-bind:class="{ 'hide': hideAvailable }">
				<li v-for="room in availableRooms">
					<room-card :room="room" :username="username"></room-card>
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
		createRoom() {
			router.push('/createRoom');
		},
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

		getData() {
			const self = this;
			axios.get('/api/user/validate')
			.then((response) => {
				if (response.data.resolved) {
					this.sessionValid = true;
					this.username = response.data.username;
	                self.sessionValid = true;
	                self.userId = response.data.userId;
	                self.username = response.data.username;
	                this.getRooms();
	                self.$store.commit( 'changeUsername', self.username );
	              } else {
	              	self.logout();
	              }
	           } );
	    },

	    getRooms() {
				axios.post('/api/room/getRooms',
				{
					username: this.username
				})
					.then(response => {
						if (response.data.resolved) {
							this.rooms = response.data.data;
						} else {
							this.data = response.data.data;
						}
					});
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
					username: this.username,
					name: name
				})
				.then((response) => {
					if (response.data.resolved) {
						const room = this.rooms.find((room) => {
							return room.name == name;
						});

						room.users.push(this.username);
					} else {
						this.data = response.data.data;
					}
				});
			},

			requestAccessToRoom(name) {
				axios.post('/api/room/requestAccess', {
					username: this.username,
					name: name,
				})
				.then((response) => {
					if (response.data.resolved) {
						const room = this.rooms.find((room) => {
							return room.name == name;
						});

						if (room && room.requests) {
							room.requests.push(this.username);
						}
					} else {
						this.data = response.data.data;
					}
				});
			},

			deleteRequest(name, requester) {
				const room = this.rooms.find(room => room.name == name);

				if (room && room.requests) {
					room.requests = room.requests.filter(request => request !== requester);
					return true;
				}

				return false;
			},

			acceptRequest(name, requester) {
				axios.post('/api/room/acceptRequest', {
					username: this.username,
					name: name,
					requester: requester
				})
				.then((response) => {
					if (response.data.resolved) {
						this.deleteRequest(name, requester);
					} else {
						this.data = response.data.data;
					}
				});
			},

			declineRequest(name, requester) {
				axios.post('/api/room/declineRequest', {
					username: this.username,
					name: name,
					requester: requester
				})
				.then((response) => {
					if (response.data.resolved) {
						this.deleteRequest(name, requester);
					} else {
						this.data = response.data.data;
					}
				});
			}
	},

	computed: {
		moderatedRooms: function() {
			return this.rooms.filter( ( room ) => {
				return room.admin == this.username;
			} );
		},

		joinedRooms: function() {
			return this.rooms.filter((room) => {
				if (~room.users.indexOf(this.username)) {
					if (room.admin) {
						return !(room.admin == this.username);
					} else {
						return true;
					}
				}
			});
		},

		availableRooms: function() {
			return this.rooms.filter( ( room ) => {
				return room.admin != this.username &&
					   room.users.indexOf( this.username ) == -1;
			} );
		}

	},

	beforeMount() {
		this.getData();
	}
}

</script>

<style scoped>
	.room-list ul {
		list-style: none;
		margin-bottom: 50px;
	}

	.hide {
		display: none;
	}
	ul{
		padding:0;
	}
	.user-info-container {
		margin: 0 auto;
		display: flex;
		flex-wrap: nowrap;
		width: 90%;
		justify-content: space-between;
		align-items: center;
	}
	.user-info-container > div{
		flex-basis:auto;
		text-align: left;
	}
	.room-list{
		width:90%;
		margin: 0 auto;
		padding-left: 10%;
		padding-right: 10%;
	}
	.list-heading{
		margin: 0 auto;
		display: flex;
		flex-wrap: nowrap;
		width: 90%;
		align-items: center;
	}
	.list-heading>hr{
		display: inline-block;
		flex-grow: 1;
	}
</style>