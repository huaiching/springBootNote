package com.mli.flowable.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowableCaseQueryService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FlowableCaseHistoryService flowableCaseHistoryService;

    /**
     * 查詢案件流程資訊
     * @param caseNo 案件編號
     * @return Map 包含流程實例ID, 狀態, 目前任務資訊
     */
    public Map<String, Object> queryCase(String caseNo) {
        // 先找未結案
        Map<String, Object> output = queryActiveCase(caseNo);

        return output != null ? output : queryCompletedCase(caseNo);
    }

    /**
     * 查詢案件流程資訊 (未結案)
     * @param caseNo 案件編號
     * @return Map 包含流程實例ID, 狀態, 目前任務資訊
     */
    public Map<String, Object> queryActiveCase(String caseNo) {

        // 查詢流程實例（運行中）
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("caseProcess")
                .variableValueEquals("caseNo", caseNo)
                .list();

        if (instances == null || instances.isEmpty()) {
            return null; // 沒有該案件流程
        }

        // 目前只取第一個匹配的流程
        ProcessInstance instance = instances.get(0);

        // 查詢該流程當前任務
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(instance.getId())
                .list();

        // 取得第一筆任務
        Task task = tasks.get(0);

        Map<String, Object> result = new HashMap<>();
        result.put("caseNo", caseNo);
        result.put("processInstanceId", instance.getId());
        result.put("taskId", task.getId());
        result.put("taskName", task.getName());
        result.put("assignee", task.getAssignee());

        return result;
    }

    /**
     * 查詢案件流程資訊 (已結案)
     * @param caseNo 案件編號
     * @return Map 包含流程實例ID、最後任務資訊
     */
    public Map<String, Object> queryCompletedCase(String caseNo) {
        List<Map<String, Object>> completedCaseList = flowableCaseHistoryService.queryCaseFullHistory(caseNo);
        // 案件不存在
        if (CollectionUtils.isEmpty(completedCaseList)) {
            return null;
        }

        // 取得歷史流程 最後一筆
        Map<String, Object> completedCase = completedCaseList.get(completedCaseList.size() - 1);

        Map<String, Object> result = new HashMap<>();
        result.put("caseNo", caseNo);
        result.put("processInstanceId", completedCase.get("processInstanceId"));
        result.put("taskId", "");
        result.put("taskName", completedCase.get("activityName"));
        result.put("assignee", completedCase.get("assignee"));

        return result;
    }
}