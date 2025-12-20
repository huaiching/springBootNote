package com.mli.flow.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "理賠歷史案件")
public class ClaimFlowHistoryVO {
    @Schema(description = "客戶證號")
    private String clientId;

    @Schema(description = "建檔編號")
    private Integer claimSeq;

    @Schema(description = "狀態類型：M.主流程 / S.子流程")
    private String statusType;

    @Schema(description = "主流程 UUID")
    private String mainUuid;

    @Schema(description = "子流程 UUID")
    private String subUuid;

    @Schema(description = "當前 UUID")
    private String caseUuid;

    @Schema(description = "模組類型")
    private String moduleType;

    @Schema(description = "主流程狀態")
    private String mainStatus;

    @Schema(description = "子流程狀態")
    private String subStatus;

    @Schema(description = "效性 : 0.無效 / 1.有效")
    private String valid;

    @Schema(description = "簽核意見")
    private String note;

    @Schema(description = "負責人")
    private String ownerUser;

    @Schema(description = "處理者")
    private String processUser;

    @Schema(description = "處理日期")
    private String processDate;

    @Schema(description = "處理時間")
    private String processTime;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getClaimSeq() {
        return claimSeq;
    }

    public void setClaimSeq(Integer claimSeq) {
        this.claimSeq = claimSeq;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
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

    public String getCaseUuid() {
        return caseUuid;
    }

    public void setCaseUuid(String caseUuid) {
        this.caseUuid = caseUuid;
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

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(String ownerUser) {
        this.ownerUser = ownerUser;
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
