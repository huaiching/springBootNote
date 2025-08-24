package com.example.api.util;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class MergePdrUtil {

    /**
     * PDF 檔案合併 (Apache PDFBox)
     *
     * @param pdfFileList 要合併的 PDF 清單
     * @return 產出的 PDF 檔案資料流（byte[]）
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
            merger.mergeDocuments(null);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF 合併失敗: ", e);
        }
    }
}
