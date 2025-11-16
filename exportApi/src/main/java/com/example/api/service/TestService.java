package com.example.api.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class TestService {

    public void text() throws Exception {

        // 使用 ClassPathResource 讀取檔案（jar 內也可以讀取）
        ClassPathResource resource = new ClassPathResource("templates/user.txt");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
//                // 跳過標題行
//                if (isFirstLine) {
//                    isFirstLine = false;
//                    continue;
//                }

                String[] fields = line.split("\\|");

                if (fields.length == 3) {
                    System.out.println(fields[0] + " " + fields[1] + " " + fields[1]);
                }
            }
        }

    }
}
