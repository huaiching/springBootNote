# 報表 - Word 轉 PDF 檔案

## 安裝依賴

- Word 轉 PDF：
  使用套件 `fr.opensagres.poi.xwpf.converter.pdf`

```xml
        <!-- fr.opensagres.poi.xwpf.converter.pdf -->
        <dependency>
            <groupId>fr.opensagres.xdocreport</groupId>
            <artifactId>fr.opensagres.poi.xwpf.converter.pdf</artifactId>
            <version>2.0.3</version>
        </dependency>
```

## 工具

- wordToPDF
  功能：Word 轉為 PDF
  輸入：要轉換的 Word 檔
  輸出：轉換後的 PDF 檔

```java
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
}
```

## 範例 - Word 轉成 PDF

先透過 poi-tl 產生 word 檔，在將其轉換為 PDF。

- Service
  
  ```java
  @Service
  public class ExportServiceImpl implements ExportService {
      @Autowired
      private ClntRepository clntRepository;
  
      /**
       * 單筆列印客戶證號明細表
       *
       * @param clientId 客戶證號
       * @return
       */
      @Override
      public byte[] wordTest(String clientId) {
          Clnt clnt = clntRepository.findById(clientId).get();
  
          Map<String, Object> context = new HashMap<>();
          context.put("names", clnt.getNames());
          context.put("clientId", clnt.getClientId());
          context.put("sex", SexEnum.getDescByCode(clnt.getSex()));
  
          return ExportWordUtil.generateWord("/templates/sample.docx", context);
      }
  
      /**
       * 列印單筆客戶證號明細表 並轉成 PDF
       *
       * @param clientId 客戶證號
       * @return
       */
      @Override
      public byte[] wordToPdf(String clientId) {
          var wordFile = wordTest(clientId);
          return ExportPdfUtil.wordToPDF(wordFile);
      }
  }
  ```

- Controller
  
  ```java
  @RestController
  @Tag(name = "PDF 報表匯出測試")
  @RequestMapping("/export/pdf")
  public class ExportPDFController {
      @Autowired
      private ExportService exportService;
  
      @Operation(summary = "Word 轉成 PDF",
              description = "Word 轉成 PDF",
              operationId = "wordToPDF")
      @GetMapping("/wordToPDF")
      public ResponseEntity<Resource> wordToPDF(@RequestParam String clientId) {
  
          var file = exportService.wordToPdf(clientId);
          return ExportReponseUtil.responseEntity("客戶證號明細表.pdf", file);
      }
  }
  ```
