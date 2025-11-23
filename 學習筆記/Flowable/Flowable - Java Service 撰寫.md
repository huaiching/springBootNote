# Flowable - Java Service 的撰寫

本章節詳細說明如何撰寫 Java Service 來控制和操作 Flowable 的 BPMN 流程。

## Flowable 核心 Service 介紹

Flowable 提供了多個核心 Service 來操作流程引擎：

```mermaid
flowchart LR
    %% --- 節點定義 ---
    Flowable(("Flowable <br> 流程管理引擎"))
    RepositoryService["RepositoryService <br> 靜態資源"]
    RuntimeService["RuntimeService <br> 執行中流程"]  
    TaskService["TaskService <br> 用戶任務"] 
    HistoryService["HistoryService <br> 歷史查詢"]  
    ManagementService["ManagementService <br> 監控"]

    %% --- 主流程連線 (Sequence Flows) ---    
    Flowable --- RepositoryService
    Flowable --- RuntimeService
    Flowable --- TaskService
    HistoryService --- Flowable 
    ManagementService --- Flowable 
```

| Service               | 說明                         |
| --------------------- | -------------------------- |
| **RepositoryService** | 管理流程定義、部署、模型               |
| **RuntimeService**    | 管理流程實例的執行、查詢執行中的流程         |
| **TaskService**       | 管理用戶任務（UserTask）的簽收、完成、委派等 |
| **HistoryService**    | 查詢歷史數據（已完成的流程、任務等）         |
| **ManagementService** | 管理和監控引擎（Job、定時器等）          |

## 常用方法

### RepositoryService - 靜態資源

<details>
<summary>createDeployment - 部署流程（classPath 方式）</summary>

`createDeployment()` <br> → `addClasspathResource(String path)` （加入 classpath 下的 BPMN 資源） <br> → `deploy()` （執行部署）

- `path`（BPMN 路徑, classpath 位置）

</details>

<details>
<summary>addInputStream - 部署流程（InputStream 方式）</summary>

`addInputStream(String resourceName, InputStream inputStream)`

- `resourceName`（資源名稱）
- `inputStream`（BPMN XML InputStream）

</details>

<details>
<summary>deleteDeployment - 刪除流程部署</summary>

`deleteDeployment(String deploymentId, boolean cascade)`

- `deploymentId`（部署 ID）
- `cascade`（是否刪除相關流程實例）

</details>

<details>
<summary>createProcessDefinitionQuery - 查詢流程定義</summary>

`createProcessDefinitionQuery()`

</details>

<details>
<summary>getProcessDiagram - 取得流程圖 InputStream</summary>

`getProcessDiagram(String processDefinitionId)`

- `processDefinitionId`（流程定義 ID）

</details>

<details>
<summary>suspendProcessDefinitionById - 暫停流程定義</summary>

`suspendProcessDefinitionById(String id)`

- `id`（流程定義 ID）

</details>

<details>
<summary>activateProcessDefinitionById - 激活流程定義</summary>

`activateProcessDefinitionById(String id)`

- `id`（流程定義 ID）

</details>

<details>
<summary>getBpmnModel - 取得 BPMN 模型物件（程式控制用）</summary>

`getBpmnModel(String processDefinitionId)`

- `processDefinitionId`（流程定義 ID）

</details>

<details>
<summary>getProcessModel - 取得 BPMN XML InputStream</summary>

`getProcessModel(String processDefinitionId)`

- `processDefinitionId`（流程定義 ID）

</details>

### RuntimeService - 執行中流程

<details>
<summary>startProcessInstanceByKey - 啟動流程實例</summary>

`startProcessInstanceByKey(String key, Map vars)`

- `key`（BPMN 流程的 ID）
- `vars`（初始流程變數 Map） 【選填】

</details>

<details>
<summary>setVariable - 設定單一流程變數（全域變數）</summary>

`setVariable(String execId, String varName, Object value)`

- `execId`（流程執行 ID）
- `varName`（變數名稱）
- `value`（變數值）

</details>

<details>
<summary>setVariables - 設定多個流程變數（全域變數）</summary>

`setVariables(String execId, Map vars)`

- `execId`（流程執行 ID）
- `vars`（多個變數 Map）

</details>

