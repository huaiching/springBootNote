package com.mli.flow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "claim_main_status")
@Schema(description = "案件主流程狀態表")
public class ClaimMainStatusEntity {
    @Id
    @Schema(description = "當前流程 UUID")
    @Column(name = "case_uuid")
    private String caseUuid;

    @Schema(description = "主流程 UUID")
    @Column(name = "main_uuid")
    private String mainUuid;

    @Schema(description = "客戶證號")
    @Column(name = "client_id")
    private String clientId;

    @Schema(description = "建檔編號")
    @Column(name = "clfp_seq")
    private Integer clfpSeq;

    @Schema(description = "模組類型")
    @Column(name = "module_type")
    private String moduleType;

    @Schema(description = "目前節點")
    @Column(name = "status")
    private String status;

    @Schema(description = "負責人")
    @Column(name = "owner_user")
    private String ownerUser;

    @Schema(description = "簽核意見")
    @Column(name = "note")
    private String note;

    @Schema(description = "處理者")
    @Column(name = "process_user")
    private String processUser;

    @Schema(description = "處理日期")
    @Column(name = "process_date")
    private String processDate;

    @Schema(description = "處理時間")
    @Column(name = "process_time")
    private String processTime;

    public ClaimMainStatusEntity() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClaimMainStatusEntity that = (ClaimMainStatusEntity) o;
        return Objects.equals(caseUuid, that.caseUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(caseUuid);
    }
}
