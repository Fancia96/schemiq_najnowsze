import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from "@/routes";
import App from './App2.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
Vue.config.productionTip = false

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(VueRouter)

let user = JSON.parse(localStorage.getItem('user')) || null;

const router = new VueRouter({
  routes // short for `routes: routes`
})
new Vue({
  render: h => h(App),
  router,
  data: {
    user: user
  }
}).$mount('#app');