# PDF 檔案合併

## 安裝依賴

- PDF 檔案合併：
  使用套件 `pdfbox`

```xml
        <!-- org.apache.pdfbox -->
        <dependency>
            <groupId>org.apache.pdfbox</groupId>
            <artifactId>pdfbox</artifactId>
            <version>2.0.30</version>
        </dependency>
```

## 工具

- 

- mergePDF
  功能：將 多個 PDF 檔 合併為 一個 PDF 檔
  輸入：要合併的 PDF 檔 清單
  輸出：合併後的 PDF 清單

```java
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * PDF 匯出工具
 */
public class ExportPdfUtil {

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
```

## 範例 - 將多個 Word 檔，先轉換成 PDF，再進行合併

- Controller
  
  ```java
  @RestController
  @Tag(name = "PDF 報表匯出測試")
  @RequestMapping("/export/pdf")
  public class ExportPDFController {
      @Autowired
      private ExportService exportService;
  
      @Operation(summary = "PDF 檔案合併",
              description = "PDF 檔案合併")
      @GetMapping("/mergePDF")
      public ResponseEntity<Resource> mergePDF(@RequestParam String clientId) {
  
          var file = exportService.wordToPdf(clientId);
          var pdfFile = ExportPdfUtil.mergePDF(Arrays.asList(file,file));
          return ExportReponseUtil.responseEntity("客戶證號明細表(合併).pdf", pdfFile);
      }
  }
  ```
