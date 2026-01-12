package com.mli.flow.uniquekey;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClaimHistoryKey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "當前流程 UUID")
    private String caseUuid;

    @Schema(description = "主流程 UUID")
    private String mainUuid;

    @Schema(description = "子流程 UUID")
    private String subUuid;

    public ClaimHistoryKey() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClaimHistoryKey that = (ClaimHistoryKey) o;
        return Objects.equals(caseUuid, that.caseUuid) && Objects.equals(mainUuid, that.mainUuid) && Objects.equals(subUuid, that.subUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseUuid, mainUuid, subUuid);
    }
}
