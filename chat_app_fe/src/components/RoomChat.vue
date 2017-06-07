<template>
<div class="roomChat">
  <h3> {{ roomName }} </h3>
  <h4> Users <h4>
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
      room: null,
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
          this.data = response.data.data;
          router.push('/');
        }
      });
    },

    getRoom() {
      axios.get('/api/room/getRoom', {
        name: this.roomName
      })
      .then((response) => {
        console.log(response.data.data);
        if (response.data.resolved) {
          this.room = response.data.data;
        } else {
          this.data = response.data.data;
        }
      });
    }
  },

  beforeMount() {
		this.getSessionInfo();
    this.getRoom();
	}
}
</script>

<style scoped>

</style>