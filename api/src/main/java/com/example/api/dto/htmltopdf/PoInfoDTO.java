package com.example.api.dto.htmltopdf;

public class PoInfoDTO {
    private String policyNo;
    private String poStsCode;
    private String poIssueDate;
    private String paidToDate;
    private String claimInd;
    private String remarkInd;
    private String informInd;
    private String weakInd;

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

    public String getPoIssueDate() {
        return poIssueDate;
    }

    public void setPoIssueDate(String poIssueDate) {
        this.poIssueDate = poIssueDate;
    }

    public String getPaidToDate() {
        return paidToDate;
    }

    public void setPaidToDate(String paidToDate) {
        this.paidToDate = paidToDate;
    }

    public String getClaimInd() {
        return claimInd;
    }

    public void setClaimInd(String claimInd) {
        this.claimInd = claimInd;
    }

    public String getRemarkInd() {
        return remarkInd;
    }

    public void setRemarkInd(String remarkInd) {
        this.remarkInd = remarkInd;
    }

    public String getInformInd() {
        return informInd;
    }

    public void setInformInd(String informInd) {
        this.informInd = informInd;
    }

    public String getWeakInd() {
        return weakInd;
    }

    public void setWeakInd(String weakInd) {
        this.weakInd = weakInd;
    }
}
