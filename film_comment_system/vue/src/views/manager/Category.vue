<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name"
                placeholder="请输入名称查询"
                style="width: 300px; margin-right: 10px;"
      />
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="info" @click="handleAdd" style="margin-bottom: 10px;">新增分类</el-button>

      <!--   公告列表   -->
      <el-table :data="data.tableData" stripe style="width: 100%">
        <!-- prop值与后端接口变量名一致   -->
        <el-table-column prop="name" label="名称" />
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handUpdata(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deletecategory(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <!--  current-page、pageSize、current-change  都是组件自带的属性,
      查询链接：https://element-plus.org/zh-CN/component/pagination.html，
      :是单向绑定 ，@是绑定事件v-on-->
      <el-pagination background layout="total, prev, pager, next"
                     @current-change="load"
                     v-model:current-page="data.pageNum"
                     v-model:pageSize="data.pageSize"
                     :total="data.total"
      />
    </div>

    <!--  添加公告弹窗  -->
    <el-dialog v-model="data.addFormVisible" name="添加分类" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right:40px">
        <el-form-item label="分类名：" >
          <el-input type="textarea" v-model="data.form.name" autocomplete="off" placeholder="请输入分类名"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addcategory">保 存</el-button>
        </div>
      </template>
    </el-dialog>

    <!--  更新公告弹窗  -->
    <el-dialog v-model="data.updateFormVisible" name="更新分类" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right:40px">
        <el-form-item label="分类名：" >
          <el-input type="textarea" v-model="data.form.name" autocomplete="off" placeholder="请输入分类名"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.updateFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updatecategory">更 新</el-button>
        </div>
      </template>
    </el-dialog>
  </div>


</template>


<script lang="ts" setup>
//数据渲染
import request from "@/utils/request";
import {reactive}  from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
// import request from "../../utils/request";

console.log('Request Module:', request)

//把data转换成reactive，页面刷新的时候数据也会自动刷新
const data=reactive({
  tableData:[],
  total:0,
  pageNum:1,  //页码
  pageSize:10, //页面大小
  name:null, //模糊查询条件
  addFormVisible:false,  //添加公告的显示和隐藏,默认不显示
  updateFormVisible:false,  //添加公告的显示和隐藏,默认不显示
  form: {
    name: '',
  },  //from对象
})

//获取当前时间函数
function getCurrentTime() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

//创建函数load
const load=()=>{
  request.get('/category/selectPage', {
    params: { //参数
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      name:data.name,
    }
  }).then(res=>{
    console.log(res)
    //绑定数据
    data.tableData=res.data.list
    data.total=res.data.total
  })
}

//页面加载时调用load
load()

//重置查询
const reset=()=>{
  data.name=null
  load()
}

//添加公告弹窗显示
const handleAdd=()=>{
  //初始化新增数据
  data.form={ name: ''}
  //显示弹窗
  data.addFormVisible=true
}

//更新公告弹窗显示
const handUpdata=(row)=>{
  console.log("当前行数据：", row);

  request.get('/category/selectById/' + row.id)
      .then(res => {
        if (res.code === '200') {
          data.form = res.data; // 回显表单
          data.updateFormVisible = true; // 显示弹窗
        } else {
          ElMessage.error('获取公告信息失败');
        }
      })
      .catch(err => {
        console.error(err);
        ElMessage.error('网络异常');
      });
}

//添加公告
const addcategory = () => {
  request.post('/category/insert', data.form)
      .then(res => {
        if (res.code === '200') {
          data.addFormVisible = false;
          load(); // 刷新列表
          ElMessage.success('操作成功');
        } else {
          ElMessage.error(res.msg || '保存失败');
        }
      })
      .catch(err => {
        ElMessage.error('网络异常，请重试');
        console.error(err);
      });
}

//更新公告
const updatecategory = () => {

  request.post('/category/updateById', data.form)
      .then(res => {
        console.log('更新响应：', res); // 打印看看返回结构

        if (res.code === '200' || res.success) {
          // 关闭弹窗
          data.updateFormVisible = false;

          // 刷新表格
          load();

          // 提示用户
          ElMessage.success('更新成功');
        } else {
          ElMessage.error(res.msg || '更新失败');
        }
      })
      .catch(err => {
        console.error('请求异常：', err);
        ElMessage.error('网络异常，请重试');
      });
};

// 删除公告
const deletecategory = (row) => {
  // 弹出确认框
  ElMessageBox.confirm('确定要删除该分类吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 用户点击【确定】后执行删除请求
    request.delete('/category/deleteById/' + row.id)
        .then(res => {
          if (res.code === '200' || res.success) {
            // 刷新表格数据
            load();
            // 提示用户删除成功
            ElMessage.success('删除成功');
          } else {
            ElMessage.error(res.msg || '删除失败');
          }
        })
        .catch(err => {
          console.error('请求异常：', err);
          ElMessage.error('网络异常，请重试');
        });
  }).catch(() => {
    // 用户点击【取消】或关闭弹窗
    ElMessage.info('已取消删除');
  });
};

</script>