package com.example.api.service;

import com.example.api.dto.excel.AddrDTO;
import com.example.api.util.ExcelUtil;

import org.jxls.common.Context;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExcelService {

    /**
     * Excel 的 Each 遞迴資料
     *
     * @return
     */
    public byte[] excelEach() {
        String userId = "A123456789";
        String userName = "測試人員";
        List<AddrDTO> addrList = new ArrayList<>();
        for (int i = 1 ; i <= 9 ; i++) {
            AddrDTO addr = new AddrDTO();
            addr.setAddrInd(String.valueOf(i));
            addr.setAddress("台北市內湖區石潭路58號"+i+"樓");
            addr.setTel("02-23455511");
            addrList.add(addr);
        }

        // 設定 資料內容
        Context context = new Context();
        context.putVar("clientId", userId);
        context.putVar("names", userName);
        context.putVar("addr", addrList);

        return ExcelUtil.generateExcel("sampleEach.xlsx", context);
    }

    /**
     * Excel 的 Each 遞迴資料 (多筆資料)
     *
     * @return
     */
    public byte[] excelEachAll() {
        Map<String, Context> dataMap = new HashMap<>();
        for (int i = 1 ; i <= 5 ; i++) {
            String userId = "TEST00"+i;
            String userName = "測試人員"+i;
            List<AddrDTO> addrList = new ArrayList<>();
            for (int j = 1 ; j <= 9 ; j++) {
                AddrDTO addr = new AddrDTO();
                addr.setAddrInd(String.valueOf(j));
                addr.setAddress("台北市內湖區石潭路58號"+j+"樓");
                addr.setTel("02-23455511");
                addrList.add(addr);
            }
            // 設定 資料內容
            Context context = new Context();
            context.putVar("clientId", userId);
            context.putVar("names", userName);
            context.putVar("addr", addrList);

            dataMap.put(userId, context);
        }

        return ExcelUtil.generateExcelList("sampleEach.xlsx", dataMap);
    }

    /**
     * Excel 的 Grid 動態資料
     *
     * @return
     */
    public byte[] excelGrid() {
        // 設定 headers
        List<String> headers = Arrays.asList("姓名", "客戶證號", "性別");
        // 設定 數據集合: 使用 List<List<Object>> 封裝
        // List<Object> 的寫入順序，對應 headers 的欄位順序
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 1 ; i <= 9 ; i++) {
            List<Object> data = new ArrayList<>();
            String userName = "測試人員"+i;
            String userId = "TEST00"+i;
            String userSex = "男性";
            data.add(userName);
            data.add(userId);
            data.add(userSex);
            dataList.add(data);

        }

        // 設定 資料內容
        Context context = new Context();
        context.putVar("title", "Grid 測試表格");
        context.putVar("headers", headers);
        context.putVar("dataList", dataList);

        return ExcelUtil.generateExcel("sampleGrid.xlsx", context);
    }

    /**
     * Excel 檔案合併
     *
     * @return
     */
    public byte[] mergeExcel() {
        Map<String, byte[]> fileList = new HashMap<>();
        fileList.put("excelEach", excelEach());
        fileList.put("excelGrid", excelGrid());

        return ExcelUtil.mergeExcel(fileList);
    }
}
