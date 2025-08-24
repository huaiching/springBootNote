package com.example.api.controller;

import com.example.api.service.HtmlToPdfService;
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
public class HtmlToPdfController {

    @Autowired
    private HtmlToPdfService htmlToPdfService;

    @Operation(summary = "openHtmlToPdf 報表測試",
            description = "openHtmlToPdf 報表測試")
    @PostMapping("/generatePdf")
    public ResponseEntity<Resource> generatePdf() {
        var file = htmlToPdfService.generatePdf();
        return ReponseUtil.responseEntity("htmlToPdf.pdf", file);
    }
}
