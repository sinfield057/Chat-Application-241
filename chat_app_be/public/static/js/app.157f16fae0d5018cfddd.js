webpackJsonp([1],[,,,function(e,t,n){"use strict";var a=n(4),r=n(40),o=n(35),s=n.n(o),i=n(36),u=n.n(i);a.a.use(r.a),t.a=new r.a({routes:[{path:"/",name:"Home",component:s.a},{path:"/main",name:"Main",component:u.a}]})},,,,,,,function(e,t,n){n(32);var a=n(2)(n(29),n(39),null,null);e.exports=a.exports},,,,,,,,,,,,,,,,,,,function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"app"}},function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n(11),r=n.n(a),o=n(3);t.default={name:"home",data:function(){return{username:"",password:"",data:""}},methods:{doLogin:function(){var e=this;r.a.post("/api/user/login",{username:e.username,password:e.password}).then(function(t){e.data=t.data.data,o.a.push("/main")}).catch(function(t){e.data="Error: "+t})},doRegister:function(){var e=this;r.a.post("/api/user/register",{username:e.username,password:e.password}).then(function(t){e.data=t.data.data}).catch(function(t){e.data="Error: "+t})}}}},function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n(4),r=n(10),o=n.n(r),s=n(3);a.a.config.productionTip=!1,new a.a({el:"#app",router:s.a,template:"<App/>",components:{App:o.a}})},function(e,t){},,function(e,t,n){e.exports=n.p+"static/img/logo2.8f4cfd6.png"},function(e,t,n){var a=n(2)(n(30),n(37),null,null);e.exports=a.exports},function(e,t,n){var a=n(2)(null,n(38),null,null);e.exports=a.exports},function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"home"},[n("h1",[e._v("Chat App")]),e._v(" "),n("input",{directives:[{name:"model",rawName:"v-model",value:e.username,expression:"username"}],attrs:{type:"text",name:"username",placeholder:"Username"},domProps:{value:e.username},on:{input:function(t){t.target.composing||(e.username=t.target.value)}}}),e._v(" "),n("input",{directives:[{name:"model",rawName:"v-model",value:e.password,expression:"password"}],attrs:{type:"password",name:"password",placeholder:"Password"},domProps:{value:e.password},on:{input:function(t){t.target.composing||(e.password=t.target.value)}}}),e._v(" "),n("br"),e._v(" "),n("br"),e._v(" "),n("input",{attrs:{type:"button",name:"login-button",value:"Login"},on:{click:e.doLogin}}),e._v(" "),n("input",{attrs:{type:"button",name:"register-button",value:"Register"},on:{click:e.doRegister}}),e._v(" "),n("p",[e._v(e._s(e.data))])])},staticRenderFns:[]}},function(e,t){e.exports={render:function(){var e=this,t=e.$createElement;e._self._c;return e._m(0)},staticRenderFns:[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"main"},[n("h1",[e._v("You are logged in")])])}]}},function(e,t,n){e.exports={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("img",{attrs:{src:n(34),id:"logo"}}),e._v(" "),a("router-view")],1)},staticRenderFns:[]}}],[31]);
//# sourceMappingURL=app.157f16fae0d5018cfddd.js.map