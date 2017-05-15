<template>
	<div id="room-card">
		Room card
		<p>Name: {{ room.name }}</p>
		<p>Description: {{ room.description }}</p>
		<p>Created at: {{ room.createdAt }}</p>
	</div>
</template>

<script>
import axios from 'axios'
import router from '../../router'
import session from 'vue-cookie'

export default {
	name: 'room-card',
	props: [ 'room' ],
}

</script>

<style>
	#room-card {
		border: solid black 1px;
		border-radius: 15px;
		margin-top: 10px;
		padding: 10px;
	}
</style>