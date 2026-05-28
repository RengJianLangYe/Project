<template>
  <div>
    <div class="card" style="width: 50%">
      <h2 style="margin-bottom: 20px; padding-left:50px;font-weight: bold">系统公告</h2>
      <el-timeline>
        <el-timeline-item v-for="item in data.noticeList" :key="item.id" :timestamp="item.time" placement="top">
          <el-card>
            <h2 style="margin-bottom: 10px;font-weight: bold">{{ item.title }}</h2>
            <div style="font-size: 16px;margin-bottom: 10px;">{{ item.content }}</div>
            <p style="color:gray">{{ item.time }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  noticeList:[]

})

request.get('/notice/selectAll').then(res =>{
  data.noticeList =res.data
})
</script>