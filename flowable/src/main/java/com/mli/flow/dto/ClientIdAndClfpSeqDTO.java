package com.mli.flow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "客戶證號 及 建檔編號")
public class ClientIdAndClfpSeqDTO {
    @Schema(description = "客戶證號")
    private String clientId;
    @Schema(description = "建檔編號")
    private Integer clfpSeq;

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
}
