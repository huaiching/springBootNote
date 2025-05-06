# repository - SQL 處理層

`repository` 資料夾 是 專門用來 `處理 SQL` 的地方。

目前常見的設計方式 與 `service` 相同為 兩層式設計

- `外層` 透過 `interface` 設定 `SQL` 的 `方法接口`。
  
  - `簡單 SQL` 繼承 `JpaRepository` 即可，無須 實作方法。
  
  - `複雜 SQL` 透過 `interface` 設定 `方法接口`。

- `內層` 負責 `複雜 SQL` 的 `實作方法`。
  
  - 需要設定 `@Repository`。

```textile
java
├─ 📄Application.java
├─ 📁repository
│   ├─ 📄AddrRepository.java
│   ├─ 📄AddrCustomRepository.java
│   ├─ 📁impl
│      ├─ 📄AddrCustomRepositoryImpl.java
```

## 簡單 SQL 的處理

對於 `簡單 SQL` 我們會透過下面三種方法 取得，`entity` 的 `CRUD` 方法。

1. 繼承 `JpaRepository` 獲得 `針對主鍵 的 CRUD`。
   
   會有兩個參數要定義，分別是 `entity` 和 `主鍵型態`。
   
   - `單一主鍵`：主鍵的屬性型態。
   
   - `多重主鍵`：`@IdClass` 設定的 `主鍵類別`。
   
   透過 `JpaRepository` 自動生成的 `CRUD` 方法，常用的有；
   
   - `save`：
     
     - 根據 `主鍵` 進行 `新增` 或 `更新` 資料。 (單筆) 
     
     - (無資料 = 新增 / 有資料 = 更新)
     
     - 範例
       
       ```java
       Clnt savedEntity = clntRepository.save(entity);
       Addr savedEntity = addrRepository.save(entity);
       ```
   
   - `saveAll`：
     
     - 根據 `主鍵` 進行 `新增` 或 `更新` 資料。 (多筆) 
     
     - (無資料 = 新增 / 有資料 = 更新)
     
     - 範例
       
       ```java
       List<Clnt> savedEntityList = clntRepository.saveAll(entityList);
       List<Addr> savedEntityList = addrRepository.saveAll(entityList);
       ```
   
   - `findById`：
     
     - 根據 `主鍵` 查詢 資料 (單筆)
     
     - 範例
       
       ```java
       Clnt savedEntity = clntRepository.save(entity);
       Addr savedEntity = addrRepository.save(entity);
       ```
   
   - `findAllById`：
     
     - 根據 `主鍵` 查詢 資料 (多筆)
     
     - 範例
       
       ```java
       Clnt entity = clntRepository.findById(id).orElse(null);
       Addr entity = addrRepository.findById(id).orElse(null);
       ```
   
   - `deleteById`：
     
     - 根據 `主鍵` 刪除 資料 (單筆)
     
     - 範例
       
       ```java
       clntRepository.deleteById(id);
       addrRepository.deleteById(id);
       ```
   
   - `deleteAllById`：
     
     - 根據 `主鍵` 刪除 資料 (多筆)
     
     - 範例
       
       ```java
       clntRepository.deleteAllById(idList);
       addrRepository.deleteAllById(idList);
       ```
   
   - `existsById`：
     
     - 判斷 `主鍵` 是否有資料。 (true = 有資料 / fakse = 無資料)
     
     - 範例
       
       ```java
       Boolean clntExists = clntRepository.existsById(id);
       Boolean addrExists = addrRepository.existsById(id);
       ```

2. 根據 `命名規則` 產生 `CRUD` 方法。
   根據下面的規則 組合 方法名稱，`JPA` 會自動生成 `SQL 方法` 提供使用。
   ＊規則 = `關鍵字` + `屬性條件` (至少一個屬性) + `排序` (選填)
   
   ```java
   List<Addr> findByClientId(String clientId);
   ```
   
   - 關鍵字
     
     | 命名規則       | 定義        |
     |:----------:|:---------:|
     | `findBy`   | 查詢結果 (多筆) |
     | `countBy`  | 計算符合條件的數量 |
     | `existsBy` | 檢查是否存在    |
   
   - 屬性條件：`欄位名稱` + `連接符號`
     
     | 連接符號             | 對應 SQL         |
     |:----------------:|:--------------:|
     | And              | AND            |
     | Or               | OR             |
     | Between          | BETWEEN        |
     | LessThan         | <              |
     | LessThanEqual    | <=             |
     | GreaterThan      | >              |
     | GreaterThanEqual | >=             |
     | Like             | LIKE           |
     | NotLike          | NOT LIKE       |
     | StartingWith     | LIKE 'value%'  |
     | EndingWith       | LIKE '%value'  |
     | Containing       | LIKE '%value%' |
     | In               | IN             |
     | NotIn            | NOT IN         |
   
   - 排序
     
     | 排序規則                | 定義   | 命名範例                           |
     |:-------------------:|:----:|:------------------------------:|
     | OrderBy + 屬性 + Asc  | 升序排列 | findByAgeOrderByNameAsc        |
     | OrderBy + 屬性 + Desc | 降序排列 | findByAgeOrderByCreateDateDesc |

