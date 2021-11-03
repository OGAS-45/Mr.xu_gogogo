<template>
  <div id="app">
    <div class="top">

      <el-image style="max-width:180px;" :src="require('@/assets/logohdu.png')"> </el-image>
      <span style="font-weight: 500;font-size: 26px;text-shadow: 2px 2px 2px #d5dde7">创新创业项目资源中心</span>
      <div class="total-right">
        <el-popover
          popper-class="popover-demo"
          placement="bottom"
          title="user"
          width="100"
          trigger="hover"
          v-if="isLogin()">
          <!--
          <div><el-button type="text">我的点赞</el-button></div>
          <div><el-button type="text">我的收藏</el-button></div>
          <el-divider class="divider-demo"></el-divider>
          <el-button type="text">我的工作台</el-button><br>
          -->
          <el-button type="text" @click="logout">退出登录</el-button>
          <el-avatar icon="el-icon-user-solid" slot="reference"></el-avatar>
        </el-popover>
        <el-button v-else plain @click="judge">登录</el-button>
        <el-image style="max-width:200px;margin-left: 20px" :src="require('@/assets/motto.png')"> </el-image>
      </div>
    </div>
    <el-row type="flex" justify="center">
      <el-col :xs="24" :sm="22" :md="20" :lg="20" :xl="18">
        <router-view/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: 'App',
        data() {
            return {
              windowWidth: document.documentElement.clientWidth
            }
        },
        created () {
          // 在页面加载时读取sessionStorage
          if (sessionStorage.getItem('store')) {
            this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem('store'))));
            //读取数据后，删除sessionStorage里的store值
            sessionStorage.removeItem("store");
          }
          // 在页面刷新时将store保存到sessionStorage里
          window.addEventListener('beforeunload', () => {
            sessionStorage.setItem('store', JSON.stringify(this.$store.state))
          })
          //监听页面回退
          if (window.history && window.history.pushState) {
            history.pushState(null, null, document.URL);
            setTimeout(() => {
              window.addEventListener("popstate", function() {         // 监听回退按钮
                // 刷新页面
                location.reload();
              }, false); //false阻止默认事件
            }, 0);
          }
        },
        methods: {
          isLogin() {
            const tokenStr = window.sessionStorage.getItem('token');
            /*if(!tokenStr) return false;
            else return true;*/
            if (this.store.state.loginState === 'true') return true;
            else return false;
          },
          goLogin() {
            this.$router.push('/login');
          },
          judge() {
            this.$confirm('登录功能当前仅限管理人员使用, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.goLogin();
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '取消登录'
              });
            });
          },
          logout() {
            this.store.commit('setLoginState', 'false');
            window.sessionStorage.clear();
            this.$router.push('/prj');
          }
        }
    }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    margin: 0px;
    padding: 0px;
    width: 96rem;
    height: 100%;
    background-color: #f5f6f7;
    overflow: auto;
  }

  .top{
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #fff;
    /* background-image: url("../assets/motto.png"); */
    padding: 10px 30px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  }

  /** 右侧部分 */
  .total-right {
    display: flex;
    align-items: center;
  }

  .popover-demo {
    text-align: center;
  }

  .divider-demo {
    margin: 0 0;
  }
</style>
