# service - 邏輯處理

`service` 資料夾負責存放與邏輯處理相關的程式碼。

在現代軟體設計中，我們常將「邏輯方法」與其「實作」分開撰寫。這種做法有助於維護與擴展，並促進程式碼的解耦。

- **外層**：創建 `interface` 來定義邏輯方法。
- **內層**：實作 `interface` 並提供具體的邏輯實現。

```textile
java
├─ 📄Application.java
├─ 📁service
│   ├─ 📄SampleService.java
│   ├─ 📁impl
│      ├─ 📄ServiceServiceImpl.java
```

---

## 建立 Service

接下來，讓我們為 `User` 設計一個 邏輯處理。

```textile
目標：
輸入 使用者資訊，顯示 使用者歡迎訊息。
```

1. 首先 讓我們 建立 `service` 資料夾，並新增一個檔案 `SampleService.java`。

2. 開啟 `User.java`，並依照下面範例 新增相關程式。
   
   - 這裡我們 建立 兩種方法：
     - 傳遞整個 `User` 物件，並產出 使用者資訊 文字訊息。
     - 傳遞 `userId` 和 `userName`，並產出 使用者資訊 文字訊息。
   
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

3. **建立 `impl` 資料夾**，並在其中新增一個檔案 `SampleServiceImpl.java`。

4. 開啟 `SampleServiceImpl.java`，並依照下面範例 撰寫 `實作方法`。
   
   - 需要 `implements` 來實作 `SampleService` 介面中的方法。
   
   - 使用 `@Service` 註解來標示此類別為邏輯處理的實作類。
   
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
