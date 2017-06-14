<template>
  <div class="roomChat">
    <v-navigation-drawer permanent light>
      <v-list class="pa-0">
        <v-list-item>
          <v-list-tile>Users</v-list-tile>
          <v-divider></v-divider>
          <v-list dense class="pt-0">
            <v-list-item v-for="user in room.users">
              <v-list-tile> {{ user }} </v-list-tile>
            </v-list-item>
          </v-list>
        </v-list-item>
        <v-list-tile v-if="room.requests.length"> Requests </v-list-tile>
        <v-divider v-if="room.requests.length"></v-divider>
          <v-list dense class="pt-0">
            <v-list-item v-for="request in room.requests">
              <v-list-tile> {{ request }} </v-list-tile>
            </v-list-item>
          </v-list>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar class="blue">
      <v-toolbar-title> {{ room.name }} </v-toolbar-title>
    </v-toolbar>
    <main>
      <v-container>
        <div v-if="room.messages.length" class="messages-container">
          <div class="messages">
            <ul>
              <li v-for="message in messages">
                <message-bubble :sender="message.sender" :receiver="username" :date="message.dateSent">
                  {{ message.message }}
                </message-bubble>
              </li>
            </ul>
          </div>
        </div>
      <div class="send-message" @keyup.enter="sendMessage">
        <v-layout row wrap>
          <v-text-field v-model="message" name="input-7-1" label="Enter message" class="input"></v-text-field>
          <v-btn class="blue darken-1 white--text mt-4" name="create-room" value ="Send" @click.native="sendMessage">Send</v-btn>
        </v-layout>
      </div>
      </v-container>
    </main>
  </div>
</template>

<script>
import axios from 'axios'
import router from '../router'
import session from 'vue-cookie'
import MessageBubble from './subcomponents/MessageBubble'

export default {
	name: 'roomChat',

  props: ['roomName'],

  components: {
    'message-bubble': MessageBubble
  },

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
      if (this.message.length) {
        const payload = {
          sender: this.username,
          message: this.message,
          dateSent: Date.now(),
          room: this.roomName
        };
        this.$socket.emit( 'sendMessage', payload );
        this.message = '';
      }
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
    },

    scrollToEnd() {
      const container = this.$el.querySelector('.messages');

      if (container) {
        container.scrollTop = container.scrollHeight;
      }
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
      this.scrollToEnd();
    })
  },

  ready() {
    this.scrollToEnd();
  }
}

</script>

<style scoped>
.messages-container {
  position: fixed;
  height: 750px;
}

.messages {
  height: 70vh;
  overflow-y: auto;
}

.send-message {
  position: fixed;
  bottom: 0;
}

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
ul{
  list-style-type: none;
  padding: 0px;
}

.input {
  width: 500px;
}
</style>