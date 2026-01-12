package com.mli.flow.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "案件歷程表")
public class ClaimHistoryVO {
    @Schema(description = "當前流程 UUID")
    private String caseUuid;

    @Schema(description = "主流程 UUID")
    private String mainUuid;

    @Schema(description = "子流程 UUID")
    private String subUuid;

    @Schema(description = "客戶證號")
    private String clientId;

    @Schema(description = "建檔編號")
    private Integer clfpSeq;

    @Schema(description = "流程類型")
    private String flowType;

    @Schema(description = "模組類型")
    private String moduleType;

    @Schema(description = "主流程 目前節點")
    private String mainStatus;

    @Schema(description = "子流程 目前節點")
    private String subStatus;

    @Schema(description = "負責人（案件責任歸屬）")
    private String ownerUser;

    @Schema(description = "意見")
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

    public String getSubUuid() {
        return subUuid;
    }

    public void setSubUuid(String subUuid) {
        this.subUuid = subUuid;
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

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getMainStatus() {
        return mainStatus;
    }

    public void setMainStatus(String mainStatus) {
        this.mainStatus = mainStatus;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
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
