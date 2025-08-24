package com.example.api.controller;

import com.example.api.service.WordToPdfService;
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
@RequestMapping("/export/wordToPdf")
public class WordToPdfController {
    @Autowired
    private WordToPdfService wordToPdfService;

    @Operation(summary = "word 轉 PDF",
            description = "word 轉 PDF")
    @PostMapping("/generateWord")
    public ResponseEntity<Resource> generateWord() {

        var file = wordToPdfService.generate();
        return ReponseUtil.responseEntity("wordToPdf.pdf", file);
    }
}
