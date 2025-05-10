package com.example.api.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class ExportReponseUtil {

    /**
     * 產生 檔案下載的 ResponseEntity
     * @param fileName 檔案名稱
     * @param fileByte 檔案資料流
     * @return
     * @throws UnsupportedEncodingException
     */
    public static ResponseEntity<Resource> responseEntity(String fileName, byte[] fileByte) {
        Charset utf8 = Charset.forName("UTF-8");
        // 文件打包
        Resource resource = new ByteArrayResource(fileByte);
        // 文件下载
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, utf8));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
