package com.example.api.util;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * html 生成 PDF 工具
 */
public class HtmlToPdfUtil {

    /**
     * html 轉 PDF
     * @param templateEngine Thymeleaf 的 TemplateEngine，用於解析 HTML 樣板
     * @param modelFile 樣版路徑 (resources/templates/{modelFile})
     * @param context 資料內容
     * @return 產出的 PDF 檔案資料流（byte[]）
     */
    public static byte[] generate(TemplateEngine templateEngine, String modelFile, Context context) {
        // 生成 HTML
        String html = templateEngine.process(modelFile, context);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // HTML 轉 PDF
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            // 設定中文字型
            ClassPathResource fontFile1 = new ClassPathResource("templates/fonts/kaiu.ttf");
            builder.useFont(() -> {
                try {
                    return fontFile1.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            },"標楷體");

            ClassPathResource fontFile2 = new ClassPathResource("templates/fonts/3of9Barcode.ttf");
            builder.useFont(() -> {
                try {
                    return fontFile2.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            },"條碼");
            // 資料輸出
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF 生成失敗", e);
        }
    }
}
