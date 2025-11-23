package com.mli.flowable.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FlowableCaseCreateService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FlowableCaseQueryService caseQueryService;

    /**
     * 新增案件
     * 1. 先查 Flowable DB 是否已有相同 caseNo
     * 2. 若不存在 → 啟動流程
     *
     * @param caseNo 案件編號
     * @return processInstanceId
     */
    public String createCase(String caseNo, String assignee) {

        // Step 1: 查詢 Flowable DB 是否已存在
        Map<String, Object> existing = caseQueryService.queryActiveCase(caseNo);
        if (existing != null) {
            throw new RuntimeException("案件已存在於流程中, caseNo=" + caseNo);
        }

        // Step 2: 準備流程變數
        Map<String, Object> vars = new HashMap<>();
        vars.put("caseNo", caseNo);
        vars.put("assignee", assignee);        // 任務負責人
        vars.put("outcome", "pass");
        vars.put("consultAssignee", "");

        // Step 3: 啟動流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("caseProcess", vars);

        return instance.getId();
    }
}