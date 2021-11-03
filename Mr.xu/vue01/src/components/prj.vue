<template>
  <div class="all">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input
          v-model="search"
          class="search_name"
          size="mini"
          placeholder="输入项目名称查询">
          <el-button slot="append" icon="el-icon-search" @click="onSearch()"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item style="margin-left: 10px">
        <el-button
          class="el-icon-refresh"
          type="text"
          @click="refreshData">刷新
        </el-button>
        <el-divider direction="vertical" class="divider-demo"></el-divider>
      </el-form-item>
      <el-form-item>
        <el-button
          type="text"
          class="button-focus"
          @click="sort('clicked')">热度优先
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button
          type="text"
          class="button-focus"
          @click="sort('stared')">星级优先
        </el-button>
        <el-divider direction="vertical" class="divider-demo"></el-divider>
      </el-form-item>
      <el-form-item>
        <el-select v-model="value" clearable placeholder="根据项目状态进行选择" size="mini" style="margin-left: 40px">
          <el-option
            v-for="item in options"
            :key="item"
            :value="item">
          </el-option>
        </el-select>
        <el-button size="mini" type="primary" @click="findByState">筛选</el-button>
      </el-form-item>
    </el-form>
    <div class="middle">
      <div class="left">
        <div class="project-box">
          <el-row :gutter="10">
            <el-col :span="6" v-for="item in tableData" :key="item.index">
              <el-popover
                placement="right-start"
                width="400"
                trigger="click">
                <div>
                  <div class="text-name">{{item.prjname}}</div>
                  <div v-if="item.label !== null" style="padding: 10px 10px; background-color: #fef0f0; border-radius: 3px;margin-bottom: 10px; border-left: 5px solid #F56C6C">
                    <span style="color: #F56C6C">文章标签：</span>
                    <el-tag
                      v-for="tags in item.label.split(',')"
                      :key="tags"
                      size="small"
                      type="danger"
                      style="margin-right: 5px; background-color: white">
                      {{ tags }}
                    </el-tag>
                  </div>
                  <div class="detail-item">{{item.description}}</div>
                  <el-divider></el-divider>
                  <div class="detail-item">项目负责人：{{item.owner}}</div>
                  <div class="detail-item">更新时间：{{item.updatedate}}</div>
                  <div class="detail-item">项目状态：{{item.state}}</div><br>
                  <el-button-group style="margin-top: 10px">
                    <el-button size="mini" class="choose-button choose" plain @click="addStar(item.id)">点赞</el-button>
                    <el-button class="choose-button" size="mini" plain>{{item.stared}}</el-button>
                  </el-button-group>
                  <!--<el-button-group style="margin-top: 10px">
                    <el-button size="mini" class="choose-button choose" plain>收藏</el-button>
                    <el-button class="choose-button" size="mini" plain>{{item.stared}}</el-button>
                  </el-button-group>-->
                  <el-button type="primary" class="download"><i class="el-icon-download"></i> 实现地址</el-button>
                  <div class="resourceTree" v-if="data2.length !== 0">
                    <span>相关资源</span>
                    <el-tree :data="data2" :props="defaultProps" @node-click="handleNodeClick" default-expand-all></el-tree>
                  </div>
                </div>
                <el-card class="box-card" shadow="hover" slot="reference" @click.native="onCardClick(item.id)">
                  <el-image :src="item.simage" fit="contain" style="width: 200px;height: 150px"></el-image>
                  <div class="text-name">{{item.prjname}}</div>
                  <div class="text-item">{{item.description}}</div>
                  <el-divider></el-divider>
                  <div class="card-bottom">
                    <div style="flex: 1; text-align: right">
                      <span class="card-icon"><i class="el-icon-thumb"></i>&nbsp;{{item.stared}}</span>
                      <!--<span class="card-icon"><i class="el-icon-star-off"></i>&nbsp;{{item.stared}}</span>-->
                      <span class="card-icon"><i class="el-icon-view"></i>&nbsp;{{item.clicked}}</span>
                    </div>
                  </div>
                </el-card>
              </el-popover>
            </el-col>
          </el-row>
        </div>
        <br>
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
      <!--<div class="right">
        <el-tree :data="data2" :props="defaultProps" @node-click="handleNodeClick" default-expand-all></el-tree>
      </div>-->
    </div>
  </div>
</template>


