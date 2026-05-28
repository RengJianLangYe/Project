package com.example.oss.service.Impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;  // 确保正确导入
import com.example.oss.ConstantPropertiesUtils;
import com.example.oss.service.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvater(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 1. 创建OSS客户端（推荐使用try-with-resources自动关闭）
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 2. 处理文件名
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String datePath = new DateTime().toString("yyyy/MM/dd");
            String fileName = datePath + "/" + uuid + originalFilename;

            // 3. 上传文件
            try (InputStream inputStream = file.getInputStream()) {
                ossClient.putObject(bucketName, fileName, inputStream);
            }

            // 4. 生成访问URL
            return "https://" + bucketName + "." + endpoint + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }
}