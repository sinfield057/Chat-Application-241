webpackJsonp([1],[,,function(e,t,a){"use strict";var n=a(6),o=a(48),r=a(40),s=a.n(r),i=a(41),u=a.n(i),c=a(42),d=a.n(c),l=a(39),p=a.n(l);n.a.use(o.a),t.a=new o.a({mode:"history",routes:[{path:"/",name:"Home",component:s.a},{path:"/main",name:"Main",component:u.a},{path:"/register",name:"Register",component:d.a},{path:"/createRoom",name:"CreateRoom",component:p.a}]})},,,,,,,,,,function(e,t,a){a(35);var n=a(1)(a(30),a(47),null,null);e.exports=n.exports},,,,,,,,,,,,,,,,,,function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"app"}},function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a(3),o=a.n(n),r=a(2),s=a(5);a.n(s);t.default={name:"createRoom",data:function(){return{username:"",sessionValid:!1,userId:"",data:"",name:"",description:""}},methods:{logout:function(){var e=this;o.a.get("/api/user/logout").then(function(t){t.data.resolved?r.a.push("/"):(e.data=r.a.data.data,r.a.push("/"))})},getSessionInfo:function(){var e=this;o.a.get("/api/user/validate").then(function(t){t.data.resolved?(e.sessionValid=!0,e.userId=t.data.userId,e.username=t.data.username):e.logout()})},createRoom:function(){var e=this;o.a.post("/api/room/createRoom",{userId:e.userId,name:e.name,description:e.description}).then(function(t){t.data.resolved,e.data=t.data.data})}},beforeMount:function(){this.getSessionInfo()}}},function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a(3),o=a.n(n),r=a(2),s=a(5);a.n(s);t.default={name:"home",data:function(){return{username:"",password:"",sessionValid:!1,data:""}},methods:{doLogin:function(){var e=this;o.a.post("/api/user/login",{username:e.username,password:e.password}).then(function(t){e.data=t.data.data,1==t.data.resolved&&r.a.push("/main")}).catch(function(t){e.data="Error: "+t})},doRegister:function(){var e=this;o.a.post("/api/user/register",{username:e.username,password:e.password}).then(function(t){e.data=t.data.data}).catch(function(t){e.data="Error: "+t})},checkSession:function(){o.a.get("/api/user/validate").then(function(e){1==e.data.resolved&&r.a.push("/main")})}},beforeMount:function(){this.checkSession()}}},function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a(3),o=a.n(n),r=a(2),s=a(5);a.n(s);t.default={name:"main",data:function(){return{username:"",sessionValid:!1,userId:"",data:""}},methods:{logout:function(){var e=this;o.a.get("/api/user/logout").then(function(t){t.data.resolved?r.a.push("/"):(e.data=r.a.data.data,r.a.push("/"))})},getSessionInfo:function(){var e=this;o.a.get("/api/user/validate").then(function(t){t.data.resolved?(e.sessionValid=!0,e.userId=t.data.userId,e.username=t.data.username):e.logout()})}},beforeMount:function(){this.getSessionInfo()}}},function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a(6),o=a(12),r=a.n(o),s=a(2);n.a.config.productionTip=!1,new n.a({el:"#app",router:s.a,template:"<App/>",components:{App:r.a}})},function(e,t){},,,function(e,t,a){e.exports=a.p+"static/img/logo2.8f4cfd6.png"},function(e,t,a){var n=a(1)(a(31),a(45),null,null);e.exports=n.exports},function(e,t,a){var n=a(1)(a(32),a(43),null,null);e.exports=n.exports},function(e,t,a){var n=a(1)(a(33),a(44),null,null);e.exports=n.exports},function(e,t,a){var n=a(1)(null,a(46),null,null);e.exports=n.exports},function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"home"},[a("h1",[e._v("Chat App")]),e._v(" "),a("input",{directives:[{name:"model",rawName:"v-model",value:e.username,expression:"username"}],attrs:{type:"text",name:"username",placeholder:"Username"},domProps:{value:e.username},on:{input:function(t){t.target.composing||(e.username=t.target.value)}}}),e._v(" "),a("input",{directives:[{name:"model",rawName:"v-model",value:e.password,expression:"password"}],attrs:{type:"password",name:"password",placeholder:"Password"},domProps:{value:e.password},on:{input:function(t){t.target.composing||(e.password=t.target.value)}}}),e._v(" "),a("br"),e._v(" "),a("br"),e._v(" "),a("input",{attrs:{type:"button",name:"login-button",value:"Login"},on:{click:e.doLogin}}),e._v(" "),a("input",{attrs:{type:"button",name:"register-button",value:"Register"},on:{click:e.doRegister}}),e._v(" "),a("p",[e._v(e._s(e.data))])])},staticRenderFns:[]}},function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"main"},[a("h1",[e._v("Welcome "+e._s(e.username)+"!")]),e._v(" "),a("p",[e._v("Your userId is: "+e._s(e.userId))]),e._v(" "),a("p",[e._v(e._s(e.data))]),e._v(" "),a("input",{attrs:{type:"button",name:"logout-button",value:"Logout"},on:{click:e.logout}}),e._v(" "),a("router-link",{attrs:{to:"createRoom"}},[e._v("Create Room")])],1)},staticRenderFns:[]}},function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"createRoom"},[a("h1",[e._v("Create Room")]),e._v(" "),a("input",{directives:[{name:"model",rawName:"v-model",value:e.name,expression:"name"}],attrs:{type:"text",name:"room-name",placeholder:"Enter room name"},domProps:{value:e.name},on:{input:function(t){t.target.composing||(e.name=t.target.value)}}}),e._v(" "),a("textarea",{directives:[{name:"model",rawName:"v-model",value:e.description,expression:"description"}],attrs:{name:"create-room-button"},domProps:{value:e.description},on:{input:function(t){t.target.composing||(e.description=t.target.value)}}}),e._v(" "),a("input",{attrs:{type:"button",name:"create-room",value:"Create room"},on:{click:e.createRoom}}),e._v(" "),a("input",{attrs:{type:"button",name:"logout-button",value:"Logout"},on:{click:e.logout}}),e._v(" "),a("router-link",{attrs:{to:"main"}},[e._v("Back to main")])],1)},staticRenderFns:[]}},function(e,t){e.exports={render:function(){var e=this,t=e.$createElement;e._self._c;return e._m(0)},staticRenderFns:[function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"register"},[a("h1",[e._v("Register form fam")])])}]}},function(e,t,a){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("img",{attrs:{src:a(38),id:"logo"}}),e._v(" "),n("router-view")],1)},staticRenderFns:[]}}],[34]);
//# sourceMappingURL=app.ac84a910fd5960681454.js.map