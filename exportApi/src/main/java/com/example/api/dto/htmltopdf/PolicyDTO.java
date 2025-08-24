package com.example.api.dto.htmltopdf;

import java.util.List;

public class PolicyDTO {
    private PoInfoDTO poInfo;
    private List<CoInfoDTO> coInfoList;

    public PoInfoDTO getPoInfo() {
        return poInfo;
    }

    public void setPoInfo(PoInfoDTO poInfo) {
        this.poInfo = poInfo;
    }

    public List<CoInfoDTO> getCoInfoList() {
        return coInfoList;
    }

    public void setCoInfoList(List<CoInfoDTO> coInfoList) {
        this.coInfoList = coInfoList;
    }
}