<details>
<summary>getVariable - 取得單一流程變數（全域變數）</summary>

`getVariable(String execId, String varName)`

- `execId`（流程執行 ID）
- `varName`（變數名稱）

</details>

<details>
<summary>getVariables - 取得全部流程變數（全域變數）</summary>

`getVariables(String execId)`

- `execId`（流程執行 ID）

</details>

<details>
<summary>deleteProcessInstance - 刪除流程實例</summary>

`deleteProcessInstance(String processInstanceId, String reason)`

- `processInstanceId`（流程實例 ID）
- `reason`（刪除原因）

</details>

<details>
<summary>createProcessInstanceQuery - 查詢流程實例</summary>

`createProcessInstanceQuery()`

</details>

<details>
<summary>createChangeActivityStateBuilder - 將流程從當前節點跳轉到指定節點，可退回上一關或跳關</summary>

`createChangeActivityStateBuilder()` <br> → `processInstanceId(String processInstanceId)` （指定流程實例） <br> → `moveActivityIdTo(String currentActivityId, String targetActivityId)` （從當前節點跳到目標節點） <br> → `changeState()` （執行跳轉）

- `processInstanceId`（流程實例 ID）
- `currentActivityId`（當前節點 ID）
- `targetActivityId`（目標節點 ID）

</details>

### TaskService - 用戶任務

<details>
<summary>createTaskQuery - 查詢任務</summary>

`createTaskQuery()`

</details>

<details>
<summary>complete - 完成任務</summary>

`complete(String taskId)`

- `taskId`（任務 ID）

</details>

<details>
<summary>complete - 完成任務（寫入流程變數）</summary>

`complete(String taskId, Map vars)`

- `taskId`（任務 ID）
- `vars`（流程變數 Map）

</details>

<details>
<summary>claim - 簽收任務</summary>

`claim(String taskId, String userId)`

- `taskId`（任務 ID）
- `userId`（使用者 ID）

</details>

<details>
<summary>unclaim - 取消簽收</summary>

`claim(String taskId, String userId)`

- `taskId`（任務 ID）

</details>

<details>
<summary>setAssignee - 指派任務負責人</summary>

`setAssignee(String taskId, String userId)`

- `taskId`（任務 ID）
- `userId`（負責人 ID）

</details>

<details>
<summary>delegateTask - 委派任務</summary>

`delegateTask(String taskId, String userId)`

- `taskId`（任務 ID）
- `userId`（受委派使用者 ID）

</details>

<details>
<summary>setOwner - 設定任務擁有者</summary>

`setOwner(String taskId, String userId)`

- `taskId`（任務 ID）
- `userId`（擁有者 ID）

</details>

<details>
<summary>setVariable - 設定單一流程變數（當前任務的區域變數）</summary>

`setVariable(String execId, String varName, Object value)`

- `taskId`（任務 ID）
- `varName`（變數名稱）
- `value`（變數值）

</details>

<details>
<summary>setVariables - 設定多個流程變數（當前任務的區域變數）</summary>

`setVariables(String execId, Map vars)`

- `taskId`（任務 ID）
- `vars`（多個變數 Map）

</details>

<details>
<summary>getVariable - 取得單一流程變數（當前任務的區域變數）</summary>

`getVariable(String execId, String varName)`

- `taskId`（任務 ID）
- `varName`（變數名稱）

</details>

<details>
<summary>getVariables - 取得全部流程變數（當前任務的區域變數）</summary>

`getVariables(String execId)`

- `taskId`（任務 ID）

</details>

<details>
<summary>createHistoricProcessInstanceQuery - 查詢歷史流程</summary>

`createHistoricProcessInstanceQuery()`

</details>

<details>
<summary>createHistoricTaskInstanceQuery - 查詢歷史任務</summary>

`createHistoricTaskInstanceQuery()`

</details>

<details>
<summary>createHistoricActivityInstanceQuery - 查詢歷史活動（含 userTask/網關/ServiceTask）</summary>

`createHistoricActivityInstanceQuery()`

</details>

<details>
<summary>createHistoricVariableInstanceQuery - 查詢歷史變數</summary>

`createHistoricVariableInstanceQuery()`

</details>

<details>
<summary>deleteHistoricProcessInstance - 刪除歷史流程資料</summary>

