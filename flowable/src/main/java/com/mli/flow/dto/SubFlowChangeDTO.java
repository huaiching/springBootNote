package com.mli.flow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;

@Schema(description = "子流程 異動流程 Input")
public class SubFlowChangeDTO {
    @Schema(description = "子流程 UUID")
    @Column(name = "sub_uuid")
    private String subUuid;

    @Schema(description = "簽核意見")
    private String note;

    @Schema(description = "負責人")
    private String ownerUser;

    @Schema(description = "處理者")
    private String processUser;

    public String getSubUuid() {
        return subUuid;
    }

    public void setSubUuid(String subUuid) {
        this.subUuid = subUuid;
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
}
