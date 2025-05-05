# JPA 執行 複雜SQL

## 說明

`簡單的SQL` 可以在 `repository` 有以下三種方式：

- 透過 繼承 `JpaRepository` 取得 `根據主鍵自動生成的 CRUD`。

- 根據 `命名規則` 來 設定 方法名稱。

- 透過 `nativeQurey` 執行 `簡單的 自定義 SQL`。

但是 如果想要執行 `複雜的 SQL`，
我們可以透過 JPA 提供的 `NamedParameterJdbcTemplate` 來簡單執行。

- `hibernate` 本身有提供 透過 `nativeQuery` 來執行 `複雜SQL`，
  但因為 `不會自動處理` 欄位的命名規則轉換 `下底線 無法自動轉換為 小駝峰`，
  所以 不推薦 此方法。

- 透過 `NamedParameterJdbcTemplate` 處理 `複雜SQL` 好處是 `自動轉換 小駝峰`。
  DB 欄位為 `下底線` 時，DTO 欄位允許 `下底線` `小駝峰` 且會 自動轉換匹配。 

其流程 共可分成 4 步驟：

1. 注入 `NamedParameterJdbcTemplate`
   
   ```java
   @Autowired
   private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
   ```

2. 撰寫 SQL 語法
   
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

3. 透過 Map 設定參數
   
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

4. 執行 SQL
   
   - `查詢` 透過 `namedParameterJdbcTemplate.query` 執行。
     
     ```java
     List<Addr> addrList = namedParameterJdbcTemplate.query(sql1, params1, new BeanPropertyRowMapper<>(Addr.class));
     ```
   
   - `增刪修` 透過 `namedParameterJdbcTemplate.update` 執行。
     
     ```java
     namedParameterJdbcTemplate.update(sql2, params2);
     ```

## 範例：查詢 - 複雜SQL

替 `Addr` 設計一個 `輸入 clientId 查詢 Addr 資料` 的方法。

```java
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
```

## 範例：修改 - 複雜的SQL

替 `Addr` 設計一個 `更新資料` 的方法。

給予 `變更前Addr` `變更後Addr` 並將 Addr 資料進行更新。

- `@Transactional`：設定為 `交易`，當執行失敗時，資料可回朔，

```java
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
```
