import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    id: '',
    studentId: '',
    activeIndex: '',
    loginState: ''
  },
  mutations: {
    setId(state, id) {
      state.id = id
    },
    setStudentId(state, studentId){
      state.studentId = studentId
    },
    setActiveIndex(state, activeIndex){
      state.activeIndex = activeIndex
    },
    setLoginState(state, loginState){
      state.loginState = loginState
    }
  }
})

export default store
