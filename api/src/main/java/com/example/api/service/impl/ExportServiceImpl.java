package com.example.api.service.impl;

import com.example.api.constants.SexEnum;
import com.example.api.entity.Addr;
import com.example.api.entity.Clnt;
import com.example.api.repository.AddrRepository;
import com.example.api.repository.ClntRepository;
import com.example.api.service.ExportService;
import com.example.api.util.ExportExcelUtil;
import com.example.api.util.ExportPdfUtil;
import com.example.api.util.ExportWordUtil;
import org.jxls.common.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ClntRepository clntRepository;
    @Autowired
    private AddrRepository addrRepository;

    /**
     * 單筆列印客戶證號明細表
     *
     * @param clientId 客戶證號
     * @return
     */
    @Override
    public byte[] wordTest(String clientId) {
        Clnt clnt = clntRepository.findById(clientId).get();

        Map<String, Object> context = new HashMap<>();
        context.put("names", clnt.getNames());
        context.put("clientId", clnt.getClientId());
        context.put("sex", SexEnum.getDescByCode(clnt.getSex()));

        return ExportWordUtil.generateWord("/templates/sample.docx", context);
    }

    /**
     * 列印單筆客戶證號明細表 並轉成 PDF
     *
     * @param clientId 客戶證號
     * @return
     */
    @Override
    public byte[] wordToPdf(String clientId) {
        var wordFile = wordTest(clientId);
        return ExportPdfUtil.wordToPDF(wordFile);
    }

    /**
     * Excel 的 Each 遞迴資料
     * @param clientId 客戶證號
     *
     * @return
     */
    @Override
    public byte[] excelEach(String clientId) {
        Clnt clnt = clntRepository.findById(clientId).get();
        List<Addr> addrList = addrRepository.findByClientId(clientId);

        // 設定 資料內容
        Context context = new Context();
        context.putVar("clientId", clnt.getClientId());
        context.putVar("names", clnt.getNames());
        context.putVar("addr", addrList);

        return ExportExcelUtil.generateExcel("/templates/sampleEach.xlsx", context);
    }

    /**
     * Excel 的 Grid 動態資料
     *
     * @return
     */
    @Override
    public byte[] excelGrid() {
        List<Clnt> clntList = clntRepository.findAll();
        // 設定 headers
        List<String> headers = Arrays.asList("姓名", "客戶證號", "性別");
        // 設定 數據集合
        // 1. 要用兩層 List 進行封裝
        // 2. add 的順序 = 資料顯示的順序
        List<List<Object>> dataList = new ArrayList<>();
        for (Clnt clnt : clntList) {
            List<Object> data = new ArrayList<>();
            data.add(clnt.getNames());
            data.add(clnt.getClientId());
            data.add(SexEnum.getDescByCode(clnt.getSex()));
            dataList.add(data);
        }

        // 設定 資料內容
        Context context = new Context();
        context.putVar("title", "Grid 測試表格");
        context.putVar("headers", headers);
        context.putVar("dataList", dataList);

        return ExportExcelUtil.generateExcel("/templates/sampleGrid.xlsx", context);
    }
}
