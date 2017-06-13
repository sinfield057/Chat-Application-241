<template>
  <v-card :class="messageType">
    <v-card-title class="pa-0">
      <span class="text-xs-left title ml-2">{{sender}}</span>
    </v-card-title>
    <p class ="text-xs-left subheading px-4 mb-0">
      <slot>Mesaj Gol</slot>
    </p>
    <p class ="text-xs-right caption mb-0 mr-2">Sent at: {{date}}</p>
  </v-card>
</template>
<script>
import axios from 'axios'
import router from '../../router'
import session from 'vue-cookie'

export default {
  name: 'message-bubble',
  props: [ 'sender', 'receiver', 'date' ],
  data() {
    return {
      sent: "message-bubble py-2 elevation-1 mb-2 grey lighten-3 sent",
      received: "message-bubble py-2 elevation-1 mb-2 blue lighten-3 received"
    }
  },
  computed: {
      messageType: function(){
        return this.sender === this.receiver ? this.sent: this.received;
      }
  }
}
</script>
<style scoped>
  .message-bubble{
    max-width:50%;
    margin: 0 auto;
  }
  .sent{
    margin-right: 20px;
  }
  .received{
    margin-left: 20px;
  }
</style>