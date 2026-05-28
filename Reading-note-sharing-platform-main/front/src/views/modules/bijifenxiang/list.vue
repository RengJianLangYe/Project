<template>
  <div class="main-content">
    <!-- 列表页 -->
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row :gutter="20" class="slt" :style="{justifyContent:contents.searchBoxPosition=='1'?'flex-start':contents.searchBoxPosition=='2'?'center':'flex-end'}">
                <el-form-item :label="contents.inputTitle == 1 ? '笔记名称' : ''">
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 1" prefix-icon="el-icon-search" v-model="searchForm.bijimingcheng" placeholder="笔记名称" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 2" suffix-icon="el-icon-search" v-model="searchForm.bijimingcheng" placeholder="笔记名称" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 0" v-model="searchForm.bijimingcheng" placeholder="笔记名称" clearable></el-input>
                </el-form-item>
                <el-form-item :label="contents.inputTitle == 1 ? '笔记类型' : ''">
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 1" prefix-icon="el-icon-search" v-model="searchForm.bijileixing" placeholder="笔记类型" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 2" suffix-icon="el-icon-search" v-model="searchForm.bijileixing" placeholder="笔记类型" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 0" v-model="searchForm.bijileixing" placeholder="笔记类型" clearable></el-input>
                </el-form-item>
                <el-form-item :label="contents.inputTitle == 1 ? '笔记标签' : ''">
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 1" prefix-icon="el-icon-search" v-model="searchForm.bijibiaoqian" placeholder="笔记标签" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 2" suffix-icon="el-icon-search" v-model="searchForm.bijibiaoqian" placeholder="笔记标签" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 0" v-model="searchForm.bijibiaoqian" placeholder="笔记标签" clearable></el-input>
                </el-form-item>
          <el-form-item>
            <el-button v-if="contents.searchBtnIcon == 1 && contents.searchBtnIconPosition == 1" icon="el-icon-search" type="success" @click="search()">{{ contents.searchBtnFont == 1?'查询':'' }}</el-button>
            <el-button v-if="contents.searchBtnIcon == 1 && contents.searchBtnIconPosition == 2" type="success" @click="search()">{{ contents.searchBtnFont == 1?'查询':'' }}<i class="el-icon-search el-icon--right"/></el-button>
            <el-button v-if="contents.searchBtnIcon == 0" type="success" @click="search()">{{ contents.searchBtnFont == 1?'查询':'' }}</el-button>
          </el-form-item>
        </el-row>
        <el-row class="ad" :style="{justifyContent:contents.btnAdAllBoxPosition=='1'?'flex-start':contents.btnAdAllBoxPosition=='2'?'center':'flex-end'}">
          <el-form-item>
            <el-button
              v-if="isAuth('bijifenxiang','新增') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 1"
              type="success"
              icon="el-icon-plus"
              @click="addOrUpdateHandler()"
            >{{ contents.btnAdAllFont == 1?'新增':'' }}</el-button>
            <el-button
              v-if="isAuth('bijifenxiang','新增') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 2"
              type="success"
              @click="addOrUpdateHandler()"
            >{{ contents.btnAdAllFont == 1?'新增':'' }}<i class="el-icon-plus el-icon--right" /></el-button>
            <el-button
              v-if="isAuth('bijifenxiang','新增') && contents.btnAdAllIcon == 0"
              type="success"
              @click="addOrUpdateHandler()"
            >{{ contents.btnAdAllFont == 1?'新增':'' }}</el-button>
            <el-button
              v-if="isAuth('bijifenxiang','删除') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 1 && contents.tableSelection"
              :disabled="dataListSelections.length <= 0"
              type="danger"
              icon="el-icon-delete"
              @click="deleteHandler()"
            >{{ contents.btnAdAllFont == 1?'删除':'' }}</el-button>
            <el-button
              v-if="isAuth('bijifenxiang','删除') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 2 && contents.tableSelection"
              :disabled="dataListSelections.length <= 0"
              type="danger"
              @click="deleteHandler()"
            >{{ contents.btnAdAllFont == 1?'删除':'' }}<i class="el-icon-delete el-icon--right" /></el-button>
            <el-button
              v-if="isAuth('bijifenxiang','删除') && contents.btnAdAllIcon == 0 && contents.tableSelection"
              :disabled="dataListSelections.length <= 0"
              type="danger"
              @click="deleteHandler()"
            >{{ contents.btnAdAllFont == 1?'删除':'' }}</el-button>





          </el-form-item>
        </el-row>
      </el-form>
      <div class="table-content">
        <el-table class="tables" :size="contents.tableSize" :show-header="contents.tableShowHeader"
            :header-row-style="headerRowStyle" :header-cell-style="headerCellStyle"
            :border="contents.tableBorder"
            :fit="contents.tableFit"
            :stripe="contents.tableStripe"
            :style="{width: '100%',fontSize:contents.tableContentFontSize,color:contents.tableContentFontColor}"
            v-if="isAuth('bijifenxiang','查看')"
            :data="dataList"
            v-loading="dataListLoading"
            @selection-change="selectionChangeHandler">
            <el-table-column  v-if="contents.tableSelection"
                type="selection"
                :header-align="contents.tableAlign"
                align="center"
                width="50">
            </el-table-column>
            <el-table-column label="索引" :align="contents.tableAlign"  v-if="contents.tableIndex" type="index" width="50" />
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="bijimingcheng"
                   :header-align="contents.tableAlign"
		    label="笔记名称">
		     <template slot-scope="scope">
                       {{scope.row.bijimingcheng}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="bijileixing"
                   :header-align="contents.tableAlign"
		    label="笔记类型">
		     <template slot-scope="scope">
                       {{scope.row.bijileixing}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="bijibiaoqian"
                   :header-align="contents.tableAlign"
		    label="笔记标签">
		     <template slot-scope="scope">
                       {{scope.row.bijibiaoqian}}
                     </template>
                </el-table-column>
                  <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign"  prop="bijitupian"
                   :header-align="contents.tableAlign"
                    width="200"
                    label="笔记图片">
                    <template slot-scope="scope">
                      <div v-if="scope.row.bijitupian">
                        <img :src="$base.url+scope.row.bijitupian.split(',')[0]" width="100" height="100">
                      </div>
                      <div v-else>无图片</div>
                    </template>
                  </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="fenxiangriqi"
                   :header-align="contents.tableAlign"
		    label="笔记日期">
		     <template slot-scope="scope">
                       {{scope.row.fenxiangriqi}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="yonghuming"
                   :header-align="contents.tableAlign"
		    label="用户名">
		     <template slot-scope="scope">
                       {{scope.row.yonghuming}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="youxiang"
                   :header-align="contents.tableAlign"
		    label="邮箱">
		     <template slot-scope="scope">
                       {{scope.row.youxiang}}
                     </template>
                </el-table-column>
              <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" 
                  prop="shhf"
                 :header-align="contents.tableAlign"
                  label="审核回复">
              </el-table-column>
              <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" 
                  prop="sfsh"
                 :header-align="contents.tableAlign"
                  label="审核状态">
                  <template slot-scope="scope">
                    <span style="margin-right:10px">{{scope.row.sfsh=='是'?'通过':'未通过'}}</span>
                  </template>
              </el-table-column>

              <!-- 编辑状态列 -->
              <!-- <el-table-column label="编辑状态" width="150" align="center">
                  <template slot-scope="scope">
                      <el-tag v-if="scope.row.lockStatus && scope.row.lockStatus.isLocked" type="danger" size="small">
                          🔒 {{ scope.row.lockStatus.editorName || scope.row.lockStatus.editorId }} 正在编辑
                      </el-tag>
                      <el-tag v-else type="success" size="small">
                          🔓 可编辑
                      </el-tag>
                  </template>
              </el-table-column> -->

              <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" 
                  v-if="isAuth('bijifenxiang','审核')"
                  prop="sfsh"
                 :header-align="contents.tableAlign"
                  label="审核">
                  <template slot-scope="scope">
                    <el-button  type="text" icon="el-icon-edit" size="small" @click="shDialog(scope.row)">审核</el-button>
                  </template>
              </el-table-column>
              <el-table-column width="600" :align="contents.tableAlign" 
   :header-align="contents.tableAlign"
    label="操作">
    <template slot-scope="scope">
        <!-- 详情按钮 -->
        <el-button type="success" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">详情</el-button>
        
        <!-- 修改按钮：主人 或 已批准的协同成员 -->
        <el-button 
    v-if="scope.row.userid == currentUserId || (scope.row.editMode == 'group' && isApprovedMember(scope.row))" 
    type="primary" size="mini" 
    :disabled="scope.row.lockStatus && scope.row.lockStatus.isLocked && scope.row.lockStatus.editorId != currentUserId"
    @click="addOrUpdateHandler(scope.row.id)">
    {{ (scope.row.lockStatus && scope.row.lockStatus.isLocked && scope.row.lockStatus.editorId != currentUserId) ? '编辑中...' : '修改' }}
</el-button>

        <!-- 查看评论按钮 -->
        <el-button type="primary" size="mini" @click="disscussListHandler(scope.row.id)">查看评论</el-button>
        
        <!-- 删除按钮（仅主人） -->
        <el-button v-if="scope.row.userid == currentUserId" type="danger" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>
        
        <!-- 协同按钮 -->
        <el-button v-if="scope.row.userid == currentUserId && scope.row.editMode != 'group'" type="warning" size="mini" @click="openCowork(scope.row)">开启协同</el-button>
        <el-button v-if="scope.row.userid == currentUserId && scope.row.editMode == 'group'" type="danger" size="mini" @click="closeCowork(scope.row)">关闭协同</el-button>
        <el-button v-if="scope.row.userid == currentUserId && scope.row.editMode == 'group'" type="info" size="mini" @click="showApplyList(scope.row)">申请列表</el-button>
    </template>
</el-table-column>
        </el-table>
        <el-pagination
          clsss="pages"
          :layout="layouts"
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="Number(contents.pageEachNum)"
          :total="totalPage"
          :small="contents.pageStyle"
          class="pagination-content"
          :background="contents.pageBtnBG"
          :style="{textAlign:contents.pagePosition==1?'left':contents.pagePosition==2?'center':'right'}"
        ></el-pagination>
      </div>
    </div>
    <!-- 添加/修改页面  将父组件的search方法传递给子组件-->
    <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>


    <el-dialog
      title="审核"
      :visible.sync="sfshVisiable"
      width="50%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="审核状态">
          <el-select v-model="shForm.sfsh" placeholder="审核状态">
            <el-option label="通过" value="是"></el-option>
            <el-option label="不通过" value="否"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" :rows="8" v-model="shForm.shhf"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="shDialog">取 消</el-button>
        <el-button type="primary" @click="shHandler">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 申请列表弹窗 -->
<!-- 申请列表弹窗 -->
<el-dialog :title="currentNoteTitle + ' - 协同申请列表'" :visible.sync="applyDialogVisible" width="500px">
  <el-table :data="applyList" border stripe>
    <el-table-column prop="userName" label="申请人" width="150"></el-table-column>
    <el-table-column prop="applied_at" label="申请时间" width="180">
      <template slot-scope="scope">
        {{ scope.row.applied_at }}
      </template>
    </el-table-column>
    <el-table-column label="操作" width="150">
      <template slot-scope="scope">
        <el-button type="success" size="small" @click="approveApply(scope.row)">批准</el-button>
        <el-button type="danger" size="small" @click="rejectApply(scope.row)">拒绝</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div v-if="applyList.length == 0" style="text-align:center; padding: 30px;">
    暂无申请
  </div>
  <span slot="footer" class="dialog-footer">
    <el-button @click="applyDialogVisible = false">关 闭</el-button>
  </span>
</el-dialog>



  </div>
</template>
<script>
import axios from 'axios'
import AddOrUpdate from "./add-or-update";
export default {
  data() {
    return {
      searchForm: {
        key: ""
      },
      form:{},
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      sfshVisiable: false,
      shForm: {},
      chartVisiable: false,
      addOrUpdateFlag:false,
      contents:{"searchBtnFontColor":"rgba(255, 255, 255, 1)","pagePosition":"1","inputFontSize":"14px","inputBorderRadius":"0px","tableBtnDelFontColor":"rgba(220, 53, 69, 1)","tableBtnIconPosition":"1","searchBtnHeight":"36px","tableBgColor":"#f5f5f5","inputIconColor":"#C0C4CC","searchBtnBorderRadius":"4px","tableStripe":false,"btnAdAllWarnFontColor":"rgba(40, 167, 69, 1)","tableBtnDelBgColor":"#fff","searchBtnIcon":"0","tableSize":"mini","searchBtnBorderStyle":"solid","tableSelection":true,"text":{"padding":"0","boxShadow":"0 0 6px rgba(0,0,0,.1)","margin":"0 auto","borderColor":"rgba(0,0,0,.3)","backgroundColor":"#f7f7f7","color":"#333","borderRadius":"6px","borderWidth":"0","width":"auto","lineHeight":"64px","fontSize":"24px","borderStyle":"solid"},"searchBtnBorderWidth":"0px","tableContentFontSize":"14px","searchBtnBgColor":"rgba(0, 150, 136, 1)","inputTitleSize":"14px","btnAdAllBorderColor":"rgba(42, 53, 66, 1)","pageJumper":true,"btnAdAllIconPosition":"1","searchBoxPosition":"1","tableBtnDetailFontColor":"rgba(40, 167, 69, 1)","tableBtnHeight":"36px","pagePager":true,"tableHeaderFontColor":"#909399","inputTitle":"0","tableBtnBorderRadius":"0px","btnAdAllFont":"1","btnAdAllDelFontColor":"rgba(220, 53, 69, 1)","tableBtnIcon":"1","btnAdAllHeight":"40px","btnAdAllWarnBgColor":"rgba(255, 255, 255, 1)","btnAdAllBorderWidth":"0px","tableStripeFontColor":"#606266","tableBtnBorderStyle":"solid","inputHeight":"36px","btnAdAllBorderRadius":"2px","btnAdAllDelBgColor":"rgba(255, 255, 255, 1)","pagePrevNext":true,"btnAdAllAddBgColor":"rgba(255, 255, 255, 0.28)","searchBtnFont":"1","tableIndex":true,"btnAdAllIcon":"1","tableSortable":true,"pageSizes":true,"tableFit":true,"pageBtnBG":true,"searchBtnFontSize":"14px","tableBtnEditBgColor":"#fff","inputBorderWidth":"1px","box":{"padding":"10px 20px","boxShadow":"0 0 6px rgba(0,0,0,0)","flag":1,"backgroundImage":"","background":"#fff"},"inputFontPosition":"1","inputFontColor":"#333","pageEachNum":10,"tableHeaderBgColor":"#fff","inputTitleColor":"#333","btnAdAllBoxPosition":"1","tableBtnDetailBgColor":"rgba(255, 255, 255, 1)","inputIcon":"1","searchBtnIconPosition":"1","btnAdAllFontSize":"14px","inputBorderStyle":"none none solid none","tableHoverFontColor":"#333","inputBgColor":"#fff","pageStyle":true,"pageTotal":true,"btnAdAllAddFontColor":"rgba(0, 123, 255, 1)","tableBtnFont":"1","tableContentFontColor":"#606266","inputBorderColor":"#DCDFE6","tableShowHeader":true,"tableHoverBgColor":"#f5f5f5","tableBtnFontSize":"10px","tableBtnBorderColor":"#DCDFE6","inputIconPosition":"2","tableBorder":true,"btnAdAllBorderStyle":"solid","tableBtnBorderWidth":"0px","tableStripeBgColor":"#F5F7FA","tableBtnEditFontColor":"rgba(255, 193, 7, 1)","tableAlign":"center"},
      layouts: '',
      // ===== 协同编辑变量 =====
      currentUserId: '',     // 新增：当前登录用户的ID
      currentUsername: '',
      applyDialogVisible: false,
      applyList: [],
      currentNoteId: null,
      currentNoteTitle: '',


    };
  },
  created() {
  // 获取 userid
  const userId = localStorage.getItem('userid');
  this.currentUserId = userId;
  
  if (userId) {
    // 根据 userid 查询用户名（用于显示）
    this.$http({
      url: "yonghu/page",
      method: "get",
      params: { userid: userId, limit: 1 }
    }).then(({ data }) => {
      if (data && data.code === 0 && data.data.list.length > 0) {
        this.currentUsername = data.data.list[0].yonghuming;
        console.log('获取到的用户名:', this.currentUsername);
        console.log('当前用户ID:', this.currentUserId);
      }
    });
  }
  
  this.init();
  this.getDataList();
  this.contentStyleChange()
},
  mounted() {

  },
  filters: {
    htmlfilter: function (val) {
      return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
    }
  },
  components: {
    AddOrUpdate,
  },
  methods: {
    // 判断当前用户是否是已批准的协同成员
// 判断当前用户是否是已批准的协同成员
async isApprovedMember(note) {
    // 如果没有登录或者是笔记主人，返回false
    if (!this.currentUserId || note.userid == this.currentUserId) {
        return false;
    }
    // 只有开启了协同的笔记才可能有协同成员
    if (note.editMode != 'group') {
        return false;
    }
    try {
        const res = await this.$http({
            url: "collab/checkMember",
            method: "get",
            params: { noteId: note.id, userId: this.currentUserId }
        });
        return res.data && res.data.code === 0 && res.data.isMember === true;
    } catch (error) {
        console.error('检查协同成员失败:', error);
        return false;
    }
},
    // ========== 协同编辑方法 ==========

// 开启协同
openCowork(row) {
  this.$confirm(`确定开启笔记"${row.bijimingcheng}"的协同编辑功能吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "info"
  }).then(() => {
    this.$http({
      url: "bijifenxiang/update",
      method: "post",
      data: { id: row.id, editMode: "group" }
    }).then(({ data }) => {
      if (data && data.code === 0) {
        this.$message.success("协同编辑已开启");
        this.getDataList();
        this.createCollabGroup(row.id, row.yonghuming);
      } else {
        this.$message.error(data.msg);
      }
    });
  });
},

// 创建协同小组
createCollabGroup(noteId, userId) {
  this.$http({
    url: "collab/createGroup",
    method: "post",
    data: { noteId: noteId, userId: userId }
  }).then(({ data }) => {
    if (data.code !== 200) {
      console.log("创建小组失败:", data.msg);
    }
  });
},

// 关闭协同
closeCowork(row) {
  this.$confirm(`确定关闭笔记"${row.bijimingcheng}"的协同编辑功能吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    this.$http({
      url: "bijifenxiang/update",
      method: "post",
      data: { id: row.id, editMode: "private" }
    }).then(({ data }) => {
      if (data && data.code === 0) {
        this.$message.success("协同编辑已关闭");
        this.getDataList();
      } else {
        this.$message.error(data.msg);
      }
    });
  });
},

showApplyList(row) {
  console.log('点击申请列表，笔记ID:', row.id);
  this.currentNoteId = row.id;
  this.currentNoteTitle = row.bijimingcheng;
  this.getApplyList();
  this.applyDialogVisible = true;
},

// 获取申请列表
getApplyList() {
  this.$http({
    url: "collab/pending-list",
    method: "get",
    params: { noteId: this.currentNoteId }
  }).then(({ data }) => {
    console.log('申请列表返回:', data);
    if (data.code === 0) {
      // 获取每个申请人的用户名
      const list = data.data || [];
      const promises = list.map(item => {
        return this.$http({
          url: "yonghu/page",
          method: "get",
          params: { userid: item.user_id, limit: 1 }
        }).then(res => {
          if (res.data && res.data.code === 0 && res.data.data.list.length > 0) {
            item.userName = res.data.data.list[0].yonghuming;
          } else {
            item.userName = item.user_id;
          }
          return item;
        });
      });
      
      Promise.all(promises).then(results => {
        this.applyList = results.map(item => ({
          id: item.id,
          userName: item.userName,
          user_id: item.user_id,
          applied_at: item.applied_at || item.appliedAt,
          status: item.status
        }));
        console.log('处理后的申请列表:', this.applyList);
      });
    } else {
      this.applyList = [];
    }
  }).catch(err => {
    console.log('请求失败:', err);
    this.applyList = [];
  });
},

// 批准申请
approveApply(application) {
  this.$confirm(`确定批准用户"${application.userId}"加入协同编辑吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "info"
  }).then(() => {
    this.$http({
      url: "collab/review",
      method: "post",
      data: { applicationId: application.id, action: "approve" }
    }).then(({ data }) => {
      if (data.code === 200) {
        this.$message.success("已批准");
        this.getApplyList();
      } else {
        this.$message.error(data.msg);
      }
    });
  });
},

// 拒绝申请
rejectApply(application) {
  this.$confirm(`确定拒绝用户"${application.userId}"的申请吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    this.$http({
      url: "collab/review",
      method: "post",
      data: { applicationId: application.id, action: "reject" }
    }).then(({ data }) => {
      if (data.code === 200) {
        this.$message.success("已拒绝");
        this.getApplyList();
      } else {
        this.$message.error(data.msg);
      }
    });
  });
},
    contentStyleChange() {
      this.contentSearchStyleChange()
      this.contentBtnAdAllStyleChange()
      this.contentSearchBtnStyleChange()
      this.contentTableBtnStyleChange()
      this.contentPageStyleChange()
    },
    contentSearchStyleChange() {
      this.$nextTick(()=>{
        document.querySelectorAll('.form-content .slt .el-input__inner').forEach(el=>{
          let textAlign = 'left'
          if(this.contents.inputFontPosition == 2) textAlign = 'center'
          if(this.contents.inputFontPosition == 3) textAlign = 'right'
          el.style.textAlign = textAlign
          el.style.height = this.contents.inputHeight
          el.style.lineHeight = this.contents.inputHeight
          el.style.color = this.contents.inputFontColor
          el.style.fontSize = this.contents.inputFontSize
          el.style.borderWidth = this.contents.inputBorderWidth
          el.style.borderStyle = this.contents.inputBorderStyle
          el.style.borderColor = this.contents.inputBorderColor
          el.style.borderRadius = this.contents.inputBorderRadius
          el.style.backgroundColor = this.contents.inputBgColor
        })
        if(this.contents.inputTitle) {
          document.querySelectorAll('.form-content .slt .el-form-item__label').forEach(el=>{
            el.style.color = this.contents.inputTitleColor
            el.style.fontSize = this.contents.inputTitleSize
            el.style.lineHeight = this.contents.inputHeight
          })
        }
        setTimeout(()=>{
          document.querySelectorAll('.form-content .slt .el-input__prefix').forEach(el=>{
            el.style.color = this.contents.inputIconColor
            el.style.lineHeight = this.contents.inputHeight
          })
          document.querySelectorAll('.form-content .slt .el-input__suffix').forEach(el=>{
            el.style.color = this.contents.inputIconColor
            el.style.lineHeight = this.contents.inputHeight
          })
          document.querySelectorAll('.form-content .slt .el-input__icon').forEach(el=>{
            el.style.lineHeight = this.contents.inputHeight
          })
        },10)

      })
    },
    // 搜索按钮
    contentSearchBtnStyleChange() {
      this.$nextTick(()=>{
        document.querySelectorAll('.form-content .slt .el-button--success').forEach(el=>{
          el.style.height = this.contents.searchBtnHeight
          el.style.color = this.contents.searchBtnFontColor
          el.style.fontSize = this.contents.searchBtnFontSize
          el.style.borderWidth = this.contents.searchBtnBorderWidth
          el.style.borderStyle = this.contents.searchBtnBorderStyle
          el.style.borderColor = this.contents.searchBtnBorderColor
          el.style.borderRadius = this.contents.searchBtnBorderRadius
          el.style.backgroundColor = this.contents.searchBtnBgColor
        })
      })
    },
    // 新增、批量删除
    contentBtnAdAllStyleChange() {
      this.$nextTick(()=>{
        document.querySelectorAll('.form-content .ad .el-button--success').forEach(el=>{
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllAddFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllAddBgColor
        })
        document.querySelectorAll('.form-content .ad .el-button--danger').forEach(el=>{
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllDelFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllDelBgColor
        })
        document.querySelectorAll('.form-content .ad .el-button--warning').forEach(el=>{
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllWarnFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllWarnBgColor
        })
      })
    },
    // 表格
    // rowStyle({ row, rowIndex}) {
    //   if (rowIndex % 2 == 1) {
    //     if(this.contents.tableStripe) {
    //       return {color:this.contents.tableStripeFontColor}
    //     }
    //   } else {
    //     return ''
    //   }
    // },
    // cellStyle({ row, rowIndex}){
    //   if (rowIndex % 2 == 1) {
    //     if(this.contents.tableStripe) {
    //       return {backgroundColor:this.contents.tableStripeBgColor}
    //     }
    //   } else {
    //     return ''
    //   }
    // },
    headerRowStyle({ row, rowIndex}){
      return {color: this.contents.tableHeaderFontColor}
    },
    headerCellStyle({ row, rowIndex}){
      return {backgroundColor: this.contents.tableHeaderBgColor}
    },
    // 表格按钮
    contentTableBtnStyleChange(){
      // this.$nextTick(()=>{
      //   setTimeout(()=>{
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--success').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnDetailFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnDetailBgColor
      //     })
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--primary').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnEditFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnEditBgColor
      //     })
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--danger').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnDelFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnDelBgColor
      //     })

      //   }, 50)
      // })
    },
    // 分页
    contentPageStyleChange(){
      let arr = []

      if(this.contents.pageTotal) arr.push('total')
      if(this.contents.pageSizes) arr.push('sizes')
      if(this.contents.pagePrevNext){
        arr.push('prev')
        if(this.contents.pagePager) arr.push('pager')
        arr.push('next')
      }
      if(this.contents.pageJumper) arr.push('jumper')
      this.layouts = arr.join()
      this.contents.pageEachNum = 10
    },

    init () {
    },
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },

    // 获取数据列表
    // 获取数据列表
getDataList() {
  this.dataListLoading = true;
  let params = {
    page: this.pageIndex,
    limit: this.pageSize,
    sort: 'id',
  }
  // ... 搜索条件 ...
  
  this.$http({
    url: "bijifenxiang/page",
    method: "get",
    params: params
  }).then(async ({ data }) => {
    if (data && data.code === 0) {
      this.dataList = data.data.list;
      this.totalPage = data.data.total;
      
      // ===== 新增：获取每条笔记的锁状态 =====
      for (let note of this.dataList) {
        try {
          const lockRes = await this.$http({
            url: "collab/lock-status",
            method: "get",
            params: { noteId: note.id }
          });
          if (lockRes.data && lockRes.data.code === 200) {
            this.$set(note, 'lockStatus', lockRes.data.data);
          } else {
            this.$set(note, 'lockStatus', { isLocked: false });
          }
        } catch (error) {
          this.$set(note, 'lockStatus', { isLocked: false });
        }
      }
      // ===== 新增结束 =====
      
    } else {
      this.dataList = [];
      this.totalPage = 0;
    }
    this.dataListLoading = false;
  });
},

    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },
    // 添加/修改
    addOrUpdateHandler(id,type) {
      this.showFlag = false;
      this.addOrUpdateFlag = true;
      this.crossAddOrUpdateFlag = false;
      if(type!='info'){
        type = 'else';
      }
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id,type);
      });
    },
    // 查看评论
    disscussListHandler(id,type) {
	this.$router.push({path:'/discussbijifenxiang',query:{refid:id}});
    },
    // 审核窗口
    shDialog(row){
      this.sfshVisiable = !this.sfshVisiable;
      if(row){
        this.shForm = {
          bijimingcheng: row.bijimingcheng,
          bijileixing: row.bijileixing,
          bijibiaoqian: row.bijibiaoqian,
          bijitupian: row.bijitupian,
          bijineirong: row.bijineirong,
          fenxiangriqi: row.fenxiangriqi,
          yonghuming: row.yonghuming,
          youxiang: row.youxiang,
          sfsh: row.sfsh,
          shhf: row.shhf,
          thumbsupnum: row.thumbsupnum,
          crazilynum: row.crazilynum,
          id: row.id
        }
      }
    },
    // 审核
    shHandler(){
      this.$confirm(`确定操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: "bijifenxiang/update",
          method: "post",
          data: this.shForm
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
                this.shDialog()
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 下载
    download(file){
      window.open(`${file}`)
    },
    // 删除
    deleteHandler(id) {
      var ids = id
        ? [Number(id)]
        : this.dataListSelections.map(item => {
            return Number(item.id);
          });
      this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: "bijifenxiang/delete",
          method: "post",
          data: ids
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.search();
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },


  }

};
</script>
<style lang="scss" scoped>
  .slt {
    margin: 0 !important;
    display: flex;
  }

  .ad {
    margin: 0 !important;
    display: flex;
  }

  .pages {
    & ::v-deep el-pagination__sizes{
      & ::v-deep el-input__inner {
        height: 22px;
        line-height: 22px;
      }
    }
  }
  

  .el-button+.el-button {
    margin:0;
  } 

  .tables {
	& ::v-deep .el-button--success {
		height: 36px;
		color: rgba(40, 167, 69, 1);
		font-size: 10px;
		border-width: 0px;
		border-style: solid;
		border-color: #DCDFE6;
		border-radius: 0px;
		background-color: rgba(255, 255, 255, 1);
	}
	
	& ::v-deep .el-button--primary {
		height: 36px;
		color: rgba(255, 193, 7, 1);
		font-size: 10px;
		border-width: 0px;
		border-style: solid;
		border-color: #DCDFE6;
		border-radius: 0px;
		background-color: #fff;
	}
	
	& ::v-deep .el-button--danger {
		height: 36px;
		color: rgba(220, 53, 69, 1);
		font-size: 10px;
		border-width: 0px;
		border-style: solid;
		border-color: #DCDFE6;
		border-radius: 0px;
		background-color: #fff;
	}

    & ::v-deep .el-button {
      margin: 4px;
    }
  }
	.form-content {
		background: transparent;
	}
	.table-content {
		background: transparent;
	}
	
	.tables ::v-deep .el-table__body tr {
				background-color: #f5f5f5 !important;
				color: #606266 !important;
	 }
	.tables ::v-deep .el-table__body tr.el-table__row--striped td {
	    background: transparent;
	}
	.tables ::v-deep .el-table__body tr.el-table__row--striped {
		background-color: #F5F7FA !important;
		color: #606266 !important;
	}
	
	 .tables ::v-deep .el-table__body tr:hover>td {
	   	   background-color: #f5f5f5 !important;
	   	   	   color: #333 !important;
	   	 }
	 
</style>
