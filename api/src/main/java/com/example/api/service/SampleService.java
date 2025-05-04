package com.example.api.service;

import com.example.api.dto.User;

public interface SampleService {

    /**
     * 顯示 使用者資訊
     * @param uesr 使用者資訊
     * @return
     */
    String showUser(User uesr);

    /**
     * 顯示 使用者資訊
     * @param userId 使用者ID
     * @param userName 使用者名稱
     * @return
     */
    String showUser(Long userId, String userName);

}
