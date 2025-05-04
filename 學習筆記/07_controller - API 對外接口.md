# controller - API 對外接口

`controller` 負責擺放 `API對外接口` 的資料夾。

在這裡面，我們會設定 `API 的請求與回應`。

目前 較常見的設計模式 會將 `路由處理` 和 `邏輯處理` 做適當切割，讓 controller 專注於處理請求格式與回傳格式。

## 建立 controller

接下來，讓我們為 剛才設計的 `service` 設計 `API 對外接口`。

1. 首先 讓我們 創立 `controller` 資料夾，並新增一個檔案 `SampleController.java`。

2. 開啟 `SampleController.java`，並依照下面範例 新增相關程式。
   
   程式中必要的 `註解` 可以分為下述幾種：
   
   - class 上方：
     
     - `@RestController`：標示 檔案是 對外接口文件。
     - `@RequestMapping`：設定 `第一層 URL`。
       - `@RequestMapping("/sample")` 代表 第一層 URL 為 `/sample`
     - `@Tag`：設定 controller 整體的 `Swagger 文字`。
       - `name`：方法簡要說明，會顯示在 controller 名稱的旁邊。
       - `description`：方法的詳細描述，會顯示在 方法點開後的最上方。
     
     class 內部：
     
     - `@Autowired`：Spring 自動注入方法 (可以不用 new 就進行使用)
     
     - 方法 上方：
       
       - `@Operation`：設定 方法的 `Swagger 文字`。
         
         - `summary`：方法的 簡要說明，會顯示在 方法旁邊。
         - `description`：方法的 詳細說明，會顯示在 方法名稱的旁邊。
         - `operationId`：方法的 識別ID，Swagger UI 不會顯示，但是 當前端透過 openAPI 產生 呼叫方法 的時候會使用到。
       
       - HTTP 映射方法
         
         公司的 Reahat 都只會使用 `@PostMapping`。
         
         - `@PostMapping`：`POST` 方法，透過 `Json` 交換數據，最常用的類型。
         
         - `@GetMapping`：`GET` 方法，通常用於 `讀取資料`。
         
         - `@PutMapping`：`PUT` 方法，通常用於 `更新資料`。
         
         - `@DeleteMapping`：`DELETE` 方法，通常用於 `刪除資料`。
     
     - 方法 參數內：
       
       根據 `HTTP 映射方法` 的設定不同，會有對應的參數類型設定。
       
       | 註解              | 特性                | HTTP 映射                                      | HTTP 映射範例                                                 |
       |:---------------:|:-----------------:|:--------------------------------------------:|:---------------------------------------------------------:|
       | `@RequestBody`  | 使用 `Json` 接收參數    | `@PostMapping`                               | `@PostMapping("/showUserPostBody")`                       |
       | `@RequestParam` | 從 URL 中獲取參數       | `@GetMapping` `@PutMapping` `@DeleteMapping` | `@GetMapping("/showUserGetParam")`                        |
       | `@PathVariable` | 在 URL 設定 變數 來獲取參數 | `@GetMapping` `@PutMapping` `@DeleteMapping` | `@GetMapping("/showUserGetVariable/{userId}/{userName}")` |
   
   ```java
   @RestController
   @RequestMapping("/sample")
   @Tag(name = "範例", description = "範例 API 接口")
   public class SampleConfig {
   
       @Autowired
       private SampleService sampleService;
   
       /**
        * @Reques參數ody 使用 Json 接收參數，會在 @PostMapping 使用
        */
       @Operation(summary = "POST 範例 API", description = "POST 範例", operationId = "showUserPostBody")
       @PostMapping("/showUserPostBody")
       public ResponseEntity<String> showUserPostBody(@RequestBody User user) {
           String msg = sampleService.showUser(user);
           return ResponseEntity.ok(msg);
       }
   
       /**
        * @RequestParam 透過 URL 接受參數，會在 @GetMapping 使用
        * 此方法 從 請求 URL 中獲取參數，方法網址不用特別設定參數
        */
       @Operation(summary = "GET 範例 API: RequestParam", description = "GET 範例", operationId = "showUserGetParam")
       @GetMapping("/showUserGetParam")
       public ResponseEntity<String> showUserGetParam(@RequestParam Long userId, @RequestParam String userName){
           String msg = sampleService.showUser(userId, userName);
           return ResponseEntity.ok(msg);
       }
   
       /**
        * @PathVariable 透過 URL 接受參數，會在 @GetMapping 使用
        * 此方法 在 URL 設定 變數，參數 由 URL 的變數中獲取
        * 網址 需要 特別設定 變數名稱
        */
       @Operation(summary = "GET 範例 API: showUserGetVariable", description = "GET 範例", operationId = "showUserGetVariable")
       @GetMapping("/showUserGetVariable/{userId}/{userName}")
       public ResponseEntity<String> showUserGetVariable(@PathVariable Long userId, @PathVariable String userName){
           String msg = sampleService.showUser(userId, userName);
           return ResponseEntity.ok(msg);
       }
   }
   ```

## 範例演示

1. `@PostMapping` + `@RequestBody`
   
   ![](image\controller_01.png)
   
   ![](image\controller_02.png)

2. `@GetMapping` + `@RequestParam`
   
   請注意 URL 的顯示方式
   
   ![](image\controller_03.png)

3. `@GetMapping` + `@PathVariable`
   
   請注意 URL 的顯示方式
   
   ![](image\controller_04.png)
