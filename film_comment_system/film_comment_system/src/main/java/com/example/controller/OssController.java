package com.example.controller;

import com.example.common.Result;
import com.example.oss.service.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping(path = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "附件",
            content = @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(type = "object"),
                    schemaProperties = {
                            @SchemaProperty(
                                    name = "file",
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    }
            )
    )
    @Operation(summary = "上传文件到 OSS", description = "上传文件并返回文件 URL")
    public Result upload(MultipartFile file){
        String url = ossService.uploadFileAvater(file);
        System.out.println("url:"+url);
        return Result.success(url);
    }
}