`deleteHistoricProcessInstance(String processInstanceId)`

- `processInstanceId`（流程實例 ID）

</details>

### HistoryService - 歷史資料

<details>
<summary>createHistoricProcessInstanceQuery - 查詢歷史流程實例</summary>

`createHistoricProcessInstanceQuery()` <br> → `processInstanceId(String processInstanceId)` （依流程實例 ID 查詢） <br> → `list()` / `singleResult()` （取得結果）

- `processInstanceId`（流程實例 ID）

</details>

<details>
<summary>createHistoricTaskInstanceQuery - 查詢歷史任務實例</summary>

`createHistoricTaskInstanceQuery()` <br> → `taskAssignee(String assignee)` （依任務執行人查詢） <br> → `list()` / `singleResult()` （取得結果）

- `assignee`（任務執行人）

</details>

<details>
<summary>createHistoricActivityInstanceQuery - 查詢歷史活動實例</summary>

`createHistoricActivityInstanceQuery()` <br> → `processInstanceId(String processInstanceId)` （依流程實例 ID 查詢） <br> → `list()` （取得結果）

- `processInstanceId`（流程實例 ID）

</details>

<details>
<summary>createHistoricVariableInstanceQuery - 查詢歷史變數實例</summary>

`createHistoricVariableInstanceQuery()` <br> → `processInstanceId(String processInstanceId)` （依流程實例 ID 查詢） <br> → `list()` （取得結果）

- `processInstanceId`（流程實例 ID）

</details>

<details>
<summary>deleteHistoricProcessInstance - 刪除歷史流程實例</summary>

`deleteHistoricProcessInstance(String processInstanceId)`

- `processInstanceId`（流程實例 ID）

</details>

<details>
<summary>deleteHistoricTaskInstance - 刪除歷史任務實例</summary>

`deleteHistoricTaskInstance(String taskId)`

- `taskId`（任務 ID）

</details>

<details>
<summary>createHistoricDetailQuery - 查詢歷史詳細資料</summary>

`createHistoricDetailQuery()` <br> → `processInstanceId(String processInstanceId)` （依流程實例 ID 查詢） <br> → `list()` （取得結果）

- `processInstanceId`（流程實例 ID）

</details>

### ManagementService - 監控

<details>
<summary>createJobQuery - 查詢異步 Job</summary>

`createJobQuery()`

</details>

<details>
<summary>executeJob - 手動執行 Job</summary>

`executeJob(String jobId)`

- `jobId`（Job ID）

</details>

<details>
<summary>deleteJob - 刪除 Job</summary>

`deleteJob(String jobId)`

- `jobId`（Job ID）

</details>

<details>
<summary>createTimerJobQuery - 查詢 Timer Job</summary>

`createTimerJobQuery()`

</details>

<details>
<summary>getTableCount - 取得所有 Flowable 表的筆數</summary>

`getTableCount()`

</details>

## 範例

</details>

<details>
<summary>案件查詢</summary>

使用 `RuntimeService` 的 `createProcessInstanceQuery()` 進行案件查詢：

- **processDefinitionKey**: 指定 BPMN 流程的 ID
- **variableValueEquals**: 透過變數名稱與變數值作為查詢條件

```java
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
```

</details>

<details>
<summary>新增案件</summary>

使用 `RuntimeService` 的 `startProcessInstanceByKey()` 啟動流程實例：

- 第 1 個參數：BPMN 流程的 ID
- 第 2 個參數：流程變數（`Map<String, Object>`），會保存到 Flowable 的資料表中

```java
package com.mli.flowable.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FlowableCaseCreateService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FlowableCaseQueryService caseQueryService;

    /**
     * 新增案件
     * 1. 先查詢 Flowable DB 是否已有相同 caseNo
     * 2. 若不存在 → 啟動流程
     *
     * @param caseNo 案件編號
     * @param assignee 任務負責人
     * @return processInstanceId
     */
    public String createCase(String caseNo, String assignee) {

        // Step 1: 查詢 Flowable DB 是否已存在
        Map<String, Object> existing = caseQueryService.queryActiveCase(caseNo);
        if (existing != null) {
            throw new RuntimeException("案件已存在於流程中, caseNo=" + caseNo);
        }

        // Step 2: 準備流程變數
        Map<String, Object> vars = new HashMap<>();
        vars.put("caseNo", caseNo);
        vars.put("assignee", assignee);        // 任務負責人
        vars.put("outcome", "pass");
        vars.put("consultAssignee", "");

        // Step 3: 啟動流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("caseProcess", vars);

        return instance.getId();
    }
}
```

