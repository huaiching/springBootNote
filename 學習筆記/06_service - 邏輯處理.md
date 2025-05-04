# service - 邏輯處理

`service` 負責擺放 `邏輯相關程式` 的地方。

目前 較常見的設計模式 會將 `邏輯方法` 和 `邏輯方法的實作` 分開撰寫

- `外層`：撰寫 `interface` 負責定義 `邏輯方法`。

- `內層`：繼承 `外層` 的 `interface` 並定義 `實作方法`。

```textile
java
├─ 📄Application.java
├─ 📁service
│   ├─ 📄SampleService.java
│   ├─ 📁Impl
│      ├─ 📄ServiceServiceImpl.java
```

## 建立 Service

接下來，讓我們為 `User` 設計一個 邏輯處理。

```textile
目標：
輸入 使用者資訊，顯示 使用者歡迎訊息。
```

1. 首先 讓我們 建立 `service` 資料夾，並新增一個檔案 `SampleService.java`。

2. 開啟 `User.java`，並依照下面範例 新增相關程式。
   
   - 這裡我們 建立 兩種方法，允許 `傳遞整個物件` 或 `個別給 ID 和 名稱`。
   
   ```java
   import com.example.api.dto.User;
   
   public interface SampleService {
   
       /**
        * 顯示 使用者資訊
        * @param uesr 使用者資訊
        * @return
        */
       String showUser(User uesr);
   
       /**
        * 顯示 使用者資訊
        * @param userId 使用者ID
        * @param userName 使用者名稱
        * @return
        */
       String showUser(Long userId, String userName);
   
   }
   ```

3. 接者 讓我們在 `service` 處，新增資料夾 `Impl`，並新增一個檔案 `SampleServiceImpl.java`。

4. 開啟 `SampleServiceImpl.java`，並依照下面範例 撰寫 `實作方法`。
   
   - 必須要 `繼承` 剛才的 `SampleService`，因為這裡我們要寫 `實作方法`。
   
   - `@Service`：標示 檔案是 邏輯處理文件。
   
   ```java
   import com.example.api.dto.User;
   import com.example.api.service.SampleService;
   import org.springframework.stereotype.Service;
   
   @Service
   public class SampleServiceImpl implements SampleService {
       /**
        * 顯示 使用者資訊
        *
        * @param uesr 使用者資訊
        * @return
        */
       @Override
       public String showUser(User uesr) {
           return showUser(uesr.getUserId(), uesr.getUserName());
       }
   
       /**
        * 顯示 使用者資訊
        *
        * @param userId 使用者ID
        * @param userName 使用者名稱
        */
       @Override
       public String showUser(Long userId, String userName) {
           String userMsg = "歡迎 " + userId + " " + userName + " 使用本系統！";
   
           return userMsg;
       }
   }
   ```
