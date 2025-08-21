package com.example.api.dto;

import java.util.List;

public class PolicyDTO {
    private String policyNo;
    private String poStsCode;
    private String o1Name;
    private String i1Name;
    private String poIssueDate;
    private List<CoveragesDTO> coList;

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getPoStsCode() {
        return poStsCode;
    }

    public void setPoStsCode(String poStsCode) {
        this.poStsCode = poStsCode;
    }

    public String getO1Name() {
        return o1Name;
    }

    public void setO1Name(String o1Name) {
        this.o1Name = o1Name;
    }

    public String getI1Name() {
        return i1Name;
    }

    public void setI1Name(String i1Name) {
        this.i1Name = i1Name;
    }

    public String getPoIssueDate() {
        return poIssueDate;
    }

    public void setPoIssueDate(String poIssueDate) {
        this.poIssueDate = poIssueDate;
    }

    public List<CoveragesDTO> getCoList() {
        return coList;
    }

    public void setCoList(List<CoveragesDTO> coList) {
        this.coList = coList;
    }
}
