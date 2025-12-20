package com.mli.flow.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "claim_sub_status")
@Schema(description = "理賠子流程狀態表")
public class ClaimSubStatusEntity {
    @Id
    @Schema(description = "當前 UUID")
    @Column(name = "case_uuid")
    private String caseUuid;

    @Schema(description = "主流程 UUID")
    @Column(name = "main_uuid")
    private String mainUuid;

    @Schema(description = "子流程 UUID")
    @Column(name = "sub_uuid")
    private String subUuid;

    @Schema(description = "客戶證號")
    @Column(name = "client_id")
    private String clientId;

    @Schema(description = "建檔編號")
    @Column(name = "claim_seq")
    private Integer claimSeq;

    @Schema(description = "模組類型")
    @Column(name = "module_type")
    private String moduleType;

    @Schema(description = "主流程 目前狀態")
    @Column(name = "main_status")
    private String mainStatus;

    @Schema(description = "子流程 目前狀態")
    @Column(name = "sub_status")
    private String subStatus;

    @Schema(description = "效性 : 0.無效 / 1.有效")
    @Column(name = "valid")
    private String valid;

    @Schema(description = "簽核意見")
    @Column(name = "note")
    private String note;

    @Schema(description = "負責人")
    @Column(name = "owner_user")
    private String ownerUser;

    @Schema(description = "處理者")
    @Column(name = "process_user")
    private String processUser;

    @Schema(description = "處理日期")
    @Column(name = "process_date")
    private String processDate;

    @Schema(description = "處理時間")
    @Column(name = "process_time")
    private String processTime;

    public ClaimSubStatusEntity() {
    }

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

    public Integer getClaimSeq() {
        return claimSeq;
    }

    public void setClaimSeq(Integer claimSeq) {
        this.claimSeq = claimSeq;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClaimSubStatusEntity that = (ClaimSubStatusEntity) o;
        return Objects.equals(caseUuid, that.caseUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(caseUuid);
    }
}
