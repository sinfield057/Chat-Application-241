<template>
<div class="roomChat">
<h1>boona</h1>
  <h3> {{ roomName }} </h3>
  <input type="text" name="message-send" placeholder="Enter Message" v-model="message">
  <button v-on:click="sendMessage()">Send</button>
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
    }
  },

  methods: {
    getSessionInfo() {
      axios.get('/api/user/validate')
      .then((response) => {
        if (response.data.resolved) {
          this.sessionValid = true;
          this.userId = response.data.userId;
          this.username = response.data.username;
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
  },

  beforeMount() {
		this.getSessionInfo();
	}
}

</script>

<style scoped>

</style>