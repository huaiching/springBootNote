package com.example.api.service.impl;

import com.example.api.constants.SexEnum;
import com.example.api.entity.Clnt;
import com.example.api.repository.ClntRepository;
import com.example.api.service.ExportService;
import com.example.api.util.ExportWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ClntRepository clntRepository;

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
}
