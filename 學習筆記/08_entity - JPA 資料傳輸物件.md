# entity - JPA 的 資料傳輸物件

`entity` 資料夾 是負責擺放 `JPA` 的 `資料傳輸物件`。

基本上 可以視為 `dto` 的延伸，但是需要加上 `JPA` 的特殊規定。

- `方法名`：`class` 的名稱 與 `TABLE NAME` 相同。

- `屬性`：必須針對 `TABLE` 進行設計 且 `TABLE` 必須要有 `Primary Key`。
  如果 TABLE 中沒有 `Primary Key`，你也必須 決定 `那些組合起來是 Primary Key`。
  例如：`polf` 是 `policy_no`；`colf` 是 `policy_no` + `coverage_no`。
  
  - `主鍵`：該屬性是 `Primary Key` 的相關屬性，必須要 `@Id` 進行註解標記。
  
  - `屬性名稱`：根據 `TABLE 欄位` 命名，但是要轉換為 `小駝峰`。
  
  - `屬性類型`：要依照 `TABLE 欄位類型` 進行設定。
    
    常見的設定方式：
    
    - `String`：對應資料庫的 `char` `varchar` `lvarchar`。
    
    - `Long`：對應資料庫的 `int8`。
    
    - `Integer`：對應資料庫的 `int4` `int` `integer` `smallint`。
    
    - `Double`：對應資料庫的 `float`。
    
    - `LocalDateTime`：對應資料庫的 `datetime`。
    
    - `建構式`：必須要有 `無參數建構式`。 (透過 IDE 生成)
    
    - `getting` 和 `setting`：透過 IDE 生成
    
    - `equals` 和 `hashCode`：必須針對 `主鍵` 建立。 (透過 IDE 生成)

## 建立 單一主鍵 entity

之前 我們建立了一個 單一主鍵的 table `clnt`，

雖然這個 table 沒有 `primary key`
但是我們知道 可以使用 `client_id`。

```sql
-- 客戶資料檔
CREATE TABLE IF NOT EXISTS clnt ( 
    clinet_id   CHAR(10),   -- 客戶證號
    names       CHAR(40),   -- 客戶姓名
    sex         CHAR(1),    -- 客戶性別
    age         INTEGER     -- 客戶年齡
);
```

接著 讓我們 實際來建立 單一主鍵的 entity

1. 首先 讓我們 創立 `entity` 資料夾，並新增一個檔案 `Clnt.java`。

2. 開啟 `Clnt.java`，並依照下面範例 新增相關程式。
   
   - class 上方
     
     - `@Entity`：標示 檔案是 `JPA` 的 `資料傳輸物件`。
     
     - `@Table`：設定 `JPA` 要對應到的 `TABLE NAME`。
     
     - `@Scheam`：Swagger 的 `註解`。
   
   - class 內部
     
     - `屬性`：根據 clnt 的欄位建立
       - `@Id`：設定 主鍵 欄位，主鍵為 `clientId`，所以只設定在 `clientId` 上。
       - `@Column`：設定 該欄位 對應的 `TABLE 欄位`。
       - `@Schema`：Swagger 的 `註解`。
     - `無參數建構式`：可透過 IDE 建立。
     - `getting` 和 `setting`：可透過 IDE 建立。
     - 主鍵的 `equals` 和 `hashCode`：可透過 IDE 建立。

```java
@Entity
@Table(name = "clnt")
@Schema(description = "客戶資料檔")
public class Clnt implements Serializable {
    @Id
    @Schema(description = "客戶證號")
    @Column(name = "clinet_id")
    private String clinetId;

    @Schema(description = "客戶姓名")
    @Column(name = "names")
    private String names;

    @Schema(description = "客戶性別")
    @Column(name = "sex")
    private String sex;

    @Schema(description = "客戶年齡")
    @Column(name = "age")
    private Integer age;

    // 無參數建構式
    public Clnt() {
    }

    // getting 和 setting
    public String getClinetId() {
        return clinetId!= null ? clinetId.trim() : null;
    }

    public void setClinetId(String clinetId) {
        this.clinetId = clinetId;
    }

    public String getNames() {
        return names!= null ? names.trim() : null;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSex() {
        return sex!= null ? sex.trim() : null;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // 根據主鍵 client_id 建立 equals 和 hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clnt that = (Clnt) o;
        return Objects.equals(clinetId, that.clinetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinetId);
    }
}
```

