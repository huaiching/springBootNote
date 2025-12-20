package com.mli.flow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "flow_definition")
@Schema(description = "流程定義表")
public class FlowDefinitionEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Schema(description = "模組類型")
    @Column(name = "module_type")
    private String moduleType;

    @Schema(description = "狀態類型：M.主流程 / S.子流程")
    @Column(name = "status_type")
    private String statusType;

    @Schema(description = "目前狀態")
    @Column(name = "current_status")
    private String currentStatus;

    @Schema(description = "目前狀態中文")
    @Column(name = "current_status_desc")
    private String currentStatusDesc;

    @Schema(description = "下一狀態")
    @Column(name = "next_status")
    private String nextStatus;

    @Schema(description = "上一狀態")
    @Column(name = "previous_staus")
    private String previousStaus;

    @Schema(description = "檢核規則")
    @Column(name = "expression_rule")
    private String expressionRule;

    public FlowDefinitionEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatusDesc() {
        return currentStatusDesc;
    }

    public void setCurrentStatusDesc(String currentStatusDesc) {
        this.currentStatusDesc = currentStatusDesc;
    }

    public String getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(String nextStatus) {
        this.nextStatus = nextStatus;
    }

    public String getPreviousStaus() {
        return previousStaus;
    }

    public void setPreviousStaus(String previousStaus) {
        this.previousStaus = previousStaus;
    }

    public String getExpressionRule() {
        return expressionRule;
    }

    public void setExpressionRule(String expressionRule) {
        this.expressionRule = expressionRule;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FlowDefinitionEntity that = (FlowDefinitionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
