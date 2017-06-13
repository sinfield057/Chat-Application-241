<template>
<div class="roomChat">
<v-card  class = "title-users ">
    <v-card-row class=" titlu blue darken-1">
      <v-card-title class="white--text ">
          {{ roomName }}
      </v-card-title>
      <v-spacer></v-spacer>
      </v-card-row>
  <h4> Users <h4>
  <ul v-if="room" class="roomUsers subheading">
    <li v-for="user in room.users"> {{ user }} </li>
  </ul>
  <br />
  <h5> Requests <h5>
  <ul v-if="room" class="roomRequests subheading">
    <li v-for="request in room.requests"> {{ request }} </li>
  </ul>
  </v-card>
  <br />
  <div>
    <ul>
      <li v-for="message in messages"> {{ message.sender }}: {{ message.message }} </li>
    </ul>
  </div>
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
      message: '',
      room: null,
      messages: [],
      currentRoom: ''
 
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
    },

    getRoom() {
      axios.post('/api/room/getRoom', {
        username: this.username,
        name: this.roomName
      })
      .then((response) => {
        if (response.data.resolved) {
          this.room = response.data.data;
          this.$store.commit( 'addMessageList', this.room.messages );
          this.$store.commit( 'changeRoom', this.room.name );
        } else {
          router.push('/');
        }
      });
    }
  },

  beforeMount() {
		this.getData();
	},

  mounted() {
    this.$store.watch( state => {
      return state.messages;
    }, 
    messageList => {
      this.messages = messageList;
    })
  }
}

</script>

<style scoped>
.roomUsers, .roomRequests {
  list-style-type: none;
  padding: 0;
}
.title-users {
    width:55%;
    left:22.5%;
}
.titlu {
  float:center;
}
</style>