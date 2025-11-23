package com.mli.flowable.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "新增案件回傳用")
public class CaseStartVO {
    @Schema(description = "流程實例 ID")
    private String processInstanceId;
    @Schema(description = "案件編號")
    private String caseNo;
    @Schema(description = "目前所在階段名稱")
    private String currentTaskName;

    public CaseStartVO() {
    }

    public CaseStartVO(String processInstanceId, String caseNo, String currentTaskName) {
        this.processInstanceId = processInstanceId;
        this.caseNo = caseNo;
        this.currentTaskName = currentTaskName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCurrentTaskName() {
        return currentTaskName;
    }

    public void setCurrentTaskName(String currentTaskName) {
        this.currentTaskName = currentTaskName;
    }
}