3. 透過 `nativeQurey` 執行 `簡單的 自定義 SQL`。
   
   - @Query(value = "SQL語句", nativeQuery = true)
   
   - SQL語句中，變數要用 `:` 標示。
   
   - 方法的參數 要使用 `@Param("SQL變數")` 標示。
   
   ```java
   @Query(value = "SELECT * FROM clnt " +
                  "WHERE client_id IN :clientIdList", nativeQuery = true)
   List<Clnt> queryClntByClientIdList(@Param("clientIdList") List<String> clientIdList);
   ```

### 建立 單一主鍵 的 repository

之前 我們建立了一個 單一主鍵 的 entity `Clnt`，

接著 讓我們為其建立 repository。

1. 首先 讓我們 創立 `repository` 資料夾，並新增一個檔案 `ClntRepository.java`。

2. 開啟 `ClntRepository.java`，並依照下面範例 新增相關程式。
- `Clnt` 的主鍵為 `clientId` 型態為 `String`。

- `JpaRepository` 第一個參數 為 entity 類 `Clnt`。

- `JpaRepository` 第二個參數 為 主鍵的型態 `String`。

```java
import com.example.api.entity.Clnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClntRepository extends JpaRepository<Clnt, String> {

    @Query(value = "SELECT * FROM clnt " +
                   "WHERE client_id IN :clientIdList", nativeQuery = true)
    List<Clnt> queryClntByClientIdList(@Param("clientIdList") List<String> clientIdList);
}
```

### 建立 多重主鍵 的 repository

之前 我們建立了一個 單一主鍵 的 entity `Addr`，

接著 讓我們為其建立 repository。

1. 首先 讓我們 創立 `repository` 資料夾，並新增一個檔案 `AddrRepository.java`。

2. 開啟 `AddrRepository.java`，並依照下面範例 新增相關程式。
- `Addr` 的主鍵為 `clientId` 和 `addrInd`，我們設定 `主鍵類別` 為 `Addr.AddrKey`。

- `JpaRepository` 第一個參數 為 entity 類 `Clnt`。

- `JpaRepository` 第二個參數 為 多重主鍵的主鍵類別 `Addr.AddrKey``。

```java
import com.example.api.entity.Addr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddrRepository extends JpaRepository<Addr, Addr.AddrKey> {

    /**
     * SELECT * FROM addr
     * HWERE client_id = :clientId
     */
    List<Addr> findByClientId(String clientId);
}
```

## 複雜 SQL 的處理

對於 `複雜 SQL`，我們會採用 類似 `Service` 的設計方式

- `外層`：設定 `interface` 負責 設定 `方法接口`。

- `內層`：繼承 `interface` 負責 `實作方法`。

### 外層：interface 的設定

一般而言，因為 介面分離原則，`複雜SQL` 我們會單獨寫一個 `interface`。

用上面的 `Addr` 舉例，

- `簡單SQL`：會建立檔案 `AddrRepository`。

- `複雜SQL`：會建立檔案 `AddrCustomRepository`，並且 讓 `AddrRepository` 繼承。
  
  - 因為 `AddrRepository` 繼承 `AddrCustomRepository`，
    所以 其他程式 只要透過 `AddrRepository` 即可使用 `複雜的SQL` 方法。

```java
public interface AddrCustomRepository {
    /**
     * 單筆更新 addr <br/>
     * @param entityOri 變更前的 addr
     * @param entityNew 變更後的 addr
     */
    void update(Addr entityOri, Addr entityNew);

    /**
     * 模糊 進行 地址搜尋
     * @param address 要搜尋的地址字串
     * @return 查詢到 Addr 資料清單
     */
    List<Addr> queryAddress(String address);
}
```

```java
public interface AddrRepository extends JpaRepository<Addr, Addr.AddrKey>, AddrCustomRepository {

