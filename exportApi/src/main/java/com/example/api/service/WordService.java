package com.example.api.service;

import org.springframework.stereotype.Service;

import com.example.api.util.WordUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordService {

    /**
     * Word 資料生成
     *
     * @return
     */
    public byte[] generateWord() {
        String userId = "A123456789";
        String userName = "測試人員";
        String userSex = "男性";


        Map<String, Object> context = new HashMap<>();
        context.put("names", userName);
        context.put("clientId", userId);
        context.put("sex", userSex);

        return WordUtil.generateWord("sample.docx", context);
    }

    /**
     * Word 資料生成 (多筆資料)
     *
     * @return
     */
    public byte[] generateWordList() {

        List<Map<String, Object>> contextList = new ArrayList<>();

        for (int i = 1 ; i <= 5 ; i++) {
            String userId = "TEST00"+i;
            String userName = "測試人員"+i;
            String userSex = "男性";

            Map<String, Object> context = new HashMap<>();
            context.put("names", userName);
            context.put("clientId", userId);
            context.put("sex", userSex);

            contextList.add(context);
        }


        return WordUtil.generateWordList("sample.docx", contextList);
    }

    /**
     * Word 多檔合併
     *
     * @return
     */
    public byte[] mergeWord() {
        List<byte[]> fileList = new ArrayList<>();

        for (int i = 1 ; i <= 5 ; i++) {
            String userId = "TEST00"+i;
            String userName = "測試人員"+i;
            String userSex = "男性";

            Map<String, Object> context = new HashMap<>();
            context.put("names", userName);
            context.put("clientId", userId);
            context.put("sex", userSex);

            byte[] file = WordUtil.generateWord("sample.docx", context);

            fileList.add(file);
        }

        return WordUtil.mergeWord(fileList);
    }
}
