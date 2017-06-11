<template>
<div class="roomChat">
  <h3> {{ roomName }} </h3>
  <input type="text" name="message-send" placeholder="Enter Message" v-model="message">
  <button v-on:click="sendMessage()">Send</button>
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
			userId: '',
			data: '',
      message: ''
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

    sendMessage() {
      const payload = {
        sender: this.username,
        message: this.message,
        dateSent: Date.now(),
        room: this.roomName
      }
      this.$socket.emit( 'sendMessage', payload );
      this.message = '';
    }

    getRoom() {
      axios.post('/api/room/getRoom', {
        username: this.username,
        name: this.roomName
      })
      .then((response) => {
        if (response.data.resolved) {
          this.room = response.data.data;
        } else {
          this.data = response.data.data;
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