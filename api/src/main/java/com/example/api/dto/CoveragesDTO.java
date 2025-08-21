package com.example.api.dto;

public class CoveragesDTO {
    private Integer coverageNo;
    private String planCode;
    private String rateScale;
    private Double faceAmt;

    public Integer getCoverageNo() {
        return coverageNo;
    }

    public void setCoverageNo(Integer coverageNo) {
        this.coverageNo = coverageNo;
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
}
