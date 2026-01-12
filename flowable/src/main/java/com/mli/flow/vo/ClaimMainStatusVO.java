package com.mli.flow.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "案件主流程狀態表")
public class ClaimMainStatusVO {
    @Schema(description = "當前流程 UUID")
    private String caseUuid;

    @Schema(description = "主流程 UUID")
    private String mainUuid;

    @Schema(description = "客戶證號")
    private String clientId;

    @Schema(description = "建檔編號")
    private Integer clfpSeq;

    @Schema(description = "模組類型")
    private String moduleType;

    @Schema(description = "目前節點")
    private String status;

    @Schema(description = "負責人")
    private String ownerUser;

    @Schema(description = "簽核意見")
    private String note;

    @Schema(description = "處理者")
    private String processUser;

    @Schema(description = "處理日期")
    private String processDate;

    @Schema(description = "處理時間")
    private String processTime;

    public String getCaseUuid() {
        return caseUuid;
    }

    public void setCaseUuid(String caseUuid) {
        this.caseUuid = caseUuid;
    }

    public String getMainUuid() {
        return mainUuid;
    }

    public void setMainUuid(String mainUuid) {
        this.mainUuid = mainUuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getClfpSeq() {
        return clfpSeq;
    }

    public void setClfpSeq(Integer clfpSeq) {
        this.clfpSeq = clfpSeq;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(String ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProcessUser() {
        return processUser;
    }

    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }
}
