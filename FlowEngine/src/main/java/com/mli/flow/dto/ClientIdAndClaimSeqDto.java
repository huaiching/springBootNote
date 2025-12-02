package com.mli.flow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "申請人ID 及 建檔編號")
public class ClientIdAndClaimSeqDto {
    @Schema(description = "申請人ID")
    private String clientId;
    @Schema(description = "建檔編號")
    private Integer claimSql;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getClaimSql() {
        return claimSql;
    }

    public void setClaimSql(Integer claimSql) {
        this.claimSql = claimSql;
    }
}
