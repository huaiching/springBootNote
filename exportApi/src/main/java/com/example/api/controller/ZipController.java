package com.example.api.controller;

import com.example.api.service.WordService;
import com.example.api.service.ZipService;
import com.example.api.util.ReponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Zip Controller", description = "Zip 報表打包測試")
@RequestMapping("/export/zip")
public class ZipController {
    @Autowired
    private ZipService zipService;

    @Operation(summary = "Zip 檔案打包",
            description = "Zip 檔案打包")
    @PostMapping("/generateZip")
    public ResponseEntity<Resource> generateZip() {

        var file = zipService.generateZip();
        return ReponseUtil.responseEntity("zipFilt.zip", file);
    }
}
