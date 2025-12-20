package com.mli.flow.constants;

public enum ValidEnum {
    /** 0.無效 */
    INVALID("0", "無效"),
    /** 1.有效 */
    VALID("1", "有效");

    private String code;
    private String desc;

    ValidEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根據 代碼 尋找 Enum
     * @param code 代碼
     * @return 代碼對應的 Enum
     */
    public static ValidEnum getEnumByCode(String code) {
        for (ValidEnum claimStatusEnum : ValidEnum.values()) {
            if (claimStatusEnum.getCode().equals(code)) {
                return claimStatusEnum;
            }
        }
        return null;
    }

    /**
     * 根據 代碼 查詢 中文
     * @param code 代碼
     * @return 對應的中文
     */
    public static String getDescByCode(String code) {
        ValidEnum claimStatusEnum = getEnumByCode(code);
        if (claimStatusEnum != null) {
            return claimStatusEnum.getDesc();
        } else {
            return "";
        }
    }
}
