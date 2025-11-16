package com.example.api.controller;

import com.example.api.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test Controller", description = "測試 api")
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @Operation(summary = "測試 api")
    @PostMapping("/teskt")
    public ResponseEntity<Void> generateZip() throws Exception {
        testService.text();
        return ResponseEntity.ok().build();
    }
}
