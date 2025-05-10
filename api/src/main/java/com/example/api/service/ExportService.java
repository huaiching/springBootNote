package com.example.api.service;

public interface ExportService {

    /**
     * 單筆列印客戶證號明細表
     * @param clientId 客戶證號
     * @return
     */
    byte[] wordTest(String clientId);
}
