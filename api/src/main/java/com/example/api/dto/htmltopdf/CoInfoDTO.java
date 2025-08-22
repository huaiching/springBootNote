package com.example.api.dto.htmltopdf;

public class CoInfoDTO {
    private String clientIdent;
    private String planCode;
    private String rateScale;
    private Double faceAmt;
    private String coIssueDate;
    private String coChangeDate;

    public String getClientIdent() {
        return clientIdent;
    }

    public void setClientIdent(String clientIdent) {
        this.clientIdent = clientIdent;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getRateScale() {
        return rateScale;
    }

    public void setRateScale(String rateScale) {
        this.rateScale = rateScale;
    }

    public Double getFaceAmt() {
        return faceAmt;
    }

    public void setFaceAmt(Double faceAmt) {
        this.faceAmt = faceAmt;
    }

    public String getCoIssueDate() {
        return coIssueDate;
    }

    public void setCoIssueDate(String coIssueDate) {
        this.coIssueDate = coIssueDate;
    }

    public String getCoChangeDate() {
        return coChangeDate;
    }

    public void setCoChangeDate(String coChangeDate) {
        this.coChangeDate = coChangeDate;
    }
}
