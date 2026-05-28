<template>
  <div>
    <!-- 查询区域 -->
    <el-card class="filter-card" shadow="hover">
      <el-row :gutter="10">
        <el-col :span="8">
          <el-input v-model="data.filmName" placeholder="请输入电影名称查询" clearable />
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="load">查询</el-button>
          <el-button @click="reset">重置</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!--分类列表-->
    <!--分类列表-->
    <div class="card" style="margin-bottom: 5px">
      <el-button
          :class="{'film-active': categoryId === null}"
          @click="loadCategory(null)"
      >
        全部
      </el-button>

      <el-button
          v-for="item in data.categoryList"
          :key="item.id"
          :class="{'film-active': categoryId === item.id}"
          @click="loadCategory(item.id)"
      >
        {{ item.name }}
      </el-button>
    </div>

    <!--电影列表-->
    <div class="card">
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in data.tableData" :key="item.id" style="margin-bottom: 20px; cursor: pointer" @click="goDetail(item.id)">
          <img :src="item.cover" alt="" style="width: 100%;height:500px; border-radius: 5px">
          <div style="margin: 5px 0; font-size: 20px" class="line1">{{ item.name}}</div>
          <div style="margin: 5px 0;display:flex ; align-content: center" >
            <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
            <div style="flex:1;text-align: right; color: #86a8e7;font-size:16px">{{ item.commentNum}}人评论</div>
          </div>
          <div style="font-size: 13px; color:#666" class="line3">{{ item.descr }}</div>
        </el-col>
      </el-row>
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
  </div>
</template>


<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request";

// 主数据容器
const data = reactive({
  name: null,
  pageNum: 1,
  pageSize: 8,
  tableData: [],
  total: 0,
  categoryList: []
});

// 使用 ref 定义 categoryId，确保它是响应式的
const categoryId = ref(null);

// 根据分类查询电影
const loadCategory = (id) => {
  categoryId.value = id; // 改变 ref 的 value 值
  load();
};

// 查询所有分类
const loadCategories = () => {
  request.get('/category/selectAll').then(res => {
    if (res.code === '200') {
      data.categoryList = res.data;
    }
  });
};
loadCategories();

// 查询电影
const load = () => {
  request.get('/film/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      categoryId: categoryId.value // 使用 ref 的 value 属性
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data.list.map(item => ({
        ...item,
        isExpanded: false,
        actorsExpanded: false
      }));
      data.total = res.data.total || 0;
    }
  });
};

const goDetail=(id)=>{
  //跳转
  location. href = '/filmDetail?id=' + id
}

// 页面加载自动执行
load();
</script>

<style scoped>
.film-active {
  background-color: #1967e3; /* 蓝底 */
  color: white;              /* 白字 */
}
</style>

