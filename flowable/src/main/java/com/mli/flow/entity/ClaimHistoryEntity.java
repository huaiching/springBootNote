package com.mli.flow.entity;

import javax.persistence.*;

import com.mli.flow.uniquekey.ClaimHistoryKey;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "claim_history")
@IdClass(ClaimHistoryKey.class)
@Schema(description = "案件歷程表")
public class ClaimHistoryEntity {
    @Id
    @Schema(description = "當前流程 UUID")
    @Column(name = "case_uuid")
    private String caseUuid;

    @Id
    @Schema(description = "主流程 UUID")
    @Column(name = "main_uuid")
    private String mainUuid;

    @Id
    @Schema(description = "子流程 UUID")
    @Column(name = "sub_uuid")
    private String subUuid;

    @Schema(description = "客戶證號")
    @Column(name = "client_id")
    private String clientId;

    @Schema(description = "建檔編號")
    @Column(name = "clfp_seq")
    private Integer clfpSeq;

    @Schema(description = "流程類型")
    @Column(name = "flow_type")
    private String flowType;

    @Schema(description = "模組類型")
    @Column(name = "module_type")
    private String moduleType;

    @Schema(description = "主流程 目前節點")
    @Column(name = "main_status")
    private String mainStatus;

    @Schema(description = "子流程 目前節點")
    @Column(name = "sub_status")
    private String subStatus;

    @Schema(description = "負責人")
    @Column(name = "owner_user")
    private String ownerUser;

    @Schema(description = "意見")
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

    public ClaimHistoryEntity() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClaimHistoryEntity that = (ClaimHistoryEntity) o;
        return Objects.equals(caseUuid, that.caseUuid) && Objects.equals(mainUuid, that.mainUuid) && Objects.equals(subUuid, that.subUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseUuid, mainUuid, subUuid);
    }
}
