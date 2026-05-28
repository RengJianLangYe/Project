<template>
  <div>
    <div style="display: flex;align-items: flex-start; grid-gap: 10px">
<!--      电影信息-->
      <div style="flex: 1; width: 0">
        <div class="card" style="padding: 30px ;margin-bottom: 10px">
          <!--        电影名字那行-->
          <div style="display: flex; align-items: center;">
            <div style="font-weight: bold; font-size: 25px; flex: 1">{{ data.film.name }}</div>
            <div>
              <el-button type="primary" plain @click="addComment('短评')">写短评</el-button>
              <el-button type="success" plain @click="addComment('长评')">写长评</el-button>
            </div>
          </div>
          <!--电影评分-->
          <div style="margin-bottom: 20px">
            <el-rate v-model="data.film.score" disabled allow-half show-score></el-rate>
          </div>
          <!--电影信息-->
          <div style="display: flex; grid-gap: 20px">
            <img :src="data.film.cover" alt="" style="width: 208px; border-radius: 5px">
            <div style="flex: 1; color: #666">
              <div style="margin-bottom: 10px"><strong>导演:</strong> {{ data.film.director }}</div>
              <div style="margin-bottom: 10px"><strong>主演:</strong> {{ data.film.actors }}</div>
              <div style="margin-bottom: 10px"><strong>类型:</strong> {{ data.film.categoryName }}</div>
              <div style="margin-bottom: 10px"><strong>制片国家/地区:</strong> {{ data.film.country }}</div>
              <div style="margin-bottom: 10px"><strong>语言:</strong> {{ data.film.language }}</div>
              <div style="margin-bottom: 10px"><strong>上映日期:</strong> {{ data.film.date }}</div>
              <div style="margin-bottom: 10px"><strong>片长:</strong> {{ data.film.duration }}</div>
              <div style="margin-bottom: 10px"><strong>IMDb: </strong> {{ data.film.imdb }}</div>
            </div>
          </div>

          <!--        剧情简介-->
          <div style="font-size: 20px; color: #1967e3; margin-bottom: 10px;margin-top: 20px">{{ data.film.name }}的剧情简介</div>
          <div style="color: #666; line-height: 24px ;text-align: justify">{{ data.film.descr }}</div>
        </div>

        <div class="card" style="padding: 20px">
          <div style="font-size: 20px; color: #239113; margin-bottom: 10px;margin-top: 20px">
            {{ data.film.name }}的短评
            <span style="font-size: 14px; color:#333">
              (全部{{data.totalShort}}条)
            </span>
          </div>
          <!-- 展示短评论-->
          <div style="border-bottom: 1px solid #eee; padding: 20px 0" v-for="item in data.commentShortList" :key="item.id">
            <div style="display: flex; align-items: center; margin-bottom: 10px">
              <span>{{ item.userName }}</span>
              <el-rate style="margin: 0 10px" v-model="item.score" disabled allow-half></el-rate>
              <span style="color: #666">{{ item.time }}</span>
            </div>
              <div style="line-height: 24px;color: #666;font-size: 15px" >{{ item.comment }}</div>
          </div>
          <!-- 分页 -->
          <div class="card">
            <el-pagination
                background
                layout="total, prev, pager, next"
                @current-change="loadShortComment"
                v-model:current-page="data.pageNumShort"
                v-model:pageSize="data.pageSizeShort"
                :total="data.totalShort"
            />
          </div>
        </div>

        <div class="card" style="padding: 20px">
          <div style="font-size: 20px; color:orange; margin-bottom: 10px;margin-top: 20px">
            {{ data.film.name }}的长评
            <span style="font-size: 14px; color:#333">
              (全部{{data.totalLong}}条)
            </span>
          </div>
          <!-- 展示长评论-->
          <div style="border-bottom: 1px solid #eee; padding: 20px 0" v-for="item in data.commentLongList" :key="item.id">
            <div style="display: flex; align-items: center; margin-bottom: 10px">
              <span>{{ item.userName }}</span>
              <el-rate style="margin: 0 10px" v-model="item.score" disabled allow-half></el-rate>
              <span style="color: #666">{{ item.time }}</span>
            </div>
            <div style="line-height: 24px; color: #666; margin-bottom: 5px" class="line3" v-html="item.comment"></div>
            <div>
              <span style="color: #1967e3; cursor: pointer" @click="viewLongComment(item.comment)">查看更多...</span>
            </div>
          </div>
          <!-- 分页 -->
          <div class="card">
            <el-pagination
                background
                layout="total, prev, pager, next"
                @current-change="loadLongComment"
                v-model:current-page="data.pageNumLong"
                v-model:pageSize="data.pageSizeLong"
                :total="data.totalLong"
            />
          </div>
        </div>



      </div>


      <div style="width: 350px;padding: 20px" class="card">
        <div style="font-size:25px;margin-bottom: 10px">推荐电影</div>
        <div
            style="margin-bottom: 20px;cursor: pointer"
            v-for="item in data.recommendList"
            :key="item.id"
            @click="goDetail(item.id)"
        >
          <img :src="item.cover" alt="" style="width: 100%" >
          <div style="margin: 5px 0; font-size: 20px">{{ item.name }}</div>
          <div>
            <el-rate
                v-model="item.score"
                disabled
                allow-half
                show-score
            ></el-rate>
          </div>
        </div>
      </div>


      <!-- 显示长评详细信息弹窗-->
      <el-dialog v-model="data.formVisibleView" title="长评" width="50%">
        <div style="line-height: 24px">
          <div v-html="data.comment" class="longComment"></div>
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="data.formVisibleView = false">关闭</el-button>
          </div>
        </template>
      </el-dialog>



      <!--  添加评论  -->
      <el-dialog v-model="data.formVisible" title="评论信息" width="50%">
        <el-form :model="data.form" label-width="80px" style="padding-right: 20px">
          <el-form-item label="打分">
            <el-rate v-model="data.form.score" allow-half show-score></el-rate>
          </el-form-item>
          <el-form-item label="内容" v-if="data.form.type==='短评'">
            <el-input
                type="textarea"
                v-model="data.form.comment"
                autocomplete="off"
                placeholder="请输入内容"
            />
          </el-form-item>

          <!--富文本框-->
          <el-form-item label="项目介绍" prop="comment" v-if="data.form.type==='长评'">
            <div style="border: 1px solid #ccc; width: 100%">
              <Toolbar
                  style="border-bottom: 1px solid #ccc"
                  :editor="editorRef"
                  :mode="mode"
              />
              <Editor
                  style="height: 500px; overflow-y: hidden;"
                  v-model="data.form.comment"
                  :mode="mode"
                  :defaultConfig="editorConfig"
                  @onCreated="handleCreated"
              />
            </div>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="data.formVisible = false">取消</el-button>
            <el-button type="primary" @click="save">保存</el-button>
          </div>
        </template>
      </el-dialog>


    </div>


  </div>
