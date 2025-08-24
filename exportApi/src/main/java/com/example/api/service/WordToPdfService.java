package com.example.api.service;

import org.springframework.stereotype.Service;

import com.example.api.util.WordToPdfUtil;
import com.example.api.util.WordUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordToPdfService {
    /**
     * word 轉 PDF
     *
     * @return
     */
    public byte[] generate() {
        String userId = "A123456789";
        String userName = "測試人員";
        String userSex = "男性";


        Map<String, Object> context = new HashMap<>();
        context.put("names", userName);
        context.put("clientId", userId);
        context.put("sex", userSex);

        byte[] file = WordUtil.generateWord("/templates/sample.docx", context);

        return WordToPdfUtil.wordToPDF(file);
    }
}
