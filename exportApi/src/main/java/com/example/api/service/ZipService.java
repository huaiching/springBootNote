package com.example.api.service;

import com.example.api.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZipService {
    @Autowired
    private ExcelService excelService;
    @Autowired
    private WordService wordService;

    /**
     * Zip 檔案打包
     * @return
     */
    public byte[] generateZip() {
        // 產生 excel
        byte[] excelFile = excelService.excelEach();

        // 產生 word
        byte[] wordFile = wordService.generateWord();


        // 打包為 zip
        Map<String, byte[]> fileList = new HashMap<>();
        fileList.put("excel.xlsx", excelFile);
        fileList.put("word.docx", wordFile);

        return ZipUtil.createZip(fileList);
    }
}
