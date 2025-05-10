# 報表 - PDF 檔案

## 安裝依賴

- Word 轉 PDF：
  使用套件 `fr.opensagres.poi.xwpf.converter.pdf`

- PDF 檔案合併：
  使用套件 `openPDF`

```xml
        <!-- fr.opensagres.poi.xwpf.converter.pdf -->
        <dependency>
            <groupId>fr.opensagres.xdocreport</groupId>
            <artifactId>fr.opensagres.poi.xwpf.converter.pdf</artifactId>
            <version>2.0.3</version>
        </dependency>

        <!-- OpenPDF -->
        <dependency>
            <groupId>com.github.librepdf</groupId>
            <artifactId>openpdf</artifactId>
            <version>1.0.5</version>
        </dependency>
```

## 工具

- wordToPDF
  功能：Word 轉為 PDF
  輸入：要轉換的 Word 檔
  輸出：轉換後的 PDF 檔

- mergePDF
  功能：將 多個 PDF 檔 合併為 一個 PDF 檔
  輸入：要合併的 PDF 檔 清單
  輸出：合併後的 PDF 清單

```java
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
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
     *
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
     * PDF 檔案合併 (iText / openPDF)
     * @param pdfFlieList 要合併的 PDF 清單
     * @return PDF 資料流
     */
    public static byte[] mergePDF(List<byte[]> pdfFlieList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // 創建 PDF 合併相關變數
            com.lowagie.text.Document document = new Document();
            com.lowagie.text.pdf.PdfCopy pdfCopy = new PdfCopy(document, outputStream);
            document.open();
            // PDF 合併
            for (byte[] pdfFlie : pdfFlieList) {
                // 讀取文件
                com.lowagie.text.pdf.PdfReader pdfReader = new PdfReader(pdfFlie);
                // 取得頁數
                int pdfPages = pdfReader.getNumberOfPages();
                // 添加至輸出文檔
                for (int pdfPage = 1; pdfPage <= pdfPages; pdfPage++) {
                    PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader, pdfPage);
                    pdfCopy.addPage(importedPage);
                }
                // 關閉 PdfReader
                pdfReader.close();
            }
            // 關閉 Document
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("PDF 合併失敗: ", e);
        }
        return outputStream.toByteArray();
    }
}
```
