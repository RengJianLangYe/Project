import { ElMessage } from 'element-plus'
import router from '../router'
import axios from "axios";

// ✅ 确保 VITE_BASE_URL 存在，默认值为 '/api'
const baseURL = import.meta.env.VITE_BASE_URL || '/api';

const request = axios.create({
    baseURL: baseURL, // 使用 /api（推荐） 或 http://localhost:9090（开发阶段）
    timeout: 30000,
});

// 请求拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    return config;
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;

        // 处理 Blob 类型响应
        if (response.config.responseType === 'blob') {
            return res;
        }

        // JSON 字符串自动解析
        if (typeof res === 'string') {
            try {
                res = JSON.parse(res);
            } catch {}
        }

        // 登录失效处理
        if (res.code === '401') {
            ElMessage.error(res.msg);
            router.push("/login");
        }

        return res;
    },
    error => {
        console.log('err:', error);
        return Promise.reject(error);
    }
);

export default request;