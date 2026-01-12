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
    private String module_type;

    @Schema(description = "流程類型")
    @Column(name = "flow_type")
    private String flowType;

    @Schema(description = "目前狀態代碼")
    @Column(name = "current_status")
    private String currentStatus;

    @Schema(description = "目前狀態中文")
    @Column(name = "current_status_desc")
    private String currentStatusDesc;

    @Schema(description = "下一節點代碼")
    @Column(name = "next_status")
    private String nextStatus;

    @Schema(description = "上一節點代碼")
    @Column(name = "prev_status")
    private String prevStatus;

    @Schema(description = "檢核規則")
    @Column(name = "expression")
    private String expression;

    public FlowDefinitionEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModule_type() {
        return module_type;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
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

    public String getPrevStatus() {
        return prevStatus;
    }

    public void setPrevStatus(String prevStatus) {
        this.prevStatus = prevStatus;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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
