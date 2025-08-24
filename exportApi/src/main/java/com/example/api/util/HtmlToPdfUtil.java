package com.example.api.util;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * html 生成 PDF 工具
 */
public class HtmlToPdfUtil {

    /**
     * html 轉 PDF
     * @param templateEngine Thymeleaf 的 TemplateEngine，用於解析 HTML 樣板
     * @param modelFile 樣版路徑 (resources/templates/{templateName}.html)
     * @param context 資料內容
     * @return
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
            File fontFile1 = new File("src/main/resources/templates/fonts/kaiu.ttf");
            builder.useFont(fontFile1, "標楷體");
            File fontFile2 = new File("src/main/resources/templates/fonts/3of9Barcode.ttf");
            builder.useFont(fontFile2, "條碼");
            // 資料輸出
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF 生成失敗", e);
        }
    }
}
