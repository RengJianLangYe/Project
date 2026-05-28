<template>
  <div>
    <!-- 查询条件 -->
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name"
                placeholder="请输入名称查询"
                style="width: 300px; margin-right: 10px;"
      />
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <!-- 列表与操作按钮 -->
    <div class="card" style="margin-bottom: 5px">
      <el-button type="info" @click="handleAdd" style="margin-bottom: 10px;">新增分类</el-button>

      <!-- 表格 -->
      <el-table :data="data.tableData" stripe style="width: 100%">
        <el-table-column prop="name" label="名称"  width="150"/>
        <el-table-column prop="cover" label="封面" width="200">
          <template #default="scope">
            <el-image :src="scope.row.cover" style="width: 150px; height: 202px; border-radius: 5px"
                      :preview-src-list="[scope.row.cover]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column label="描述" width="300">
          <template #default="scope">
            <div class="description-cell">
              <span v-if="!scope.row.isExpanded">{{ scope.row.descr.slice(0, 50) }}...</span>
              <span v-else>{{ scope.row.descr }}</span>
              <el-button size="small" type="text" @click="toggleDescription(scope.row)">
                {{ scope.row.isExpanded ? '折叠' : '展开' }}
              </el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="year" label="年份" />
        <el-table-column prop="director" label="导演" />
        <el-table-column prop="actors" label="演员" width="200">
          <template #default="scope">
            <span v-if="!scope.row.actorsExpanded">{{ scope.row.actors.slice(0, 50) }}...</span>
            <span v-else>{{ scope.row.actors }}</span>
            <el-button size="small" type="text" @click="toggleActors(scope.row)">
              {{ scope.row.actorsExpanded ? '折叠' : '展开' }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类" />
        <el-table-column prop="country" label="国家" />
        <el-table-column prop="language" label="语言" />
        <el-table-column prop="date" label="上映日期" />
        <el-table-column prop="duration" label="片长" />
        <el-table-column prop="imdb" label="IMDb" />

        <!-- 操作列 -->
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handUpdate(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deletefilm(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="card">
      <el-pagination
          background
          layout="total, prev, pager, next"
          @current-change="load"
          v-model:current-page="data.pageNum"
          v-model:pageSize="data.pageSize"
          :total="data.total"
      />
    </div>

    <!-- 新增电影弹窗 -->
    <el-dialog v-model="data.addFormVisible" title="添加电影信息" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right:40px">
        <el-form-item label="电影名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入电影名"/>
        </el-form-item>

        <el-form-item label="封面" prop="cover">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>

        <!-- 描述 -->
        <el-form-item label="描述">
          <el-input type="textarea" v-model="data.form.descr" placeholder="请输入描述"/>
        </el-form-item>

        <el-form-item label="年份">
            <el-input type="text" v-model="data.form.year" placeholder="请输入年份"/>
        </el-form-item>

        <el-form-item label="导演">
          <el-input type="text" v-model="data.form.director" placeholder="请输入导演"/>
        </el-form-item>

        <el-form-item label="演员">
          <el-input type="textarea" v-model="data.form.actors" placeholder="请输入演员"/>
        </el-form-item>

        <el-form-item label="分类">
          <el-input type="text" v-model="data.form.categoryName" placeholder="请输入分类"/>
        </el-form-item>

        <el-form-item label="国家">
          <el-input type="text" v-model="data.form.country" placeholder="请输入国家"/>
        </el-form-item>

        <el-form-item label="语言">
          <el-input type="text" v-model="data.form.language" placeholder="请输入语言"/>
        </el-form-item>

        <el-form-item label="上映日期">
<!--          <el-input type="text" v-model="data.form.date" placeholder="请输入上映日期"/>-->
          <el-date-picker
              style="width: 100%"
              v-model="data.form.date"
              type="date"
              placeholder="选择日期"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="片长">
          <el-input type="text" v-model="data.form.duration" placeholder="请输入片长"/>
        </el-form-item>

        <el-form-item label="IMDb">
          <el-input type="text" v-model="data.form.imdb" placeholder="请输入 IMDb"/>
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addfilm">保 存</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 编辑电影弹窗 -->
    <el-dialog v-model="data.updateFormVisible" title="编辑电影信息" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right:40px">
        <el-form-item label="电影名" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入电影名"/>
        </el-form-item>

        <el-form-item label="封面" prop="cover">
          <div v-if="data.form.cover" style="border: 2px; margin-right: 10px">
            <img :src="data.form.cover" alt="封面图片" style="max-width: 150px; max-height: 200px; border-radius: 5px;" />
          </div>
          <el-upload
              :action="uploadUrl"
              list-type="picture"
              :on-success="handleImgSuccess"
              :file-list="[{
                name: 'currentCover',
                url: data.form.cover
              }]"
          >
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>

        <el-form-item label="描述">
          <el-input type="textarea" v-model="data.form.descr" placeholder="请输入描述"/>
        </el-form-item>

        <el-form-item label="年份">
          <el-input type="text" v-model="data.form.year" placeholder="请输入年份"/>
<!--          <el-date-picker-->
<!--              v-model="data.form.year"-->
<!--              type="date"-->
<!--              placeholder="选择日期"-->
<!--              format="YYYY/MM/DD"-->
<!--              value-format="YYYY-MM-DD"-->
<!--          />-->
        </el-form-item>

        <el-form-item label="导演">
          <el-input type="text" v-model="data.form.director" placeholder="请输入导演"/>
        </el-form-item>

        <el-form-item label="演员">
          <el-input type="textarea" v-model="data.form.actors" placeholder="请输入演员"/>
        </el-form-item>

        <el-form-item label="分类">
          <el-input type="text" v-model="data.form.categoryName" placeholder="请输入分类"/>
        </el-form-item>

        <el-form-item label="国家">
          <el-input type="text" v-model="data.form.country" placeholder="请输入国家"/>
        </el-form-item>

        <el-form-item label="语言">
          <el-input type="text" v-model="data.form.language" placeholder="请输入语言"/>
        </el-form-item>

        <el-form-item label="上映日期">
<!--          <el-input type="text" v-model="data.form.date" placeholder="请输入上映日期"/>-->
          <el-date-picker
              style="width: 100%"
              v-model="data.form.date"
              type="date"
              placeholder="选择日期"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="片长">
          <el-input type="text" v-model="data.form.duration" placeholder="请输入片长"/>
        </el-form-item>

        <el-form-item label="IMDb">
          <el-input type="text" v-model="data.form.imdb" placeholder="请输入 IMDb"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.updateFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updatefilm">更 新</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.description-cell {
  display: flex;
  align-items: center;
}
</style>

