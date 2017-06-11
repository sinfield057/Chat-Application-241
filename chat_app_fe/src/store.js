import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({ 
  state: { 
    username: 'default',
    room: '',
    messages: null
  }, 
  
  getters: {
  	getMessages ( state ) {
  		return state.messages;
  	}
  },
  
  mutations: { 
    changeUsername ( state, user ) { 
      state.username = user;
    },
    
    addMessageList ( state, messageList ) {
    	state.messages = messageList;
    },

    pushNewMessage ( state, newMessage ) {
    	state.messages.push( newMessage );
    },

    changeRoom ( state, roomName ) {
    	state.room = roomName;
    }
  } 
});