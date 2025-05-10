package com.example.api.constants;

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
    public static SexEnum getEnumByCode(String code) {
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
    public static String getDescByCode(String code) {
        SexEnum sexEnum = getEnumByCode(code);
        if (sexEnum != null) {
            return sexEnum.getDesc();
        } else {
            return "";
        }
    }
}
