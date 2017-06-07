<template>
<div class="main">
  <h3> {{ roomName }} </h3>
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
			data: ''
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
    }
  },

  beforeMount() {
		this.getSessionInfo();
	}
}
</script>

<style scoped>

</style>