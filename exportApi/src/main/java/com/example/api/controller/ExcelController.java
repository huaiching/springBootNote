package com.example.api.controller;

import com.example.api.service.ExcelService;
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
@Tag(name = "Excel Controller", description = "Excel 報表匯出測試")
@RequestMapping("/export/excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @Operation(summary = "Excel 報表測試: Each 遞迴表格",
            description = "Excel 報表測試: Each 遞迴表格")
    @PostMapping("/excelEach")
    public ResponseEntity<Resource> excelEach() {
        byte[] fileByte = excelService.excelEach();
        String fileName = "Each測試表格.xlsx";

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

    @Operation(summary = "Excel 報表測試: Each 遞迴表格 多資料",
            description = "Excel 報表測試: Each 遞迴表格 多資料")
    @PostMapping("/excelEachAll")
    public ResponseEntity<Resource> excelEachAll() {
        byte[] fileByte = excelService.excelEachAll();
        String fileName = "Each測試表格(多檔).xlsx";

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

    @Operation(summary = "Excel 報表測試: Grid 動態表格",
            description = "Excel 報表測試: Grid 動態表格")
    @PostMapping("/excelGrid")
    public ResponseEntity<Resource> excelGrid() {
        byte[] fileByte = excelService.excelGrid();
        String fileName = "Grid測試表格.xlsx";

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

    @Operation(summary = "Excel 檔案合併",
            description = "Excel 檔案合併")
    @PostMapping("/mergeExcel")
    public ResponseEntity<Resource> mergeExcel() {
        byte[] fileByte = excelService.mergeExcel();
        String fileName = "mergeExcel.xlsx";

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
