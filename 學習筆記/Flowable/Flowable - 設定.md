# Flowable - 設定

本章節說明如何在 Spring Boot 專案中配置 Flowable。

## 1. Maven 依賴配置

### pom.xml

```xml
<!-- Flowable Spring Boot Starter -->
<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter</artifactId>
    <version>6.8.1</version>
</dependency>
```

## 2. Spring Boot 應用配置

### application.yml

```yml
flowable:
  # ==================== 流程定義部署 ====================
  # 自動部署 BPMN 檔案位置（支援多個路徑，逗號分隔）
  process-definition-location-prefix: classpath*:/processes/
  process-definition-location-suffixes:
    - **.bpmn20.xml
    - **.bpmn

  # ==================== 資料庫設定 ====================
  # 資料庫 Schema 更新策略
  # - true: 自動建表/更新表結構（開發環境適用）
  # - false: 不自動建表（正式環境建議手動建表後設為 false）
  # - create-drop: 每次啟動時重建表（僅測試用）
  database-schema-update: true

  # ==================== 歷史資料設定 ====================
  # 歷史資料保留等級
  # - none: 不記錄歷史
  # - activity: 記錄流程實例和活動實例
  # - audit: 記錄所有變數變更（推薦）
  # - full: 記錄所有細節，包含表單屬性（正式環境建議使用）
  history-level: full

  # ==================== 非同步執行器設定 ====================
  # 啟用非同步執行器（正式環境強烈建議開啟，提升效能）
  async-executor-activate: true

  # 非同步執行器執行緒池設定（可選）
  async-executor:
    core-pool-size: 2
    max-pool-size: 10
    queue-size: 100
    # Job 獲取間隔時間（毫秒）
    default-async-job-acquire-wait-time: 10000
    # 每次獲取的 Job 數量
    max-async-jobs-due-per-acquisition: 1

  # ==================== 流程定義檢查 ====================
  # 檢查流程定義是否變更
  # - true: 開發時開啟，自動偵測 BPMN 變更並重新部署
  # - false: 正式環境建議關閉，提升啟動效能
  check-process-definitions: true

  # ==================== REST API 設定 ====================
  # 啟用 Flowable 內建 REST API
  # - true: 啟用（開發時方便測試）
  # - false: 正式環境建議關閉，自行開發 API
  rest-enabled: false

  # REST API 路徑前綴（預設為 /）
  rest-api-base-path: /flowable-rest

  # ==================== IDM 身份管理設定 ====================
  # 是否啟用 IDM 引擎（包含使用者、群組管理）
  idm-enabled: true

  # ==================== 其他進階設定 ====================
  # 流程實例名稱（可選，用於日誌或監控）
  # process-instance-name-like: %test%

  # 是否啟用事件日誌（用於審計追蹤）
  enable-event-dispatcher: true

  # 流程引擎名稱（多引擎時使用）
  # process-engine-name: default
```

### 開發環境建議配置

```yml
flowable:
  # 流程定義部署
  process-definition-location-prefix: classpath*:/processes/

  # 資料庫設定
  database-schema-update: true          # 自動建表

  # 歷史資料設定
  history-level: full                   # 完整歷史記錄

  # 非同步執行器
  async-executor-activate: true         # 啟用非同步執行

  # 流程定義檢查
  check-process-definitions: true       # 自動偵測流程變更

  # IDM 身份管理
  idm-enabled: true                     # 啟用身份管理
```

### 正式環境建議配置

```yml
flowable:
  # 流程定義部署
  process-definition-location-prefix: classpath*:/processes/

  # 資料庫設定
  database-schema-update: false         # 手動建表，避免自動更新

  # 歷史資料設定
  history-level: audit                  # 保留必要歷史記錄

  # 非同步執行器
  async-executor-activate: true         # 啟用非同步執行
  async-executor:
    core-pool-size: 2
    max-pool-size: 10
    queue-size: 100

  # 流程定義檢查
  check-process-definitions: false      # 關閉自動檢查，提升效能

  # IDM 身份管理
  idm-enabled: false                    # 若使用外部身份管理系統可關閉

  # 事件監聽
  enable-event-dispatcher: true         # 啟用事件監聽，用於審計
```

## 常見問題

### 1. 如何指定多個 BPMN 檔案路徑？

```yml
flowable:
  process-definition-location-prefix: classpath*:/processes/,classpath*:/workflows/
```

### 2. 如何手動建立 Flowable 資料表？

可使用 Flowable 提供的 SQL 腳本：

- 下載對應資料庫的 DDL 腳本：[Flowable GitHub](https://github.com/flowable/flowable-engine/tree/main/modules/flowable-engine/src/main/resources/org/flowable/db/create)
- 手動執行 SQL 建表後，設定 `database-schema-update: false`

### 3. 如何查看 Flowable 建立的資料表？

Flowable 會建立約 70+ 張表，主要分為以下類別：

- **ACT_RE_***: Repository（流程定義、部署）
- **ACT_RU_***: Runtime（執行中的流程實例、任務）
- **ACT_HI_***: History（歷史流程、任務、變數）
- **ACT_ID_***: Identity（使用者、群組）
- **ACT_GE_***: General（通用資料，如 Job、事件）

### 4. 如何自訂資料表前綴？

```yml
flowable:
  database-table-prefix: FLW_
```

這會將所有表名從 `ACT_*` 改為 `FLW_*`。

### 5. 如何停用某些引擎？

```yml
flowable:
  # 停用 DMN 決策引擎
  dmn-enabled: false
  # 停用 Form 表單引擎
  form-enabled: false
  # 停用 Content 內容引擎
  content-enabled: false
```
