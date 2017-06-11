<template>
<div class="roomChat">
  <h3> {{ roomName }} </h3>
  <h4> Users <h4>
  <ul v-if="room" class="roomUsers">
    <li v-for="user in room.users"> {{ user }} </li>
  </ul>
  <br />
  <h4> Requests <h5>
  <ul v-if="room" class="roomRequests">
    <li v-for="request in room.requests"> {{ request }} </li>
  </ul>
</div>
</template>

<script>
import axios from 'axios'
import router from '../router'
import session from 'vue-cookie'

export default {
	name: 'roomChat',

  props: ['roomName'],

  data() {
    return {
      username: '',
			sessionValid: false,
			data: '',
      room: null,
    }
  },

  methods: {
    getData() {
      axios.get('/api/user/validate')
      .then((response) => {
        if (response.data.resolved) {
          this.username = response.data.username;          
          this.sessionValid = true;
          
          this.getRoom();
        } else {
          router.push('/');
        }
      });
    },

    getRoom() {
      axios.post('/api/room/getRoom', {
        username: this.username,
        name: this.roomName
      })
      .then((response) => {
        if (response.data.resolved) {
          this.room = response.data.data;
        } else {
          router.push('/');
        }
      });
    }
  },

  beforeMount() {
		this.getData();
	}
}
</script>

<style scoped>
.roomUsers, .roomRequests {
  padding: 0;
}
</style>