</details>

<details>
<summary>送至下一關 / 送至照會</summary>

使用 `TaskService` 完成當前任務，讓流程前進到下一個節點：

- 使用 `createTaskQuery()` 配合 `processInstanceId()` 查詢案件當前任務
  - 透過案件查詢取得 `processInstanceId`
  - 無資料代表流程已完成或案件不存在
- 使用 `complete(taskId)` 完成任務，讓流程前進至下一關
- 可透過 `setVariable()` 在完成任務前設定流程變數

```java
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
     * @return 下一關任務資訊
     */
    public Map<String, String> advanceCase(String caseNo) {

        // 取得案件資料
        Map<String, Object> caseInfo = caseQueryService.queryActiveCase(caseNo);
        if (caseInfo == null) {
            throw new RuntimeException("案件不存在或流程未啟動, caseNo=" + caseNo);
        }

        String processInstanceId = (String) caseInfo.get("processInstanceId");

        // 判斷案件狀態
        List<Task> currentTasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        if (currentTasks.isEmpty()) {
            throw new RuntimeException("案件已完成或無當前任務, caseNo=" + caseNo);
        }

        Task task = currentTasks.get(0);

        // 若為結束照會，要將 outcome 改回 pass
        if (caseInfo.get("taskName").equals("照會回復")) {
            taskService.setVariable(task.getId(), "outcome", "pass");
        }

        // 完成當前任務
        taskService.complete(task.getId());

        // 取得下一關任務資訊進行回傳
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
     * @return 下一關任務資訊
     */
    public Map<String, String> toConsult(String caseNo) {

        // 取得案件資料
        Map<String, Object> caseInfo = caseQueryService.queryActiveCase(caseNo);
        if (caseInfo == null) {
            throw new RuntimeException("案件不存在或流程未啟動, caseNo=" + caseNo);
        }

        // 不是審核關卡就離開
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
```

</details>

<details>
<summary>返回上一關</summary>

透過任務跳轉的方式讓流程返回到上一個節點：

- 使用 `createTaskQuery()` 配合 `processInstanceId()` 查詢案件當前任務
  - 透過案件查詢取得 `processInstanceId`
  - 無資料代表流程已完成或案件不存在
- 使用 `createChangeActivityStateBuilder()` 改變任務節點位置
  - `processInstanceId()`：指定要操作的流程實例 ID
  - `moveActivityIdTo()`：指定從哪個節點跳轉到哪個節點
  - `changeState()`：執行節點跳轉
- 需要自行實作方法來取得上一個節點的 ID

```java
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

        // Step 5: 取得返回後的任務資訊進行回傳
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
     * 這裡為簡單示例，實務上可能需要透過 BPMN Model 查找 sequenceFlow
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
```

</details>

<details>
<summary>查詢案件完整歷史</summary>

使用 `HistoryService` 查詢流程的完整歷史記錄：

- 透過案件查詢取得 `processInstanceId`
- 使用 `createHistoricProcessInstanceQuery()` 查詢歷史流程實例
  - `processDefinitionKey()`：指定流程定義的 key
  - `variableValueEquals()`：透過變數作為查詢條件
- 使用 `createHistoricActivityInstanceQuery()` 查詢該流程的所有歷史活動節點
  - `processInstanceId()`：指定流程實例 ID
  - `orderByHistoricActivityInstanceStartTime().asc()`：按開始時間升序排列

```java
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

            // 將每個節點轉為 Map，並過濾掉不需要的節點類型
            return activities.stream()
                    .filter(act -> !act.getActivityId().matches("flow.*"))          // 排除連線
                    .filter(act -> !act.getActivityId().matches(".*Event"))         // 排除起點與終點
                    .filter(act -> !act.getActivityId().matches("gateway.*"))       // 排除決策節點
                    .filter(act -> !act.getActivityId().matches("subProcess.*"))    // 排除子流程 subProcess
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
```

</details>