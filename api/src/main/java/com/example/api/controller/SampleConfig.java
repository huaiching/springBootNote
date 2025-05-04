package com.example.api.controller;

import com.example.api.dto.User;
import com.example.api.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sample")
@Tag(name = "範例", description = "範例 API 接口")
public class SampleConfig {

    @Autowired
    private SampleService sampleService;

    /**
     * @RequestBody 使用 Json 接收參數，會在 @PostMapping 使用
     */
    @Operation(summary = "POST 範例 API", description = "POST 範例", operationId = "showUserPostBody")
    @PostMapping("/showUserPostBody")
    public ResponseEntity<String> showUserPostBody(@RequestBody User user) {
        String msg = sampleService.showUser(user);
        return ResponseEntity.ok(msg);
    }

    /**
     * @RequestParam 透過 URL 接受參數，會在 @GetMapping 使用
     * 此方法 從 請求 URL 中獲取參數，方法網址不用特別設定參數
     */
    @Operation(summary = "GET 範例 API: RequestParam", description = "GET 範例", operationId = "showUserGetParam")
    @GetMapping("/showUserGetParam")
    public ResponseEntity<String> showUserGetParam(@RequestParam Long userId, @RequestParam String userName){
        String msg = sampleService.showUser(userId, userName);
        return ResponseEntity.ok(msg);
    }

    /**
     * @PathVariable 透過 URL 接受參數，會在 @GetMapping 使用
     * 此方法 在 URL 設定 變數，參數 由 URL 的變數中獲取
     * 網址 需要 特別設定 變數名稱
     */
    @Operation(summary = "GET 範例 API: showUserGetVariable", description = "GET 範例", operationId = "showUserGetVariable")
    @GetMapping("/showUserGetVariable/{userId}/{userName}")
    public ResponseEntity<String> showUserGetVariable(@PathVariable Long userId, @PathVariable String userName){
        String msg = sampleService.showUser(userId, userName);
        return ResponseEntity.ok(msg);
    }
}
