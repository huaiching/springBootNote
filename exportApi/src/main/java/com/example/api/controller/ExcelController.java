package com.example.api.controller;

import com.example.api.service.ExcelService;
import com.example.api.util.ReponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        var file = excelService.excelEach();
        return ReponseUtil.responseEntity("Each測試表格.xlsx", file);
    }

    @Operation(summary = "Excel 報表測試: Each 遞迴表格 多資料",
            description = "Excel 報表測試: Each 遞迴表格 多資料")
    @PostMapping("/excelEachAll")
    public ResponseEntity<Resource> excelEachAll() {
        var file = excelService.excelEachAll();
        return ReponseUtil.responseEntity("Each測試表格(多檔).xlsx", file);
    }

    @Operation(summary = "Excel 報表測試: Grid 動態表格",
            description = "Excel 報表測試: Grid 動態表格")
    @PostMapping("/excelGrid")
    public ResponseEntity<Resource> excelGrid() {
        var file = excelService.excelGrid();
        return ReponseUtil.responseEntity("Grid測試表格.xlsx", file);
    }

    @Operation(summary = "Excel 檔案合併",
            description = "Excel 檔案合併")
    @PostMapping("/mergeExcel")
    public ResponseEntity<Resource> mergeExcel() {
        var file = excelService.mergeExcel();
        return ReponseUtil.responseEntity("mergeExcel.xlsx", file);
    }
}
