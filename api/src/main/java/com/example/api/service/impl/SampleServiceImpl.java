package com.example.api.service.impl;

import com.example.api.dto.User;
import com.example.api.service.SampleService;
import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {
    /**
     * 顯示 使用者資訊
     *
     * @param uesr 使用者資訊
     * @return
     */
    @Override
    public String showUser(User uesr) {
        return showUser(uesr.getUserId(), uesr.getUserName());
    }

    /**
     * 顯示 使用者資訊
     *
     * @param userId 使用者ID
     * @param userName 使用者名稱
     */
    @Override
    public String showUser(Long userId, String userName) {
        String userMsg = "歡迎 " + userId + " " + userName + " 使用本系統！";

        return userMsg;
    }
}
