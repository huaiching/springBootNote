package com.example.api.controller;

import com.example.api.service.ExportService;
import com.example.api.util.ExportReponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Word 報表匯出測試")
@RequestMapping("/export/word")
public class ExportWordController {
    @Autowired
    private ExportService exportService;

    @Operation(summary = "Word 報表測試",
            description = "Word 報表測試",
            operationId = "test")
    @GetMapping("/test")
    public ResponseEntity<Resource> test(@RequestParam String clientId) {

        var file = exportService.wordTest(clientId);
        return ExportReponseUtil.responseEntity("客戶證號明細表.docx", file);
    }
}
