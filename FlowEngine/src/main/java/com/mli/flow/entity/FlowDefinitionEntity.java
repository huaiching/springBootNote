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
public class FlowDefinitionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Schema(description = "流程類型")
    @Column(name = "flow_type")
    private String flowType;

    @Schema(description = "目前節點代碼")
    @Column(name = "current_status")
    private String currentStatus;

    @Schema(description = "下一節點代碼")
    @Column(name = "next_status")
    private String nextStatus;

    @Schema(description = "上一節點代碼")
    @Column(name = "prew_status")
    private String prewStatus;

    @Schema(description = "SpEL")
    @Column(name = "spel_expression")
    private String spelExpression;


    public FlowDefinitionEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlowType() {
        return flowType!= null ? flowType.trim() : null;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getCurrentStatus() {
        return currentStatus!= null ? currentStatus.trim() : null;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getNextStatus() {
        return nextStatus!= null ? nextStatus.trim() : null;
    }

    public void setNextStatus(String nextStatus) {
        this.nextStatus = nextStatus;
    }

    public String getPrewStatus() {
        return prewStatus!= null ? prewStatus.trim() : null;
    }

    public void setPrewStatus(String prewStatus) {
        this.prewStatus = prewStatus;
    }

    public String getSpelExpression() {
        return spelExpression!= null ? spelExpression.trim() : null;
    }

    public void setSpelExpression(String spelExpression) {
        this.spelExpression = spelExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowDefinitionEntity that = (FlowDefinitionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // 主鍵 實體類
    public static class FlowDefinitionKey implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer id;

        public FlowDefinitionKey() {}

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FlowDefinitionKey that = (FlowDefinitionKey) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    // update 實體類
    public static class FlowDefinitionUpdate implements Serializable {
        private static final long serialVersionUID = 1L;

        private FlowDefinitionEntity flowdefinitionOri;
        private FlowDefinitionEntity flowdefinitionNew;

        public FlowDefinitionEntity getFlowDefinitionOri() {
            return flowdefinitionOri;
        }

        public void setFlowDefinitionOri(FlowDefinitionEntity flowdefinitionOri) {
            flowdefinitionOri = flowdefinitionOri;
        }

        public FlowDefinitionEntity getFlowDefinitionNew() {
            return flowdefinitionNew;
        }

        public void setFlowDefinitionNew(FlowDefinitionEntity flowdefinitionNew) {
            flowdefinitionNew = flowdefinitionNew;
        }

    }
}
