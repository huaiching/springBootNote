# Word 轉 PDF 檔案

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
public class WordToPdfUtil {

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
  import org.springframework.stereotype.Service;  
  import com.example.api.util.WordToPdfUtil;
  import com.example.api.util.WordUtil;  
  import java.util.HashMap;
  import java.util.Map;
  
  @Service
  public class WordToPdfService {
      /**
       * word 轉 PDF
       *
       * @return
       */
      public byte[] generate() {
          String userId = "A123456789";
          String userName = "測試人員";
          String userSex = "男性";
          Map<String, Object> context = new HashMap<>();
          context.put("names", userName);
          context.put("clientId", userId);
          context.put("sex", userSex);
  
          byte[] file = WordUtil.generateWord("/templates/sample.docx", context);
  
          return WordToPdfUtil.wordToPDF(file);
      }
  }
  ```

- Controller
  
  ```java
  @Operation(summary = "word 轉 PDF",
          description = "word 轉 PDF")
  @PostMapping("/generateWord")
  public ResponseEntity<Resource> generateWord() {
  
      var file = wordToPdfService.generate();
      return ReponseUtil.responseEntity("wordToPdf.pdf", file);
  }
  ```
