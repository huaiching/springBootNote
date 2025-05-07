## constants - 常數 與 Enum

`constants` 資料夾 是用來 `常數` `Enum` 的地方。

擺放在 `constants` 的資料，屬於 `名詞解釋` 類型的資料，通常 `很少修改`。

## 建立 Enum

讓我們 替 `Clnt` 中的 `sex` 欄位，設定 `名詞解釋`。

```textile
需求：
sex = 1 代表 男性
sex = 2 代表 女性
其他資料 代表 未定義，給予 空白
```

1. 首先 讓我們 創立 `constants` 資料夾，並新增一個檔案 `SexEnum.java`。

2. 開啟 `SexEnum.java`，並依照下面範例 新增相關程式。
   
   - 先設定 `欄位`，再設定 `名詞解釋`。
     
     ```java
         Man("1","男"),
         Woman("2","女");
     
         private String code;
         private String desc;
     ```
   
   - 透過 IDE 工具，生成 `建構式` `getting` `setting`。
     
     ```java
         // 建構式: IDE 自動產生
         SexEnum(String code, String desc) {
             this.code = code;
             this.desc = desc;
         }
     
         // getting 和 setting: IDE 自動產生
         public String getCode() {
             return code;
         }
     
         public void setCode(String code) {
             this.code = code;
         }
     
         public String getDesc() {
             return desc;
         }
     
         public void setDesc(String desc) {
             this.desc = desc;
         }
     ```
   
   - 自行撰寫 需要的 方法。
     
     - 透過 代碼 取得 Enum。
       
       ```java
           /**
            * 根據 代碼 尋找 Enum
            * @param code 要查詢的代碼
            * @return 代碼對應的 Enum
            */
           public SexEnum getEnumByCode(String code) {
               for (SexEnum sexEnum : SexEnum.values()) {
                   if (sexEnum.getCode().equals(code)) {
                       return sexEnum;
                   }
               }
               return null;
           }
       ```
     
     - 透過 代碼 取得 中文。
       
       ```java
           /**
            * 根據 代碼 查詢 中文
            * @param code 要查詢的代碼
            * @return 對應的中文
            */
           public String getDescByCode(String code) {
               SexEnum sexEnum = getEnumByCode(code);
               if (sexEnum != null) {
                   return sexEnum.getDesc();
               } else {
                   return "";
               }
           }
       ```

完整的程式碼範例

```java
public enum SexEnum {
    Man("1","男"),
    Woman("2","女");

    private String code;
    private String desc;

    // 建構式: IDE 自動產生
    SexEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // getting 和 setting: IDE 自動產生
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    /**
     * 根據 代碼 尋找 Enum
     * @param code 要查詢的代碼
     * @return 代碼對應的 Enum
     */
    public SexEnum getEnumByCode(String code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getCode().equals(code)) {
                return sexEnum;
            }
        }
        return null;
    }

    /**
     * 根據 代碼 查詢 中文
     * @param code 要查詢的代碼
     * @return 對應的中文
     */
    public String getDescByCode(String code) {
        SexEnum sexEnum = getEnumByCode(code);
        if (sexEnum != null) {
            return sexEnum.getDesc();
        } else {
            return "";
        }
    }
}
```
