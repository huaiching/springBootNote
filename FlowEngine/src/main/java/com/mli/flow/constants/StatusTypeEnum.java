package com.mli.flow.constants;

public enum StatusTypeEnum {
    /** M.主流程 */
    MAIN("M", "主流程"),
    /** S.子流程 */
    SUB("S", "子流程");

    private String code;
    private String desc;

    StatusTypeEnum(String code, String desc) {
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
    public static StatusTypeEnum getEnumByCode(String code) {
        for (StatusTypeEnum claimStatusEnum : StatusTypeEnum.values()) {
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
        StatusTypeEnum claimStatusEnum = getEnumByCode(code);
        if (claimStatusEnum != null) {
            return claimStatusEnum.getDesc();
        } else {
            return "";
        }
    }
}