    /**
     * SELECT * FROM addr
     * HWERE client_id = :clientId
     */
    List<Addr> findByClientId(String clientId);
}
```

### 內層：複雜 SQL 的實作方法

想要執行 `複雜的 SQL`，建議透過 JPA 提供的 `NamedParameterJdbcTemplate` 來執行。

- 透過 `NamedParameterJdbcTemplate` 處理 `複雜SQL` 好處是 `自動轉換 小駝峰`。
  DB 欄位為 `下底線` 時，DTO 欄位允許 `下底線` `小駝峰` 且會 自動轉換匹配。

- `hibernate` 本身有提供 透過 `nativeQuery` 來執行 `複雜SQL`，
  但因為 `不會自動處理` 欄位的命名規則轉換 `下底線 無法自動轉換為 小駝峰`，
  所以 不推薦 此方法。

其流程 共可分成 5 步驟：

1. class 打上註解 `@Repository`。

2. 注入 `NamedParameterJdbcTemplate`。
   
   ```java
   @Autowired
   private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
   ```

3. 撰寫 SQL 語法。
   
   ```java
   String sql1 = "SELECT * FROM addr " +
               "WHERE address LIKE :address";
   ```
   
   ```java
   String sql2 = "UPDATE addr " +
               "SET client_id = :clientIdNew " +
               "   ,addr_ind = :addrIndNew " +
               "   ,address = :addressNew " +
               "   ,tel = :telNew " +
               "WHERE client_id = :clientIdOri " +
               "  AND addr_ind = :addrIndOri " + 
               "  AND address = :addressOri " + 
               "  AND tel = :telOri ";
   ```

4. 透過 Map 設定參數。
   
   ```java
   Map<String, Object> params1 = new HashMap<>();
   params1.put("address", "%" + address + "%");
   ```
   
   ```java
   Map<String, Object> params2 = new HashMap<>();
   params2.put("clientIdNew", entityNew.getClientId());
   params2.put("addrIndNew", entityNew.getAddrInd());
   params2.put("addressNew", entityNew.getAddress());
   params2.put("telNew", entityNew.getTel());
   params2.put("clientIdOri", entityOri.getClientId());
   params2.put("addrIndOri", entityOri.getAddrInd());
   params2.put("addressOri", entityOri.getAddress());
   params2.put("telOri", entityOri.getTel());4. 
   ```

5. 執行 SQL。
   
   - `查詢` 透過 `namedParameterJdbcTemplate.query` 執行。
     
     ```java
     List<Addr> addrList = namedParameterJdbcTemplate.query(sql1, params1, new BeanPropertyRowMapper<>(Addr.class));
     ```
   
   - `增刪修` 透過 `namedParameterJdbcTemplate.update` 執行。
     
     ```java
     namedParameterJdbcTemplate.update(sql2, params2);
     ```

完整的程式範例

```java
@Repository
public class AddrCustomRepositoryImpl implements AddrCustomRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 單筆更新 addr <br/>
     * @param entityOri 變更前的 addr
     * @param entityNew 變更後的 addr
     */
    @Override
    @Transactional
    public void update(Addr entityOri, Addr entityNew) {
        // 建立 SQL
        String sql = "UPDATE addr " +
                "SET client_id = :clientIdNew " +
                "   ,addr_ind = :addrIndNew " +
                "   ,address = :addressNew " +
                "   ,tel = :telNew " +
                "WHERE client_id = :clientIdOri " +
                "  AND addr_ind = :addrIndOri " +
                "  AND address = :addressOri " +
                "  AND tel = :telOri ";
        // 填入 參數
        Map<String, Object> params = new HashMap<>();
        params.put("clientIdNew", entityNew.getClientId());
        params.put("addrIndNew", entityNew.getAddrInd());
        params.put("addressNew", entityNew.getAddress());
        params.put("telNew", entityNew.getTel());
        params.put("clientIdOri", entityOri.getClientId());
        params.put("addrIndOri", entityOri.getAddrInd());
        params.put("addressOri", entityOri.getAddress());
        params.put("telOri", entityOri.getTel());
        // 執行 方法
        namedParameterJdbcTemplate.update(sql, params);
    }


    /**
     * 模糊 進行 地址搜尋
     *
     * @param address 要搜尋的地址字串
     * @return 查詢到 Addr 資料清單
     */
    @Override
    public List<Addr> queryAddress(String address) {
        // 建立 SQL
        String sql = "SELECT * FROM addr " +
                "WHERE address LIKE :address";
        // 填入 參數
        Map<String, Object> params = new HashMap<>();
        params.put("address", "%" + address + "%");
        // 執行 方法
        List<Addr> addrList = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Addr.class));

        return addrList;
    }
}
```
