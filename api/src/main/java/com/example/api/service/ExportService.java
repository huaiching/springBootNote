package com.example.api.service;

public interface ExportService {

    /**
     * 單筆列印客戶證號明細表
     * @param clientId 客戶證號
     * @return
     */
    byte[] wordTest(String clientId);


    /**
     * 列印單筆客戶證號明細表 並轉成 PDF
     * @param clientId 客戶證號
     * @return
     */
    byte[] wordToPdf(String clientId);

    /**
     * Excel 的 Each 遞迴資料
     * @param clientId 客戶證號
     *
     * @return
     */
    byte[] excelEach(String clientId);

    /**
     * Excel 的 Grid 動態資料
     *
     * @return
     */
    byte[] excelGrid();
}
