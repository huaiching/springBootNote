package com.example.api.controller;

import com.example.api.service.MergePdfService;
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
@Tag(name = "PDF Controller", description = "PDF 報表匯出測試")
@RequestMapping("/export/htmlToPdf")
public class MergePdfController {

    @Autowired
    private MergePdfService mergePdfService;

    @Operation(summary = "PDF 合併",
            description = "PDF 合併")
    @PostMapping("/mergePDF")
    public ResponseEntity<Resource> mergePDF() {
        var file = mergePdfService.mergePDF();
        return ReponseUtil.responseEntity("mergePDF.pdf", file);
    }
}
