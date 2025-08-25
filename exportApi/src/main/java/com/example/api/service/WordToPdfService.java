package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.util.WordToPdfUtil;
import com.example.api.util.WordUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordToPdfService {
    @Autowired
    private WordService wordService;

    /**
     * word 轉 PDF
     *
     * @return 產出的 PDF 檔案資料流（byte[]）
     */
    public byte[] generate() {
        byte[] file = wordService.generateWord();

        return WordToPdfUtil.wordToPDF(file);
    }
}