</template>


<script setup>
import { reactive } from "vue";
import request from "@/utils/request";
import router from "@/router";
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import {onBeforeUnmount, ref, shallowRef} from "vue";
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

// 主数据容器
const data = reactive({
  id: router.currentRoute.value.query.id,
  film: {},
  recommendList: [],
  formVisible: false,
  formVisibleView: false,
  comment:null,
  form: {},
  pageNumShort: 1,
  pageSizeShort: 3,
  totalShort: 0,
  commentShortList: [],
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  pageNumLong: 1,
  pageSizeLong: 3,
  totalLong: 0,
  commentLongList: [],
});

/* wangEditor5 初始化开始 */
const baseUrl = import.meta.env.VITE_BASE_URL
const editorRef = shallowRef()  // 编辑器实例，必须用 shallowRef
const mode = 'default'
const editorConfig = { MENU_CONF: {} }
// 图片上传配置
editorConfig.MENU_CONF['uploadImage'] = {
  server: baseUrl + '/files/wang/upload',  // 服务端图片上传接口
  fieldName: 'file'  // 服务端图片上传接口参数
}
// 组件销毁时，也及时销毁编辑器，否则可能会造成内存泄漏
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
// 记录 editor 实例，重要！
const handleCreated = (editor) => {
  editorRef.value = editor
}
/* wangEditor5 初始化结束 */

const goDetail=(id)=>{
  //跳转
  location. href = '/filmDetail?id=' + id
}

const viewLongComment=(comment)=>{
  data.comment=comment
  data.formVisibleView=true;
}

const addComment = (type) => {
  data.form = {type:type}
  data.formVisible = true;

}

//新增评论的方法
const save = () => {
  // 添加简单的表单验证
  if (!data.form.score) {
    ElMessage.warning('请先评分');
    return;
  }
  if (!data.form.comment || data.form.comment.trim() === '') {
    ElMessage.warning('请输入评论内容');
    return;
  }
  // 添加当前时间
  // data.form.time = new Date();
  // 获取当前时间并格式化为 YYYY-MM-DD HH:mm:ss
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');

  data.form.time = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  data.form.filmId = data.id
  data.form.userId = data.user.id
  request.post('/comment/insert', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('评论成功');
      data.formVisible = false;
      loadShortComment();
    } else {
      ElMessage.error(res.msg || '评论失败');
    }
  }).catch(err => {
    ElMessage.error('提交评论出错: ' + (err.response?.data?.msg || err.message));
    console.error(err);
  });
}

const loadShortComment = () => {
  request.get('/comment/selectPage', {
    params: {
      pageNum: data.pageNumShort,
      pageSize: data.pageSizeShort,
      type: '短评',
      filmId:data.id,
    }
  }).then(res => {
    data.commentShortList = res.data.list
    data.totalShort = res.data.total        // 总条数
  }).catch(err => {
    console.error('加载短评失败:', err)
  })
}

const loadLongComment = () => {
  request.get('/comment/selectPage', {
    params: {
      pageNum: data.pageNumLong,  // 修正：使用长评的分页参数
      pageSize: data.pageSizeLong, // 修正：使用长评的分页参数
      type: '长评',
      filmId:data.id,
    }
  }).then(res => {
    data.commentLongList = res.data.list  // 修正：赋值给长评列表
    data.totalLong = res.data.total
  }).catch(err => {
    console.error('加载长评失败:', err)  // 修正：错误消息改为"长评"
  })
}
//页面加载时调用
loadShortComment()
loadLongComment()  // 添加这行


request.get('/film/selectById/'+data.id).then(res =>{
  data.film=res.data   //赋值
})

request.get('/film/selectRecommend').then(res =>{
  data.recommendList=res.data   //赋值
})
</script>

<style>
.longComment img{
  width: 100%;
}

</style>