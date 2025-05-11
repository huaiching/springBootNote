package com.example.api.util;

import org.apache.commons.lang3.StringUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Excel 匯出工具 (使用 JXLS 2)
 */
public class ExportExcelUtil {

    /**
     * 產生 Excel 檔案
     *
     * @param modelFile 樣版檔案路徑（相對於 classpath，例如 "templates/sample_template.xlsx"）
     * @param context   JXLS Context，包含資料模型與變數（例如 context.putVar("users", userList)）
     * @return 產出的 Excel 檔案資料流（byte[]）
     */
    public static byte[] generateExcel(String modelFile, Context context) {
        // 參數驗證
        if (StringUtils.isEmpty(modelFile)) {
            throw new RuntimeException("樣版路徑 不可空白!!");
        }
        if (context == null) {
            throw new RuntimeException("資料內容 不可空白!!");
        }

        // 產生檔案
        try (
                // 讀取 classpath 下的樣版 Excel 檔案
                InputStream inputStream = new ClassPathResource(modelFile).getInputStream();

                // 建立輸出流，用來儲存產生的 Excel 內容
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            // 處理 Excel 樣版，產生新的 Excel 並寫入 outputStream
            JxlsHelper.getInstance()
                    .setEvaluateFormulas(true) // 啟用 Excel 公式自動計算
                    .processTemplate(inputStream, outputStream, context);

            // 回傳產生好的 byte[]
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Excel 產生失敗，樣版路徑: " + modelFile, e);
        }
    }
}
