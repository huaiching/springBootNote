package com.example.api.controller;

import com.example.api.service.PdfService;
import com.example.api.util.ExportReponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;


    @GetMapping("/policy/pdf")
    public ResponseEntity<Resource> generatePdf() {
        var file = pdfService.generatePolicyPdf();
        return ExportReponseUtil.responseEntity("policy.pdf", file);
    }
}
