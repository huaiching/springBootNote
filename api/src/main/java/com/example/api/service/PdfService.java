package com.example.api.service;

import com.example.api.util.ExportPdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generatePolicyPdf() {
        // 模擬資料
        Map<String, Object> data = new HashMap<>();

        data.put("applicant", Map.of(
                "name", "王小明",
                "id", "A123456789",
                "phone", "0912-345678"
        ));

        data.put("policy", Map.of(
                "number", "POL123456",
                "date", "2025-08-20"
        ));

        List<Map<String, Object>> coverages = List.of(
                Map.of("item", "意外險", "amount", "100萬", "premium", "2000"),
                Map.of("item", "醫療險", "amount", "50萬", "premium", "1500"),
                Map.of("item", "壽險", "amount", "200萬", "premium", "3000")
        );
        data.put("coverages", coverages);

        return ExportPdfUtil.htmlToPdf(templateEngine, "policy", data);
    }

}
