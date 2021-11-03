// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import elementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'
import './global.css'

Vue.use(elementUI)

import axios from 'axios'
Vue.prototype.axios = axios

import qs from 'qs';
Vue.prototype.qs = qs;

import store from './store'
Vue.prototype.store = store

import { Message } from 'element-ui'
Vue.prototype.$message = Message

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
