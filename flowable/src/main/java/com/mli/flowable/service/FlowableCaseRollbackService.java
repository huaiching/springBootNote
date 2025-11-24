package com.mli.flowable.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlowableCaseRollbackService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FlowableCaseQueryService caseQueryService;


    /**
     * 將案件返回上一關
     *
     * @param caseNo 案件編號
     * @return 返回後的當前任務資訊列表
     */
    public List<Map<String, String>> rollbackCase(String caseNo) {

        // Step 1: 查詢流程實例
        var caseInfo = caseQueryService.queryActiveCase(caseNo);
        if (caseInfo == null) {
            throw new RuntimeException("案件不存在或流程未啟動, caseNo=" + caseNo);
        }

        String processInstanceId = (String) caseInfo.get("processInstanceId");

        // Step 2: 查詢當前任務
        List<Task> currentTasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        if (currentTasks.isEmpty()) {
            throw new RuntimeException("案件已完成或無當前任務, caseNo=" + caseNo);
        }

        // Step 3: 假設只有一個當前任務，將流程退回上一個節點
        Task currentTask = currentTasks.get(0);

        // Step 4: 使用 runtimeService 改變活動節點
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdTo(currentTask.getTaskDefinitionKey(), getPreviousActivityId(currentTask))
                .changeState();

        // Step 5: 取得任務資訊 進行回傳
        List<Task> newCurrentTasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        return newCurrentTasks.stream().map(t -> Map.of(
                "taskId", t.getId(),
                "taskName", t.getName(),
                "assignee", t.getAssignee()
        )).collect(Collectors.toList());
    }

    /**
     * 取得上一個活動節點 ID
     * 這裡簡單示例，實務中可能需要 BPMN Model 查找 sequenceFlow
     */
    private String getPreviousActivityId(Task task) {
        // TODO: 根據你的 BPMN 設計返回正確的前置任務 ID
        // 例如 taskFiling -> taskPickup, taskReview -> taskFiling
        switch (task.getTaskDefinitionKey()) {
            case "taskFiling": return "taskPickup";
            case "taskReview": return "taskFiling";
            case "taskApprove": return "taskReview";
            case "taskClose": return "taskApprove";
            default: throw new RuntimeException("無法退回，未知任務: " + task.getTaskDefinitionKey());
        }
    }
}