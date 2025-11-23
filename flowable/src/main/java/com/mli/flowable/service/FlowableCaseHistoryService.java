package com.mli.flowable.service;

import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlowableCaseHistoryService {

    @Autowired
    private HistoryService historyService;

    /**
     * 查詢案件完整歷史
     *
     * @param caseNo 案件編號
     * @return List<Map> 每筆 Map 包含節點ID、節點名稱、負責人、開始/結束時間、流程實例ID
     */
    public List<Map<String, Object>> queryCaseFullHistory(String caseNo) {

        // 查詢歷史流程
        List<HistoricProcessInstance> historicInstances = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("caseProcess")
                .variableValueEquals("caseNo", caseNo)
                .orderByProcessInstanceStartTime()
                .asc()
                .list();

        // 遍歷每個流程實例
        return historicInstances.stream().flatMap(instance -> {

            // 查詢每個流程實例的所有活動節點歷史
            List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instance.getId())
                    .orderByHistoricActivityInstanceStartTime()
                    .asc()
                    .list();

            // 將每個節點轉為 Map
            return activities.stream()
                    .filter(act -> !act.getActivityId().matches("flow.*"))          // 排除 連線
                    .filter(act -> !act.getActivityId().matches(".*Event"))         // 排除 起點 跟 終點
                    .filter(act -> !act.getActivityId().matches("gateway.*"))       // 排除 決策節點
                    .filter(act -> !act.getActivityId().matches("subProcess.*"))    // 排除 子流程 subProcess
                    .filter(act -> act.getActivityName() != null)
                    .map(act -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("processInstanceId", act.getProcessInstanceId());
                        map.put("activityName", act.getActivityName());
                        map.put("assignee", act.getAssignee());   // 任務負責人
                        map.put("startTime", act.getStartTime());
                        map.put("endTime", act.getEndTime());
                        return map;
                    });

        }).collect(Collectors.toList());
    }
}
