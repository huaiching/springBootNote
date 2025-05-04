package com.example.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "使用者資訊")
public class User {
    // 欄位定義
    @Schema(description = "使用者ID")
    private Long userId;

    @Schema(description = "使用者名稱")
    private String userName;

    // getting 和 setting
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
