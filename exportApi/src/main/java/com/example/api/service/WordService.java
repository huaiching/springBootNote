package com.example.api.service;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopColumnTableRenderPolicy;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.example.api.dto.excel.AddrDTO;
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
    public byte[] generateWordMerge() {

        List<Map<String, Object>> contextList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String userId = "TEST00" + i;
            String userName = "測試人員" + i;
            String userSex = "男性";

            Map<String, Object> context = new HashMap<>();
            context.put("names", userName);
            context.put("clientId", userId);
            context.put("sex", userSex);

            contextList.add(context);
        }


        return WordUtil.generateWordMerge("sample.docx", contextList);
    }

    /**
     * Word 清單資料生成
     *
     * @return
     */
    public byte[] generateWordList() {
        String userId = "A123456789";
        String userName = "測試人員";

        List<Map<String, Object>> addrList = new ArrayList<>();
        for (int j = 1; j <= 9; j++) {
            AddrDTO addrDTO = new AddrDTO();
            addrDTO.setAddrInd(String.valueOf(j));
            addrDTO.setAddress("台北市內湖區石潭路58號" + j + "樓");
            addrDTO.setTel("02-23455511");

            Map<String, Object> addr = new HashMap<>();
            addr.put("addrInd", addrDTO.getAddrInd());
            addr.put("address", addrDTO.getAddress());
            addr.put("tel", addrDTO.getTel());
            addrList.add(addr);
        }

        // 設定 列表規則
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure configure = Configure.builder().bind("addr", policy).build();

        // 設定 資料內容
        Map<String, Object> context = new HashMap<>();
        context.put("clientId", userId);
        context.put("names", userName);
        context.put("addr", addrList);

        return WordUtil.generateWordList("sampleList.docx", configure, context);
    }

    /**
     * Word 多檔合併
     *
     * @return
     */
    public byte[] mergeWord() {
        List<byte[]> fileList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String userId = "TEST00" + i;
            String userName = "測試人員" + i;
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
