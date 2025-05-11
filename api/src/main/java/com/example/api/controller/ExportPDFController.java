package com.example.api.controller;

import com.example.api.service.ExportService;
import com.example.api.util.ExportReponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "PDF 報表匯出測試")
@RequestMapping("/export/pdf")
public class ExportPDFController {
    @Autowired
    private ExportService exportService;

    @Operation(summary = "Word 轉成 PDF",
            description = "Word 轉成 PDF",
            operationId = "wordToPDF")
    @GetMapping("/wordToPDF")
    public ResponseEntity<Resource> wordToPDF(@RequestParam String clientId) {

        var file = exportService.wordToPdf(clientId);
        return ExportReponseUtil.responseEntity("客戶證號明細表.pdf", file);
    }
}
