package com.example.api.controller;

import com.example.api.service.WordService;
import com.example.api.util.ReponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Word Controller", description = "Word 報表匯出測試")
@RequestMapping("/export/word")
public class WordController {
    @Autowired
    private WordService wordService;

    @Operation(summary = "Word 資料生成",
            description = "Word 資料生成")
    @PostMapping("/generateWord")
    public ResponseEntity<Resource> generateWord() {

        var file = wordService.generateWord();
        return ReponseUtil.responseEntity("word檔案生成.docx", file);
    }

    @Operation(summary = "Word 資料生成 (多筆資料)",
            description = "Word 資料生成 (多筆資料)")
    @PostMapping("/generateWordList")
    public ResponseEntity<Resource> generateWordList() {

        var file = wordService.generateWordList();
        return ReponseUtil.responseEntity("word檔案生成(多筆).docx", file);
    }

    @Operation(summary = "Word 多檔合併",
            description = "Word 多檔合併")
    @PostMapping("/mergeWord")
    public ResponseEntity<Resource> mergeWord() {

        var file = wordService.mergeWord();
        return ReponseUtil.responseEntity("word多檔合併.docx", file);
    }
}