## 建立 多重主鍵 entity

之前 我們建立了一個 多重主鍵的 table `addr`，

雖然這個 table 沒有 `primary key`
但是我們知道 可以使用 `client_id` + `addr_ind`。

```sql
-- 客戶地址檔
CREATE TABLE IF NOT EXISTS addr (
    clinet_id   CHAR(10),   -- 客戶姓名
    addr_ind    CHAR(1),    -- 地址指示
    address     CHAR(72),   -- 地址
    tel         CHAR(11)    -- 電話
)
```

接著 讓我們 實際來建立 多重主鍵的 entity

1. 首先 讓我們 創立 `entity` 資料夾，並新增一個檔案 `Addr.java`。

2. 開啟 `Addr.java`，並依照下面範例 新增相關程式。
   
   - class 上方
     與 `單一主鍵 enttiy` 不同的是，`多重主鍵 entity` 需要建立 `主鍵類別`。
     
     - `@Entity`：標示 檔案是 `JPA` 的 `資料傳輸物件`。
     
     - `@Table`：設定 `JPA` 要對應到的 `TABLE NAME`。
     
     - `@IdClass`：設定 多重主鍵的 `主鍵類別`。
     
     - `@Scheam`：Swagger 的 `註解`。
   
   - class 內部
     
     - `屬性`：根據 clnt 的欄位建立
       - `@Id`：設定 主鍵 欄位，主鍵為 `clientId` `addrInd`，所以設定在 `clientId` `addrInd` 上。
       - `@Column`：設定 該欄位 對應的 `TABLE 欄位`。
       - `@Schema`：Swagger 的 `註解`。
     - `無參數建構式`：可透過 IDE 建立。
     - `getting` 和 `setting`：可透過 IDE 建立。
     - 主鍵的 `equals` 和 `hashCode`：可透過 IDE 建立。
     - 建立 `主鍵類別` 的 內部class `AddrKey`。
       - `屬性` 為 `所有主鍵`
       - `無參數建構式`：可透過 IDE 建立。
       - `getting` 和 `setting`：可透過 IDE 建立。
       - `equals` 和 `hashCode`：可透過 IDE 建立。 (所有屬性)

```java
@Entity
@Table(name = "addr")
@IdClass(Addr.AddrKey.class)
@Schema(description = "客戶地址檔")
public class Addr implements Serializable {
    @Id
    @Schema(description = "客戶姓名")
    @Column(name = "clinet_id")
    private String clinetId;

    @Id
    @Schema(description = "地址指示")
    @Column(name = "addr_ind")
    private String addrInd;

    @Schema(description = "地址")
    @Column(name = "address")
    private String address;

    @Schema(description = "電話")
    @Column(name = "tel")
    private String tel;

    // 無參數建構式: IDE 生成
    public Addr() {
    }

    // getting 和 setting: IDE 生成
    public String getClinetId() {
        return clinetId!= null ? clinetId.trim() : null;
    }

    public void setClinetId(String clinetId) {
        this.clinetId = clinetId;
    }

    public String getAddrInd() {
        return addrInd!= null ? addrInd.trim() : null;
    }

    public void setAddrInd(String addrInd) {
        this.addrInd = addrInd;
    }

    public String getAddress() {
        return address!= null ? address.trim() : null;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel!= null ? tel.trim() : null;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    // 主鍵的 equals 和 hashCode: IDE 生成
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addr that = (Addr) o;
        return Objects.equals(clinetId, that.clinetId) && Objects.equals(addrInd, that.addrInd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinetId, addrInd);
    }

    // 主鍵 實體類
    public static class AddrKey implements Serializable {
        private static final long serialVersionUID = 1L;

        private String clinetId;
        private String addrInd;

        public AddrKey() {
        }

        public String getClinetId() {
            return clinetId;
        }

        public void setClinetId(String clinetId) {
            this.clinetId = clinetId;
        }

        public String getAddrInd() {
            return addrInd;
        }

        public void setAddrInd(String addrInd) {
            this.addrInd = addrInd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AddrKey that = (AddrKey) o;
            return Objects.equals(clinetId, that.clinetId) && Objects.equals(addrInd, that.addrInd);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clinetId, addrInd);
        }
    }
}
```
