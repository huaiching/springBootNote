package com.mli.flowable.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.flowable.task.api.Task;

import java.text.SimpleDateFormat;

@Schema(description = "待辦任務列表用")
public class TaskVO {
    @Schema(description = "Flowable 任務 ID", example = "c1d2e3f4-1234-5678-9012-abcdef123456")
    private String taskId;

    @Schema(description = "任務名稱（階段名稱）", example = "取件")
    private String taskName;

    @Schema(description = "所屬流程實例 ID", example = "b0e8f8a0-9d5d-11ee-8c8e-0242ac110002")
    private String processInstanceId;

    @Schema(description = "目前負責人（登入帳號）", example = "john")
    private String assignee;

    @Schema(description = "任務建立時間", example = "2025-11-21 16:30:25")
    private String createTime;

    public TaskVO() {
    }

    public TaskVO(Task task) {this.taskId = task.getId();
        this.taskName = task.getName();
        this.processInstanceId = task.getProcessInstanceId();
        this.assignee = task.getAssignee();

        // 時間格式化（台灣時區）
        if (task.getCreateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.createTime = sdf.format(task.getCreateTime());
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
