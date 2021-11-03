<template>
  <div class="all">
    <div class="demo-form-inline">
      <el-menu :default-active="activeIndex"  v-on:select="selectItem" class="el-menu-demo" mode="horizontal" router>
        <el-menu-item class="menu-item" :index="item.route" v-for="item in moduleList" :key="item.name">{{ item.name }}</el-menu-item>
      </el-menu>
      <span class="num-result">{{ '共搜索到'+num+'个结果' }}</span>
    </div>

    <div>
      <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      moduleList: [
        { name: '项目', route: '/prj' },
        //{ name: '视频', route: '/movie' },
        { name: '资源', route: '/resour' },
        //{ name: '专题', route: '/title' },
        //{ name: '图片', route: '/image' }
      ],
      activeIndex: '/prj',
      num: ''
    }
  },
  created() {
    this.getNum()
  },
  methods: {
    getIndex() {
      this.activeIndex = this.store.state.activeIndex;
    },
    selectItem(index) {
      this.store.commit('setActiveIndex', index);
      //console.log(this.store.state.activeIndex);
      this.getNum();
    },
    getNum() {
      this.getIndex()
      this.axios.post(this.activeIndex+'/rows').then(response =>
      {
        this.num = response.data;
        //console.log(this.num);
      }).catch(error =>
      {
        console.log(error);
      });
    }
  }
}
</script>

<style scoped>
.all {
  height: 100%;
}

.demo-form-inline {
  /*border: solid 1px #cecece;*/
  border-radius: 3px;
  border: 1px solid #EBEEF5;
  display: flex;
  align-items: center;
  margin-top: 20px;
  padding: 0px 20px;
  background-color: white;
}

.el-menu-demo {
  border-width: 0;
}

.menu-item {
  font-size: 16px;
  font-weight: 700;
  margin: 0 15px;
}

.num-result {
  flex: 1;
  text-align: right;
  font-size: 14px;
  color: #818193;
}

.text-button {
  color: #555666;
  font-size: 16px;
  font-weight: 800;
  border-radius: 0;
  padding-left: 3px;
  padding-right: 3px;
  margin: 0 20px;
}

.el-button--text:hover {
  color: black;
}

.el-button--text:focus {
  color: black;
  border-color: #2d73d4;
  border-width: 0 0 3px 0;
}
</style>
