<template>
  <div style="margin-top: 20px">
    <el-form :inline="true" class="demo-form-inline">
      <el-input
        v-model="search"
        class="search_name"
        size="mini"
        placeholder="输入资源名称查询">
        <el-button slot="append" icon="el-icon-search" @click="onSearch()"></el-button>
      </el-input>
      <el-button style="margin-left: 10px"
        class="el-icon-refresh"
        type="text"
        @click="refreshData">刷新
      </el-button>
      <el-divider direction="vertical" class="divider-demo"></el-divider>
      <el-button
        type="text"
        class="button-focus"
        @click="sort('clicked')">热度优先
      </el-button>
      <el-button
        type="text"
        class="button-focus"
        @click="sort('stared')">星级优先
      </el-button>
      <el-divider direction="vertical" class="divider-demo"></el-divider>
      <el-button
        size="mini"
        class="operate-button"
        v-if="isManager()"
        @click="judgeWhich('add')">添加资源
      </el-button>
      <el-dialog title="资源相关信息" :visible.sync="dialogFormVisible" center @close="cancelForm">
        <el-form style="text-align: left" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="110px">
          <el-form-item label="资源名称" prop="rname">
            <el-input v-model="ruleForm.rname"></el-input>
          </el-form-item>
          <el-form-item label="上传者姓名" prop="owner">
            <el-input v-model="ruleForm.owner"></el-input>
          </el-form-item>
          <el-form-item label="资源类别" prop="cls">
            <el-select v-model="ruleForm.cls" placeholder="请选择资源类别">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="资源介绍" prop="description">
            <el-input type="textarea" v-model="ruleForm.description"></el-input>
          </el-form-item>
          <el-form-item label="资源链接" prop="url">
            <el-input v-model="ruleForm.url"></el-input>
          </el-form-item>
          <el-form-item label="链接密码" prop="rpassword">
            <el-input v-model="ruleForm.rpassword"></el-input>
          </el-form-item>
          <el-form-item label="标签" prop="label">
            <el-popover
              placement="bottom"
              title="最多选择3个标签"
              width="400"
              trigger="click">
              <el-tag
                style="margin-bottom: 10px"
                :key="tag"
                v-for="tag in dynamicTags"
                closable
                :disable-transitions="false"
                @close="handleClose(tag)">
                {{tag}}
              </el-tag>
              <el-input
                class="input-new-tag"
                v-if="inputVisible"
                v-model="inputValue"
                ref="saveTagInput"
                size="small"
                @keyup.enter.native="handleInputConfirm"
                @blur="handleInputConfirm"
              >
              </el-input>
              <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
              <el-divider></el-divider>
              <span style="margin-right: 10px">推荐</span>
              <el-button type="success" style="margin-bottom: 10px" size="small" plain
                         v-for="(tag, index) in recommendTags" :key="index"
                         @click="addTags(tag)">{{ tag }}</el-button>
              <el-button  slot="reference" size="small" plain>添加标签</el-button>
              <span slot="reference" style="margin-left: 10px; color: #408cff">已添加的标签:
                <el-tag v-for="tag in dynamicTags" :key="tag" style="margin-left: 10px" size="small">{{ tag }}</el-tag>
              </span>
            </el-popover>
          </el-form-item>
          <div style="text-align: right">
            <el-form-item>
              <el-button @click="resetForm">重 置</el-button>
              <el-button type="primary" @click="determine">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </el-form>
    <div class="middle">
      <div class="left">
        <el-menu
          :default-active="clsName"
          :active="clsName"
          class="el-menu-vertical-demo">
          <el-menu-item v-for="item in category" :key="item" :index="item" class="menu-item-demo" @click="getByCls(item)">
            <span slot="title">{{ item }}</span>
          </el-menu-item>
        </el-menu>
      </div>
      <div class="right">
        <el-collapse v-model="activeNames" @change="handleChange" class="resource-demo">
          <el-collapse-item v-for="(item, index) in resourceList" :name="index" :key="index" class="title-demo">
            <template slot="title">
              <span style="font-size: 15px; font-weight: 550; margin-right: 50px">{{ item.rname }}</span>
              <el-tag size="small" type="danger" style="margin-right: 10px" v-for="tags in item.label.split('，')" :key="tags">{{ tags }}</el-tag>
              <!--<span class="card-icon"><i class="el-icon-view"></i>&nbsp;{{item.clicked}}</span>-->
              <div style="margin: 0 30px 0 auto;" v-if="isManager()">
                <el-button size="mini" @click="updateResoure(item); judgeWhich('update')">修改</el-button>
                <el-button size="mini" @click="judgeDelete(item)">删除</el-button>
              </div>
              <div style="margin: 0 30px 0 auto;" v-else></div>
            </template>
            <div style="white-space: pre-wrap;">{{ item.description }}</div>
            <div style="margin-top: 10px">
              <span style="margin-right: 50px">上传者：{{ item.owner }}</span>
              <span style="margin-right: 50px">上传时间：{{ item.cdate }}</span>
              <span style="color: #408cff">下载次数：{{ item.clicked }}</span>
            </div>
            <div style="margin-top: 10px">
              <el-button-group style="margin-right: 10px">
                <el-button size="mini" class="choose-button choose" plain @click="addStar(item.id)">点赞</el-button>
                <el-button class="choose-button" size="mini" plain>{{item.stared}}</el-button>
              </el-button-group>
              <!--<el-button-group>
                <el-button size="mini" class="choose-button choose" plain>收藏</el-button>
                <el-button class="choose-button" size="mini" plain>{{item.stared}}</el-button>
              </el-button-group>-->
              <el-button-group style="float: right; margin-right: 50px">
                <el-button type="primary" size="mini" class="choose-button" @click="getResource(item)">获取资源</el-button>
                <el-button class="choose-button" size="mini" plain>{{'提取密码：' + item.url.split(" ")[1]}}</el-button>
              </el-button-group>
            </div>
          </el-collapse-item>
        </el-collapse>
        <div class="pages">
          <el-pagination
            background
            :disabled = "disablePage"
            :current-page.sync="currentPage"
            small
            layout="prev, pager, next"
            :page-size="pageSize"
            :total="total"
            @current-change="handleCurrentChange">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      search: '',
      clsName: '',
      activeNames: ['1'],
      resourceList: [],
      dialogFormVisible: false,
      ruleForm: {
        id: '',
        rname: '',
        owner: '',
        cls: '',
        description: '',
        url: '',
        rpassword: '',
        label: ''
      },
      options: [{
        value: '高校课程',
        label: '高校课程'
      }, {
        value: '资格考试',
        label: '资格考试'
      }, {
        value: '公司财报',
        label: '公司财报'
      }, {
        value: '行业研究',
        label: '行业研究'
      }, {
        value: '实用工具',
        label: '实用工具'
      }, {
        value: '生活娱乐',
        label: '生活娱乐'
      }, {
        value: '其他',
        label: '其他'
      }],
      value: [],
      category: [],
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      recommendTags: [
        '软件工程', '计算机类', '会计学', '经济学', '理学', 'html', 'css', 'vue', 'Spring', 'SpringBoot', 'js', 'axios'
      ],
      operate: '',    //判断操作为 增加 或 修改
      //分页部分
      pageSize: 50,
      currentPage: 1,
      total: 0,
      disablePage: false,
      //添加、修改资源表单的规则
      rules: {
        rname: [
          { required: true, message: '请输入资源名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        owner: [
          { required: true, message: '请输入上传者姓名', trigger: 'blur' }
        ],
        cls: [
          { required: true, message: '请选择资源类别', trigger: 'change' }
        ],
        description: [
          { required: true, message: '请对资源进行简单描述', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '请输入百度网盘链接', trigger: 'blur' }
        ],
        rpassword: [
          { required: true, message: '请输入链接密码', trigger: 'blur' },
          { min: 4, max: 4, message: '链接密码为 4 位', trigger: 'blur' }
        ],
        label: [
          { required: true, message: '请选择合适的标签', trigger: ['blur', 'change'] }
        ]
      }
    };
  },
  created() {
    this.store.commit('setActiveIndex', '/resour');
    this.ListResource();
    this.getPages();
    this.getCls();
  },
  methods: {
    handleChange(val) {
      console.log(val);
    },
    ListResource() {
      this.axios.post('/resour/page').then(response =>
      {
        this.resourceList = response.data;
      }).catch(error =>
      {
        console.log(error);
      });
      /*
      this.axios.post('/resour/ListResour').then(response =>
      {
        this.resourceList = response.data;
        console.log(this.resourceList);
      }).catch(error =>
      {
        console.log(error);
      });
      */
    },
    //搜索区域 相关
    onSearch() {
      let postData = {
        rname: this.search
      }
      this.axios.post('/resour/ListByName', this.qs.stringify(postData))
        .then((response) => {
          if(response.status === 200){
            this.resourceList = response.data;
            this.clsName = '';
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    refreshData() {
      location.reload();
    },
    sort(type) {
      this.resourceList.sort(this.compare(type));
    },
    compare(attr) {
      return function(a,b){
        var val1 = a[attr];
        var val2 = b[attr];
        return val2 - val1;
      }
    },
    getResource(e) {
      this.addClick(e.id);
      window.open(e.url.split(" ")[0], '_blank');
    },
    handleCurrentChange() {
      console.log(`当前页: ${this.currentPage}`);
      let postData = this.qs.stringify({
        page: this.currentPage
      });
      this.axios({
        method: 'post',
        url: '/resour/page',
        data: postData
      }).then(response =>
      {
        this.resourceList = response.data;
      }).catch(error =>
      {
        console.log(error);
      });
    },
    getPages() {
      this.axios.post('/resour/rows').then(response =>
      {
        this.total = response.data;
      }).catch(error =>
      {
        console.log(error);
      });
    },
    //左侧菜单相关
    getCls() {
      this.axios.post('/resour/ListResourCls')
        .then((response) => {
          console.log(response);
          this.category = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getByCls(e) {
      let clsData = {
        cls: e
      }
      this.axios.post('/resour/ListByCls', this.qs.stringify(clsData))
        .then((response) => {
          console.log(response);
          this.resourceList = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //判断操作为 增加 或 修改
    judgeWhich(e) {
      this.dialogFormVisible = true;
      if(e === 'add') {
        this.operate = 'add'
      } else if (e === 'update') {
        this.operate = 'update'
      }
    },
    determine() {
      if(this.operate === 'add') {
        this.submitForm();
      } else if (this.operate === 'update') {
        this.updateForm();
      }
    },
    //添加资源 区域 的 methods
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      this.ruleForm.label = '';
      for(let i = 0; i<this.dynamicTags.length; i++){
        if(i > 0) this.ruleForm.label += "，";
        this.ruleForm.label += this.dynamicTags[i];
      }
    },
    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        if(this.dynamicTags.length < 3)
          this.dynamicTags.push(inputValue);
        else alert("最多添加 3 个标签");
      }
      this.inputVisible = false;
      this.inputValue = '';
    },
    addTags(e) {
      if(this.dynamicTags.length == 0){
        this.dynamicTags.push(e);
        this.ruleForm.label = e;
      }else if(this.dynamicTags.length < 3){
        this.dynamicTags.push(e);
        this.ruleForm.label = this.ruleForm.label + "，" + e;
      }else {
        this.$message.error("最多添加 3 个标签");
      }
    },
    submitForm() {
      console.log(this.ruleForm.cls);
      let insertForm = {
        rname: this.ruleForm.rname,
        owner: this.ruleForm.owner,
        cls: this.ruleForm.cls,
        description: this.ruleForm.description,
        url: this.ruleForm.url.concat(" " , this.ruleForm.rpassword),
        label: this.ruleForm.label
      };
      this.$refs.ruleForm.validate(async valid => {
        if (!valid) return null  // 如果验证失败就不往下继续执行

        this.axios.post('/resour/insert', this.qs.stringify(insertForm))
          .then((response) => {
            if(response.status === 200) this.$message.success("添加资源成功")
            console.log(response);
            this.dialogFormVisible = false  // 关闭弹框
            this.$refs.ruleForm.resetFields() // 清空表单
            this.ListResource(); // 刷新表单
          })
          .catch((error) => {
            console.log(error);
          });

        /*const { data: res } = await this.axios.post('/resour/insert', this.qs.stringify(insertForm))
        if (res !== '') this.$message.success('添加成功')

        this.getUserList() // 重新调用，刷新表单*/
      })


    },
    cancelForm() {
      this.dialogFormVisible = false;
      this.$message({
        showClose: true,
        message: '取消对资源的操作'
      });
      this.ruleForm = {
          id: '',
          rname: '',
          owner: '',
          cls: '',
          description: '',
          url: '',
          rpassword: '',
          label: ''
      }
      this.dynamicTags = []
    },
    resetForm(){
      this.ruleForm = {
        id: '',
        rname: '',
        owner: '',
        cls: '',
        description: '',
        url: '',
        rpassword: '',
        label: ''
      }
      this.dynamicTags = []
    },
    //修改资源
    updateResoure(e) {
      this.ruleForm.id = e.id;
      this.ruleForm.rname = e.rname;
      this.ruleForm.owner = e.owner;
      this.ruleForm.cls = e.cls;
      this.ruleForm.description = e.description;
      this.ruleForm.url = e.url;
      this.ruleForm.rpassword = e.url.substring(e.url.length-5);
      this.ruleForm.label = e.label;
      this.dynamicTags = e.label.split("，");
    },
    updateForm() {
      let insertForm = {
        id: this.ruleForm.id,
        rname: this.ruleForm.rname,
        owner: this.ruleForm.owner,
        cls: this.ruleForm.cls,
        description: this.ruleForm.description,
        url: this.ruleForm.url.concat(" " , this.ruleForm.rpassword),
        label: this.ruleForm.label
      };
      this.$refs.ruleForm.validate(async valid => {
        if (!valid) return null  // 如果验证失败就不往下继续执行

        this.axios.post('/resour/update', this.qs.stringify(insertForm))
          .then((response) => {
            if(response.status === 200) this.$message.success("修改资源成功")
            console.log(response);
            this.dialogFormVisible = false  // 关闭弹框
            this.ListResource(); // 刷新表单
            this.ruleForm = {
              id: '',
              rname: '',
              owner: '',
              cls: '',
              description: '',
              url: '',
              rpassword: '',
              label: ''
            }
            this.dynamicTags = []
          })
          .catch((error) => {
            console.log(error);
          });
      })
    },
    //确定是否删除资源
    judgeDelete(e) {
      this.$confirm('此操作将永久删除该资源, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteResource(e);
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //删除资源
    deleteResource(e) {
      let resourceId = {
        id: e.id
      }
      this.axios.post('/resour/delete', this.qs.stringify(resourceId))
        .then((response) => {
          if(response.status === 200) {
            this.ListResource(); // 刷新表单
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 浏览 或 点赞
    addStar(e){
      let postId = {
        id: e
      }
      this.axios.post('/resour/addStar',this.qs.stringify(postId))
        .then((response) => {
          if(response.status === 200) this.ListResource();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    addClick(e){
      let postId = {
        id: e
      }
      this.axios.post('/resour/addClick',this.qs.stringify(postId))
        .then((response) => {
          if(response.status === 200) this.ListResource();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //判断是否为管理人员
    isManager(){
      if(this.store.state.loginState === 'true') return true;
      else return false;
    }
  }
}
</script>

<style scoped>
.middle {
  margin-top: 10px;
  border-width: 1px;
  width: 100%;
  overflow: auto;
  background-color: white;
}

.left {
  float: left;
  width: 13%;
}

.right {
  float: right;
  width: 87%;
}

/* 搜索框区域css 一横条*/
.search_name{
  width: 400px;
}

/*/deep/ .el-input__inner {
  background-color: #f5f7fa;
}*/

/deep/ input::-webkit-input-placeholder {
  color: #818193;
}

/deep/ .el-form-item__content {
  line-height: 0;
}

/deep/ .el-input-group__append {
  background-color: #659cee;
  color: white;
}

.demo-form-inline {
  /*border: solid 1px #cecece;*/
  border-radius: 3px;
  border: 1px solid #EBEEF5;
  display: flex;
  align-items: center;
  /** margin-top: 8px; */
  padding: 8px 20px;
  background-color: white;
}

.operate-button {
  margin-left: 30px;
  padding-left: 10px;
  padding-right: 10px;
}
.divider-demo {
  height: 2em;
  margin-left: 40px;
}

/** 左侧菜单栏 */
.el-menu-vertical-demo {
  border-right-width: 0;
  border-top: 1px solid #EBEEF5;
}

.menu-item-demo {
  text-align: center;
  color: #B3D8FF;
}

.menu-item-demo:hover {
  background-color: white;
}

.menu-item-demo.is-active {
  background-color: white;
  color: #409EFF;
  font-weight: 550;
  font-size: 16px;
}

/** 资源面板样式 */
.resource-demo {
  background-color: white;
  padding: 0 30px;
}

.title-demo >>> .el-collapse-item__arrow {
  margin: 0 8px 0 0;
}

.choose-button {
  font-size: 12px;
  height: 25px;
  display: flex;
  align-items: center;
}

.choose {
  background-color: #fafbfc;
}

.card-icon {
  margin: 0 50px 0 auto;
  color: #888B90;
}

/* 筛选按钮 css */
.button-focus {
  margin-left: 30px;
  padding-left: 10px;
  padding-right: 10px;
  color: #888B90;
}
.button-focus:hover {
  color: #408cff;
}
.button-focus:focus {
  background-color: #659cee;
  color: white;
}

/** 添加资源 样式 */
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

/** 分页 样式 */
.pages{
  margin: 10px 10px;
  padding: 0px;
  text-align: right;
}
</style>
