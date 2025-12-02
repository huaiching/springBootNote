package com.mli.flow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/demo")
@Tag(name = "demo Controller", description = "範例 API")
public class DemoController {

    @GetMapping("/sample")
    @Operation(summary = "sample")
    public ResponseEntity<String> sample(@RequestParam String caseNo) {
        return ResponseEntity.ok("範例 API");
    }
}
