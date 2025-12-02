package com.mli.flow.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "案件歷程表")
public class ClaimHistoryVo {
    @Schema(description = "UUID")
    private String uuid;

    @Schema(description = "客戶證號")
    private String clientId;

    @Schema(description = "建檔編號")
    private String claimSeq;

    @Schema(description = "目前節點")
    private String status;

    @Schema(description = "目前節點中文")
    private String statusDesc;

    @Schema(description = "處理者")
    private String processUser;

    @Schema(description = "處理日期")
    private String processDate;

    @Schema(description = "處理時間")
    private String processTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClaimSeq() {
        return claimSeq;
    }

    public void setClaimSeq(String claimSeq) {
        this.claimSeq = claimSeq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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
