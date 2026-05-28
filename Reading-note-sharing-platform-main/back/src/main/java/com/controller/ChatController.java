package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${deepseek.api-key}")
    private String apiKey;

    // AI 续写接口
    @PostMapping("/continue")
    public ResponseEntity<Map<String, Object>> continueChat(@RequestBody Map<String, String> request) {
        String content = request.get("content");

        String prompt = "请根据以下内容继续续写100字，保持风格一致，直接返回续写内容，不要加任何解释：\n\n" + content;

        String result = callDeepSeek(prompt);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", result);
        return ResponseEntity.ok(response);
    }

    // 智能摘要接口
    @PostMapping("/summary")
    public ResponseEntity<Map<String, Object>> summary(@RequestBody Map<String, String> request) {
        String content = request.get("content");

        String prompt = "请为以下内容生成一个简洁的摘要，50字以内：\n\n" + content;

        String result = callDeepSeek(prompt);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", result);
        return ResponseEntity.ok(response);
    }

    // 调用 DeepSeek API 的通用方法
    private String callDeepSeek(String prompt) {
        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        requestBody.put("messages", messages);
        requestBody.put("stream", false);
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 1000);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 发送请求
        RestTemplate restTemplate = new RestTemplate();
        String deepseekUrl = "https://api.deepseek.com/v1/chat/completions";

        try {
            Map<String, Object> response = restTemplate.postForObject(deepseekUrl, entity, Map.class);

            // 提取返回内容
            List<?> choices = (List<?>) response.get("choices");
            Map<?, ?> firstChoice = (Map<?, ?>) choices.get(0);
            Map<?, ?> message = (Map<?, ?>) firstChoice.get("message");
            String content = (String) message.get("content");

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "调用失败：" + e.getMessage();
        }
    }
}