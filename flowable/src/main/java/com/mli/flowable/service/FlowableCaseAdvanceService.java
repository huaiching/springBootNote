package com.mli.flowable.service;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowableCaseAdvanceService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FlowableCaseQueryService caseQueryService;

    /**
     * 將案件送至下一關
     *
     * @param caseNo 案件編號
     * @return 下一關任務資訊列表
     */
    public Map<String, String> advanceCase(String caseNo) {

        // 取得 案件資料
        Map<String, Object> caseInfo = caseQueryService.queryActiveCase(caseNo);
        if (caseInfo == null) {
            throw new RuntimeException("案件不存在或流程未啟動, caseNo=" + caseNo);
        }

        String processInstanceId = (String) caseInfo.get("processInstanceId");

        // 判斷 案件狀態
        List<Task> currentTasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        if (currentTasks.isEmpty()) {
            throw new RuntimeException("案件已完成或無當前任務, caseNo=" + caseNo);
        }

        Task task = currentTasks.get(0);

        // 若為 結束照會，要將 outcome 改回 pass
        if (caseInfo.get("taskName").equals("照會回復")) {
            taskService.setVariable(task.getId(), "outcome", "pass");
        }

        // 完成 當前任務
        taskService.complete(task.getId());


        // 取得任務資訊 進行回傳
        List<Task> nextTasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        Task nextTask = nextTasks.get(0);

        Map<String, String> nextData = new HashMap<>();
        nextData.put("taskId", nextTask.getId());
        nextData.put("taskName", nextTask.getName());
        nextData.put("assignee", nextTask.getAssignee());
        nextData.put("processInstanceId", nextTask.getProcessInstanceId());

        return nextData;
    }

    /**
     * 送至照會
     *
     * @param caseNo 案件編號
     * @return 下一關任務資訊列表
     */
    public Map<String, String> toConsult(String caseNo) {

        // 取得 案件資料
        Map<String, Object> caseInfo = caseQueryService.queryActiveCase(caseNo);
        if (caseInfo == null) {
            throw new RuntimeException("案件不存在或流程未啟動, caseNo=" + caseNo);
        }

        // 不是 審核關卡 就離開
        if (!caseInfo.get("taskName").equals("審核")) {
            throw new RuntimeException("非審核不可進行照會, caseNo=" + caseNo);
        }

        // 取得當前任務
        String processInstanceId = (String) caseInfo.get("processInstanceId");
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        // 調整 outcome 為 consult
        taskService.setVariable(task.getId(), "outcome", "consult");

        return advanceCase(caseNo);
    }

}