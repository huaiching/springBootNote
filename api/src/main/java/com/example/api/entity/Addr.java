package com.example.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.IdClass;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDateTime;

@Entity
@Table(name = "addr")
@IdClass(Addr.AddrKey.class)
@Schema(description = "客戶地址檔")
public class Addr implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Schema(description = "客戶姓名")
    @Column(name = "client_id")
    private String clientId;

    @Id
    @Schema(description = "地址指示")
    @Column(name = "addr_ind")
    private String addrInd;

    @Schema(description = "地址")
    @Column(name = "address")
    private String address;

    @Schema(description = "電話")
    @Column(name = "tel")
    private String tel;

    // 無參數建構式: IDE 生成
    public Addr() {
    }

    // getting 和 setting: IDE 生成
    public String getClientId() {
        return clientId!= null ? clientId.trim() : null;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAddrInd() {
        return addrInd!= null ? addrInd.trim() : null;
    }

    public void setAddrInd(String addrInd) {
        this.addrInd = addrInd;
    }

    public String getAddress() {
        return address!= null ? address.trim() : null;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel!= null ? tel.trim() : null;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    // 主鍵的 equals 和 hashCode: IDE 生成
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addr that = (Addr) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(addrInd, that.addrInd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, addrInd);
    }

    // 主鍵 實體類
    public static class AddrKey implements Serializable {
        private static final long serialVersionUID = 1L;

        private String clientId;
        private String addrInd;

        public AddrKey() {
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getAddrInd() {
            return addrInd;
        }

        public void setAddrInd(String addrInd) {
            this.addrInd = addrInd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AddrKey that = (AddrKey) o;
            return Objects.equals(clientId, that.clientId) && Objects.equals(addrInd, that.addrInd);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clientId, addrInd);
        }
    }
}
