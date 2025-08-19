package com.example.api.util;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * PDF 匯出工具
 */
public class ExportPdfUtil {
    /**
     * word 轉 PDF
     * (fr.opensagres.poi.xwpf.converter.pdf)
     * @param wordFile word 檔
     * @return 產出的 PDF 檔案資料流（byte[]）
     */
    public static byte[] wordToPDF(byte[] wordFile) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(wordFile));
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, outputStream, options);
        } catch (Exception e) {
            throw new RuntimeException("word 轉 PDF 失敗: ", e);
        }
        return outputStream.toByteArray();
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