<script lang="ts" setup>
import request from "@/utils/request";
import { reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

// OSS 上传地址
const uploadUrl = import.meta.env.VITE_BASE_URL + '/fileoss/file';

// 数据模型
const data = reactive({
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  name: null,

  addFormVisible: false,  //新增弹窗显示
  updateFormVisible: false,

  form: {
    id: '',
    name: '',
    cover: '',
    descr: '',
    year: '',
    director: '',
    actors: '',
    categoryName: '',
    country: '',
    language: '',
    date: '',
    duration: '',
    imdb: ''
  }
});

// 图片上传成功回调
const handleImgSuccess = (res) => {
  data.form.cover = res.data;
};

// 获取当前时间（可选）
function getCurrentTime() {
  const now = new Date();
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')} ${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;
}

// 加载数据
const load = () => {
  request.get('/film/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
    }
  }).then(res => {
    if (res.code === '200') {
      // 初始化折叠状态
      data.tableData = res.data.list.map(item => ({
        ...item,
        isExpanded: false,         // 描述折叠状态
        actorsExpanded: false     // 演员折叠状态
      }));
      data.total = res.data.total || 0;
    } else {
      ElMessage.error('加载失败');
    }
  });
};

// 页面加载自动执行
load();

// 重置搜索条件
const reset = () => {
  data.name = null;
  load();
};

// 显示新增弹窗
const handleAdd = () => {
  //初始化新增数据
  data.form={ id: '', name: '',cover:'',descr:'',year:'',director:'',actors:'',categoryName:'',country:'',language:'',date:'',duration:'',imdb:''}

  //显示弹窗
  data.addFormVisible=true
};
//
// // 删除封面图片
// const handleRemove = (file, fileList) => {
//   return ElMessageBox.confirm('确定要删除该图片吗？')
//       .then(() => {
//         data.form.cover = ''; // 清空封面图片字段
//         return true;
//       })
//       .catch(() => false);
// };

// const handleExceed = () => {
//   ElMessage.warning('只能上传一张封面图片');
// };

// 显示编辑弹窗
const handUpdate = (row) => {
  // 请求详情数据
  request.get(`/film/selectById/${row.id}`).then(res => {
    if (res.code === '200') {
      data.form = res.data;
      data.updateFormVisible = true;
    } else {
      ElMessage.error('获取电影信息失败');
    }
  }).catch(err => {
    ElMessage.error('网络异常');
    console.error(err);
  });
};

// 新增电影
const addfilm = () => {
  request.post('/film/insert', data.form)
      .then(res => {
        if (res.code === '200') {
          data.addFormVisible = false;
          load(); // 刷新列表
          ElMessage.success('新增成功');
        } else {
          ElMessage.error(res.msg || '新增失败');
        }
      })
      .catch(() => {
        ElMessage.error('网络异常，请重试');
      });
};

// 更新电影
const updatefilm = () => {
  request.put('/film/updateById', data.form)
      .then(res => {
        if (res.code === '200' || res.success) {
          data.updateFormVisible = false;
          load(); // 刷新列表
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

// 删除电影
const deletefilm = (row) => {
  ElMessageBox.confirm('确定要删除该电影吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    request.delete(`/film/deleteById/${row.id}`)
        .then(res => {
          if (res.code === '200') {
            load();
            ElMessage.success('删除成功');
          } else {
            ElMessage.error(res.msg || '删除失败');
          }
        })
        .catch(err => {
          console.error('删除失败:', err);
          ElMessage.error('网络异常，请重试');
        });
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
};

// 折叠/展开描述
const toggleDescription = (row: any) => {
  console.log('切换折叠状态:', row);
  row.isExpanded = !row.isExpanded;
};

//折叠展开演员
const toggleActors=(row: any) => {
  console.log('切换折叠状态:', row);
  row.actorsExpanded = !row.actorsExpanded;
}

</script>