<script>
  export default {

    data() {
      return {
        tableData: [],
        search: '',
        dialogVisible: false,
        dialogUpdate: false,
        pageSize: 50,
        currentPage: 1,
        total: 0,
        disablePage: false,
        data2: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        currentRow:null,
        activeIndex: '/projects',
        options: [],
        value: ''
      }
    },
    methods: {
      getPrj() {
        this.axios.post('/prj/page').then(response =>
        {
          this.tableData = response.data;
        }).catch(error =>
        {
          console.log(error);
        });
      },
      findState() {
        this.axios.post('/prj/findState')
          .then((response) => {
            this.options = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      },
      findByState() {
        let stateData = {
          state: this.value,
          prjname: this.search
        }
        this.axios.post('/prj/findByState',this.qs.stringify(stateData))
          .then((response) => {
            this.tableData = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      },
      addStar(e){
        let postId = {
          id: e
        }
        this.axios.post('/prj/addStar',this.qs.stringify(postId))
          .then((response) => {
            if(response.status === 200) this.getPrj();
          })
          .catch((error) => {
            console.log(error);
          });
      },
      addClick(e){
        let postId = {
          id: e
        }
        this.axios.post('/prj/addClick',this.qs.stringify(postId))
          .then((response) => {
            if(response.status === 200) this.getPrj();
          })
          .catch((error) => {
            console.log(error);
          });
      },
      handleNodeClick(data){
        this.axios.post('/resour/ListResourUrlById/'+data.id).then(response =>
        {
          window.open(response.data, '_blank');
          //this.data2 = response.data;

        }).catch(error =>
        {
          console.log(error);
        });
      },
      handleCurrentChange() {
        console.log(`当前页: ${this.currentPage}`);
        let postData = this.qs.stringify({
          page: this.currentPage
        });
        this.axios({
          method: 'post',
          url: '/prj/page',
          data: postData
        }).then(response =>
        {
          this.tableData = response.data;
        }).catch(error =>
        {
          console.log(error);
        });
      },
      onCardClick(e) {
        //console.log(e);
        this.addClick(e);
        this.axios.post('/resour/ListResourByClsTree/'+e).then(response =>
        {
          this.data2 = response.data;

        }).catch(error =>
        {
          console.log(error);
        });
      },
      onSearch() {
        let postData = this.qs.stringify({
          prjname: this.search
        });
        this.axios({
          method: 'post',
          url: '/prj/ListByName',
          data: postData
        }).then(response =>
        {
          this.tableData = response.data;
          this.disablePage = true;
        }).catch(error =>
        {
          console.log(error);
        });
      },
      getPages() {
        this.axios.post('/prj/rows').then(response =>
        {
          this.total = response.data;
        }).catch(error =>
        {
          console.log(error);
        });
      },
      refreshData() {
        location.reload();
      },
      sort(type) {
        this.tableData.sort(this.compare(type));
      },
      compare(attr) {
        return function(a,b){
          var val1 = a[attr];
          var val2 = b[attr];
          return val2 - val1;
        }
      }
    },
    created() {
      /*this.axios.get('static/user.json').then(response =>
      {
          this.tableData = response.data.tableData;
          this.total = response.data.tableData.length;
          // console.log(JSON.parse(JSON.stringify(response.data))['tableData'])
      });*/
      this.getPrj();

      this.axios.post('/prj/rows').then(response =>
      {
        this.total = response.data;
      }).catch(error =>
      {
        console.log(error);
      });

      this.axios.post('/resour/ListResourByClsTree').then(response =>
      {

        this.data2 = response.data;

      }).catch(error =>
      {
        console.log(error);
      });

      this.findState();

      this.store.commit('setActiveIndex', '/prj');
    }
  }
</script>
<style scoped>
  .pages{
    margin: 0px;
    padding: 0px;
    text-align: right;
  }
  .all{
    text-align: center;
    margin-top: 20px;
  }
  .middle{
    width:100%;
  }
  .left{
    float:left;
    width:100%;
  }
  .right{
    float:right;
    width:24%;
    margin-top: 10px;
    padding: 10px 0;
    border: 1px solid #EBEEF5;
    background-color: white;
  }

  .search_name{
    width: 400px;
  }

  /deep/ .el-input__inner {
    background-color: #f5f7fa;
  }

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

  .el-form-item {
    margin: 0 0;
  }

  /* 卡片css */
  .text-name {
    color: #2d73d4;
    font-weight: 550;
    font-size: 17px;
    margin-top: 10px;
    margin-bottom: 10px;
    overflow:hidden;
    white-space: nowrap;
    text-overflow:ellipsis;
  }

  .text-item {
    font-size: 14px;
    margin-bottom: 10px;
    text-overflow:ellipsis;
    /*display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;*/
    overflow: hidden;
    white-space: nowrap;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 100%;
    padding-bottom: 10px;
  }

  .el-col {
    margin-bottom: 8px;
  }

  .el-divider--horizontal {
    margin: 12px 0;
  }

  .project-box {
    margin-top: 8px;
  }

  .card-bottom {
    height: 16px;
    display: flex;
    align-items: center;
  }

  .choose-button {
    font-size: 12px;
    height: 25px;
  }

  .choose {
    background-color: #fafbfc;
  }

  .card-icon {
    font-size: 14px;
    color: #888B90;
    margin-left: 10px;
    float: right;
  }

  /* 项目详情css */
  .detail-item {
    font-size: 14px;
    margin-bottom: 10px;
    overflow: hidden;
  }

  .download {
    float: right;
  }

  .resourceTree {
    margin-top: 30px;
  }

  /* 搜索框区域css 一横条*/
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
  .divider-demo {
    height: 2em;
    margin-left: 40px;
  }
</style>
