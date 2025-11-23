package com.mli.flowable.dto;

public class CaseDto {
    private String caseNo;      // 案件編號
    private String title;       // 案件標題
    private String description; // 案件說明
    private String creator;     // 建立者 (當作 assignee)

    // 建構子
    public CaseDto() {}

    public CaseDto(String caseNo, String title, String description, String creator) {
        this.caseNo = caseNo;
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    // Getter & Setter
    public String getCaseNo() { return caseNo; }
    public void setCaseNo(String caseNo) { this.caseNo = caseNo; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }
}
