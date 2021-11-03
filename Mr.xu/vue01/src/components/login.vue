<template>
  <div class="login_container">
    <div class="login_box">
      <el-image style="max-width:100px; position: absolute; left: 50%; transform: translate(-50%, 15%);" :src="require('../assets/logohdu_logo.jpg')"></el-image>
      <!--登录表单-->
      <el-form ref="loginRef" class="login_form" :model="loginForm" :rules="loginFormRules">
        <el-form-item prop="studentId">
          <el-input v-model="loginForm.studentId" prefix-icon="el-icon-user" placeholder="学号/工号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" prefix-icon="el-icon-unlock" placeholder="密码">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login_button" @click="login">Sign In</el-button>
        </el-form-item>
      </el-form>
      <!-- 注册
      <el-button type="text" class="upLink" @click="register">Sign Up <i class="el-icon-arrow-right"></i></el-button>
      -->
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      //登录表单的数据绑定对象
      loginForm: {
        studentId: '',
        password: ''
      },
      //表单的验证规则
      loginFormRules: {
        studentId: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { min: 8, max: 8, message: '学号为8位', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login() {
      if(this.loginForm.studentId === '20212021' && this.loginForm.password === 'happy2021'){
        this.store.commit('setLoginState', 'true');
        this.$router.push('/prj');
      }
      else this.$message.error("账号或密码错误");
      /*this.axios.post("/user/login", this.qs.stringify(this.loginForm))
        .then((response) => {
          if(response.data.message !== "SUCCESS") return this.$message.error("用户名或密码错误");
          window.sessionStorage.setItem("token", response.data.data.token);
          this.store.commit('setId', response.data.data.id);
          this.store.commit('setStudentId', response.data.data.studentId);
          //console.log(this.$store.state.studentId);
          this.$router.push('/main');
        })
        .catch((error) => {
          console.log(error);
        });*/
    },
    register() {
      this.$router.push('/register')
    }
  }
}
</script>

<style lang="less" scoped>
.login_container {
  background-color: #42474d;
  height: 100%;
  width: 100%;
}

.login_box {
  width: 300px;
  height: 400px;
  background-color: #fff;
  border-radius: 5px;
  position: absolute;
  left: 50%;
  transform: translate(-50%, 15%);
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login_button {
  width: 100%;
  background-color: #50565e;
  font-family: "Helvetica Neue";
}

.login_form {
  position: absolute;
  bottom: 15px;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.upLink {
  font-family: "Helvetica Neue";
  position: absolute;
  right: 0;
  bottom: 10px;
  padding: 0 20px;
  color: #929497;
}

#box1 {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
