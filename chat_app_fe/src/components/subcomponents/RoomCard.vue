<template>
	<v-card >
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
	</v-card>


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