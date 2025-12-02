package com.mli.flow.constants;

public enum ClaimStatusEnum {
    /** 1.建檔 */
    VALUE_1("1", "建檔"),
    /** 2.審核 */
    VALUE_2("2", "審核"),
    /** 3.送核 */
    VALUE_3("3", "送核"),
    /** 4.結案 */
    VALUE_4("4", "結案"),
    /** A.照會 */
    VALUE_A("A", "照會");

    private String status;
    private String desc;

    ClaimStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根據 節點 尋找 Enum
     * @param status 要查詢的節點
     * @return 代碼對應的 Enum
     */
    public static ClaimStatusEnum getEnumByStatus(String status) {
        for (ClaimStatusEnum claimStatusEnum : ClaimStatusEnum.values()) {
            if (claimStatusEnum.getStatus().equals(status)) {
                return claimStatusEnum;
            }
        }
        return null;
    }

    /**
     * 根據 節點 查詢 中文
     * @param status 要查詢的節點
     * @return 對應的中文
     */
    public static String getDescByStatusCode(String status) {
        ClaimStatusEnum claimStatusEnum = getEnumByStatus(status);
        if (claimStatusEnum != null) {
            return claimStatusEnum.getDesc();
        } else {
            return "";
        }
    }
}
