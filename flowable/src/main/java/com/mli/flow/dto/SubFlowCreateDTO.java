package com.mli.flow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "子流程 新增流程 Input")
public class SubFlowCreateDTO {
    @Schema(description = "模組類型")
    private String moduleType;

    @Schema(description = "客戶證號")
    private String clientId;

    @Schema(description = "建檔編號")
    private Integer clfpSeq;

    @Schema(description = "負責人")
    private String ownerUser;

    @Schema(description = "處理者")
    private String processUser;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
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
