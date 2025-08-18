package com.example.api.util;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * PDF 匯出工具
 */
public class ExportPdfUtil {

    /**
     * 使用 docx4j 將 Word (docx) 轉 PDF
     * @param wordFile Word 檔案的 byte[]
     * @return PDF 檔案的 byte[]
     */
    public static byte[] wordToPDF(byte[] wordFile) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(wordFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // 讀取 docx
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(bais);

            // 轉 PDF (使用 FO renderer)
            Docx4J.toPDF(wordMLPackage, baos);

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Word 轉 PDF 失敗 (docx4j): ", e);
        }
    }

    /**
     * PDF 檔案合併 (Apache PDFBox)
     *
     * @param pdfFileList 要合併的 PDF 清單
     * @return PDF 資料流
     */
    public static byte[] mergePDF(List<byte[]> pdfFileList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PDFMergerUtility merger = new PDFMergerUtility();
            // 設定輸出流
            merger.setDestinationStream(outputStream);

            // 加入每個 PDF
            for (byte[] pdfFile : pdfFileList) {
                merger.addSource(new ByteArrayInputStream(pdfFile));
            }

            // 合併
            merger.mergeDocuments(null); // null = 使用預設 MemoryUsageSetting

        } catch (Exception e) {
            throw new RuntimeException("PDF 合併失敗: ", e);
        }
        return outputStream.toByteArray();
    }
}
