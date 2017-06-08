<template>
	<v-card >
		<!--linkul catre camera e momentan activ indiferent daca userul curent face parte sau nu din camera-->
		<router-link :to="{ path: 'roomChat/' + room.name }">
			<v-card-row class="blue darken-1 white--text mt-3">
				<v-card-title class="card-title">
					<v-card-column class="text-xs-left headline ml-2"> {{ room.name }}</v-card-column>
					<v-card-column class="text-xs-right caption"> Members(TODO)</v-card-column>
				</v-card-title>
			</v-card-row>
			<v-card-text>
				<p class ="text-xs-left subheading">{{ room.description }}</p>
				<p class ="text-xs-right caption mb-0 ">Created at: {{ room.createdAt }}</p>
			</v-card-text>
		</router-link>
		<v-card-row v-if="availableRoom">
			<button v-if="room.admin" @click="requestAccessToRoom(room.name)" :disabled="~room.requests.indexOf(username) ? true : false">
				{{ ~room.requests.indexOf(username) ? "Waiting for request to be accepted" : "Request access" }}
			</button>
			<button v-else @click="joinRoom(room.name)">Join room</button>
		</v-card-row>
	</v-card>

</template>

<script>
import axios from 'axios'
import router from '../../router'
import session from 'vue-cookie'

export default {
	name: 'room-card',
	props: [ 'room','username' ],
	data() {
		return{
			room: this.room,
			username: this.username,
		}
	},
	methods: {
	    joinRoom(name) {
	    	axios.post('/api/room/joinRoom', {
				name: name,
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
				name: name,
				requester: this.username
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
		//as vrea sa folosesc functia asta ca sa determin daca camera ar face parte din availableRooms
		availableRoom: function() {
			console.log(this.room.name + (this.room.admin != this.username) + '&&' + (this.room.users.indexOf( this.username ) == -1) + '\n\n');
			return this.room.admin != this.username &&
				   this.room.users.indexOf( this.username ) == -1;
		}
	}
}

</script>

<style scoped>
	a {
		text-decoration: none;
		color: inherit;
	}
</style>