package com.example.api.dto;

import com.example.api.entity.Addr;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Addr 更新用物件")
public class AddrUpdate {

    @Schema(description = "變更前 Addr")
    private Addr addrOri;

    @Schema(description = "變更後 Addr")
    private Addr addrNew;

    public Addr getAddrOri() {
        return addrOri;
    }

    public void setAddrOri(Addr addrOri) {
        this.addrOri = addrOri;
    }

    public Addr getAddrNew() {
        return addrNew;
    }

    public void setAddrNew(Addr addrNew) {
        this.addrNew = addrNew;
    }
}
