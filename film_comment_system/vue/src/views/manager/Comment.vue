<template>
  <div class="comment-container">
    <!-- 查询区域 -->
    <el-card class="filter-card" shadow="hover">
      <el-row :gutter="10">
        <el-col :span="8">
          <el-input v-model="data.filmName" placeholder="请输入电影名查询" clearable />
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="load">查询</el-button>
          <el-button @click="reset">重置</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 操作按钮与表格 -->
    <el-card class="table-card" shadow="never">
      <!-- 新增评论按钮已注释 -->
      <!-- <el-button type="info" @click="handleAdd" icon="plus">新增评论</el-button> -->

      <!-- 表格 -->
      <el-table :data="data.tableData" stripe style="width: 100%; margin-top: 15px;">
        <el-table-column prop="filmName" label="电影名称" />
        <el-table-column prop="score" label="评分">
          <template #default="scope">
            <el-rate disabled v-model="scope.row.score" allow-half />
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评论">
          <template #default="scope">
            <el-button link @click="preview(scope.row.comment)">查看内容</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="用户名称" />
        <el-table-column prop="time" label="评论时间" />
        <el-table-column prop="type" label="类型">
          <template #default="scope">
            <el-tag type="primary" v-if="scope.row.type === '短评'">短评</el-tag>
            <el-tag type="success" v-if="scope.row.type === '长评'">长评</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button size="small" type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <el-pagination
            background
            layout="total, prev, pager, next"
            @current-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :total="data.total"
        />
      </div>
    </el-card>

    <!-- 新增用户弹窗已注释 -->
    <!--
    <el-dialog v-model="data.addFormVisible" title="添加评论" width="40%" center>
      <el-form :model="data.form" label-width="80px" :rules="rules" ref="formRef">
        <el-form-item label="标题" prop="title">
          <el-input v-model="data.form.title" autocomplete="off" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="data.form.content" type="textarea" :rows="4" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addcomment">保 存</el-button>
        </div>
      </template>
    </el-dialog>
    -->

    <!-- 查看评论内容弹窗 -->
    <el-dialog v-model="data.formVisibleComment" title="评论内容" width="40%">
      <div v-html="data.commentContent" class="longComment"></div>
      <template #footer>
        <el-button @click="data.formVisibleComment = false">关 闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import request from '@/utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const data = reactive({
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  title: null,
  filmName: null,     // 用于传递给后端模糊查询
  userName: null,     // 支持用户名搜索
  addFormVisible: false,
  formVisibleComment: false,
  commentContent: '',
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  form: {
    title: '',
    content: '',
    time: ''
  }
})

// 获取当前时间函数
function getCurrentTime() {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  const seconds = String(now.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

// 加载数据
const load = () => {
  request.get('/comment/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title,
      filmName: data.filmName,
      userName: data.userName,
      userId: data.user.role === 'ADMIN' ? null : data.user.id
    }
  }).then(res => {
    data.tableData = res.data.list
    data.total = res.data.total
  })
}

// 重置查询
const reset = () => {
  data.title = null
  data.filmName = null
  data.userName = null
  load()
}

// 打开新增弹窗（已注释）
// const handleAdd = () => {
//   data.form = { title: '', content: '', time: '' }
//   data.addFormVisible = true
// }

// 添加用户（已注释）
// const addcomment = () => {
//   data.form.time = getCurrentTime()
//   request.post('/comment/insert', data.form).then(res => {
//     if (res.code === '200') {
//       data.addFormVisible = false
//       load()
//       ElMessage.success('操作成功')
//     } else {
//       ElMessage.error(res.msg || '保存失败')
//     }
//   }).catch(() => {
//     ElMessage.error('网络异常，请重试')
//   })
// }

// 查看评论
const preview = content => {
  data.commentContent = content
  data.formVisibleComment = true
}

// 删除用户
const del = id => {
  ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    request.delete(`/comment/deleteById/${id}`).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('删除成功')
      } else {
        ElMessage.error(res.msg || '删除失败')
      }
    }).catch(() => {
      ElMessage.error('网络异常，请重试')
    })
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 初始化加载
load()
</script>

<style scoped>
.comment-container {
  padding: 20px;
}

.filter-card {
  margin-bottom: 15px;
}

.table-card {
  padding: 10px;
}

.pagination-wrapper {
  margin-top: 15px;
  display: flex;
  justify-content: center;
}

 .longComment img{
   width: 100%;
 }

</style>