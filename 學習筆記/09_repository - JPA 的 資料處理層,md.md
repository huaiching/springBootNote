# repository - JPA 的 資料處理層

`repository` 資料夾，用來擺放 `entity` 的 `JPA 資料交互設定`。

在這裡面，我們可以透過 下面三種方法 取得，`entity` 的 `CRUD` 方法。

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

## 建立 單一主鍵 的 repository

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

## 建立 多重主鍵 的 repository

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
