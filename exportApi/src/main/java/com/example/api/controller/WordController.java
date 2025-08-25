package com.example.api.controller;

import com.example.api.service.WordService;
import com.example.api.util.ReponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        byte[] fileByte = wordService.generateWord();
        String fileName = "word檔案生成.docx";

        // 文件打包
        Resource resource = new ByteArrayResource(fileByte);
        // 文件下载
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "Word 資料生成 (多筆資料)",
            description = "Word 資料生成 (多筆資料)")
    @PostMapping("/generateWordMerge")
    public ResponseEntity<Resource> generateWordMerge() {
        byte[] fileByte = wordService.generateWordMerge();
        String fileName = "word檔案生成(多筆).docx";

        // 文件打包
        Resource resource = new ByteArrayResource(fileByte);
        // 文件下载
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "Word 清單資料生成",
            description = "Word 清單資料生成")
    @PostMapping("/generateWordList")
    public ResponseEntity<Resource> generateWordList() {
        byte[] fileByte = wordService.generateWordList();
        String fileName = "word檔案生成(清單).docx";

        // 文件打包
        Resource resource = new ByteArrayResource(fileByte);
        // 文件下载
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "Word 多檔合併",
            description = "Word 多檔合併")
    @PostMapping("/mergeWord")
    public ResponseEntity<Resource> mergeWord() {
        byte[] fileByte = wordService.mergeWord();
        String fileName = "word多檔合併.docx";

        // 文件打包
        Resource resource = new ByteArrayResource(fileByte);
        // 文件下载
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
