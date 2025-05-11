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
@Tag(name = "Excel 報表匯出測試")
@RequestMapping("/export/excel")
public class ExportExcelController {
    @Autowired
    private ExportService exportService;

    @Operation(summary = "Excel 報表測試: Each 遞迴表格",
            description = "Excel 報表測試: Each 遞迴表格",
            operationId = "excelEach")
    @GetMapping("/excelEach")
    public ResponseEntity<Resource> excelEach(@RequestParam String clientId) {
        var file = exportService.excelEach(clientId);
        return ExportReponseUtil.responseEntity("Grid測試表格.xlsx", file);
    }

    @Operation(summary = "Excel 報表測試: Grid 動態表格",
            description = "Excel 報表測試: Grid 動態表格",
            operationId = "excelGrid")
    @GetMapping("/excelGrid")
    public ResponseEntity<Resource> excelGrid() {
        var file = exportService.excelGrid();
        return ExportReponseUtil.responseEntity("Grid測試表格.xlsx", file);
    }
}
