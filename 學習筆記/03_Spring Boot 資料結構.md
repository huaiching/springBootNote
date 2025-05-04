# Spring Boot 資料結構

## 專案的 資料結構

在 Spring Boot 的專案中，通常會依照 業務性質 將檔案拆分成 多個資料夾。

下面是 常見的 資料夾結構

```textile
java
├─ 📄Application.java           # 啟動主程式
├─ 📁config                     # 設定檔
    ├─ 📄SwaggerDocConfig.java  # swagger doc 的 title 設定檔
├─ 📁constants                  # 靜態類別，例如：常數、enum
├─ 📁controller                 # 對外 API 接口
├─ 📁service                    # 業務邏輯層 的 interface
│   ├─ 📁Impl                   # service 的實作類別，處理實際商業邏輯
├─ 📁entity                     # JPA 實體類別，對應資料庫中的資料表結構
├─ 📁dto                        # 資料傳輸物件
├─ 📁util                       # 工具類別，例如：時間格式處理、API 回應包裝
├─ 📁mapper                     # Entity 與 DTO 之間的轉換邏輯

resources
├─ 📄application.properties     # Spring Boot 設定檔
├─ 📄schema.sql                 # 初始建表 SQL
├─ 📁templates                  # 樣版檔 
```

當 前端 發出 `API請求` 時，會 依序執行 各資料夾 中的程式代碼。

![](image\callApi.png)

## 各資料夾 簡介

### 📁config：設定檔

放置 `應用設定相關類別` 的 資料夾

例如：剛才我們有在裡面建立了 Swagger Doc 的 配置文檔 `SwaggerDocConfig.java`。

- 需要設定 `@Configuration`。

### 📁controller：對外接口

放置 `API 的 對外接口` 的 資料夾

- 需要設定 `@RestController`。

- 只負責 `建立 API 對外接口` 和 `將請求轉拋 service` 不會在裡面處理業務邏輯。

### 📁service：業務邏輯

放置 `業務邏輯類` 的 資料夾，通常為兩層架構

```textile
├─ 📁service                    # 業務邏輯層 的 interface
│   ├─ 📁Impl                   # service 的實作類別，處理實際商業邏輯
```

- `外層` 透過 `interface` 設定 業務邏輯的 `方法接口`。

- `內層` 透過 繼承 `service` 來 `實作業務邏輯`。
  
  - 需要設定 `@Service`。

### 📁entity：資料傳輸物件(DB相關)

Spring Boot 裡面的 `資料傳輸物件` 資料夾。

放置 `JPA 資料表實體類`，基本上可以視為 `table 檔案結構物件`，
屬性 要跟 DataBase 的 檔案結構 相同。

- 需要設定 `@Entity` 

- 需要設定 `@Table` 來 標示 對應的 資料表檔案
  如：`@Table(name = "clnt")` 和 `@Table(name = "addr")`

- 必須要有 `getting 方法`、`setting 方法` 及 `其他 entity 特殊設定`。

### 📁dto：資料傳輸物件(非DB)

Spring Boot 裡面的 `資料傳輸物件` 資料夾。

放置 `entity` 以外 所有 `資料傳輸物件`。

- 必須要有 `getting 方法`、`setting 方法。

### 📁constants：常數與名詞解釋

放置 `靜態文件` 的 資料夾，通常裡面會擺放

- `常數` 

- `Enum` 資料辭典說明文件

### 📁util：工具程式

放置 `工具類` 的 資料夾，各種 公用的靜態方法 都會放在這邊。
例如：

- `DateUtil`：日期轉換工具 

- `ExportUtil`：報表產出的相關工具

- `ApiUtil`：對外 API 呼叫方法

### 📁mapper：資料轉換

放置 `entity <--> dto` 的 資料轉換工具。
