<template>
	<v-card >
		<router-link :to="availableRoom ? {path: 'main'} : { path: 'roomChat/' + room.name }">
			<v-card-row :class="{available: availableRoom}" class="blue darken-1 white--text mt-3">
				<v-card-title class="card-title">
					<span class="text-xs-left headline ml-2 room-name"> {{ room.name }}</span>
					<span class="text-xs-right caption members">({{room.users.length}} members)</span>
				</v-card-title>
			</v-card-row>
			<v-card-text :class="{available: availableRoom}">
				<p class ="text-xs-left subheading">{{ room.description }}</p>
				<p class ="text-xs-right caption mb-0 ">Created at: {{ room.createdAt }}</p>
			</v-card-text>
		</router-link>
		<v-card-row :class="{available: availableRoom}" v-if="availableRoom" >
			<v-btn v-if="room.admin" @click.native="requestAccessToRoom(room.name)" :disabled="~room.requests.indexOf(username) ? true : false" class="blue darken-1 white--text mt-3">
				{{ ~room.requests.indexOf(username) ? "Waiting for request to be accepted" : "Request access" }}
			</v-btn>
			<v-btn v-else @click.native = "joinRoom(room.name)" class="blue darken-1 white--text mt-3">Join room</v-btn>
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
				username: this.username,
				name: name,
			})
			.then((response) => {
				if (response.data.resolved) {
					this.room.users.push(this.username);
				} else {
					this.data = response.data.data;
				}
			});
		},

		requestAccessToRoom(name) {
	    	axios.post('/api/room/requestAccess', {
				username: this.username,
				name: name,
				requester: this.username
			})
			.then((response) => {
				if (response.data.resolved) {
					if (this.room && this.room.requests) {
						this.room.requests.push(this.username);
					}
				} else {
					this.data = response.data.data;
				}
			});
		}
	},
	computed: {
		availableRoom: function() {
			return (this.room.admin != this.username) &&
				   (this.room.users.indexOf( this.username ) === -1);
		}
	}
}

</script>

<style scoped>
	.card-title{
		display: flex;
		flex-wrap: nowrap;
	}
	.room-name{
		flex-grow:2;
		flex-shrink:1;
	}
	.members{
		flex-grow:1;
		flex-shrink:2;
	}
	a {
		text-decoration: none;
		color: inherit;
	}
	.available {
		cursor: default;
	}
</style>