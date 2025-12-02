package com.mli.flow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "claim_history")
@Schema(description = "案件歷程表")
public class ClaimHistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Schema(description = "UUID")
    @Column(name = "uuid")
    private String uuid;

    @Schema(description = "客戶證號")
    @Column(name = "client_id")
    private String clientId;

    @Schema(description = "建檔編號")
    @Column(name = "claim_seq")
    private Integer claimSeq;

    @Schema(description = "目前節點")
    @Column(name = "status")
    private String status;

    @Schema(description = "處理者")
    @Column(name = "process_user")
    private String processUser;

    @Schema(description = "處理日期")
    @Column(name = "process_date")
    private String processDate;

    @Schema(description = "處理時間")
    @Column(name = "process_time")
    private String processTime;


    public ClaimHistoryEntity() {}

    public String getUuid() {
        return uuid!= null ? uuid.trim() : null;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return clientId!= null ? clientId.trim() : null;
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

    public String getStatus() {
        return status!= null ? status.trim() : null;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessUser() {
        return processUser!= null ? processUser.trim() : null;
    }

    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }

    public String getProcessDate() {
        return processDate!= null ? processDate.trim() : null;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getProcessTime() {
        return processTime!= null ? processTime.trim() : null;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimHistoryEntity that = (ClaimHistoryEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    // 主鍵 實體類
    public static class ClaimHistoryKey implements Serializable {
        private static final long serialVersionUID = 1L;

        private String uuid;

        public ClaimHistoryKey() {}

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClaimHistoryKey that = (ClaimHistoryKey) o;
            return Objects.equals(uuid, that.uuid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uuid);
        }
    }

    // update 實體類
    public static class ClaimHistoryUpdate implements Serializable {
        private static final long serialVersionUID = 1L;

        private ClaimHistoryEntity claimhistoryOri;
        private ClaimHistoryEntity claimhistoryNew;

        public ClaimHistoryEntity getClaimHistoryOri() {
            return claimhistoryOri;
        }

        public void setClaimHistoryOri(ClaimHistoryEntity claimhistoryOri) {
            claimhistoryOri = claimhistoryOri;
        }

        public ClaimHistoryEntity getClaimHistoryNew() {
            return claimhistoryNew;
        }

        public void setClaimHistoryNew(ClaimHistoryEntity claimhistoryNew) {
            claimhistoryNew = claimhistoryNew;
        }

    }
}
