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
public class MergePdrUtil {
    /**
     * PDF 檔案合併
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
            merger.mergeDocuments(null);

        } catch (Exception e) {
            throw new RuntimeException("PDF 合併失敗: ", e);
        }
        return outputStream.toByteArray();
    }
}
```

## 範例 - 將多個 Word 檔，先轉換成 PDF，再進行合併

- Service
  
  ```java
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  import com.example.api.util.MergePdrUtil;
  import java.util.ArrayList;
  import java.util.List;
  
  @Service
  public class MergePdfService {
      @Autowired
      private HtmlToPdfService htmlToPdfService;
  
      public byte[] mergePDF() {
          List<byte[]> fileList = new ArrayList<>();
  
          for (int i = 1 ; i <= 5 ; i++) {
              byte[] file = htmlToPdfService.generatePdf();
              fileList.add(file);
          }
  
          return MergePdrUtil.mergePDF(fileList);
      }
  }
  ```

- Controller
  
  ```java
  @Operation(summary = "PDF 合併",
          description = "PDF 合併")
  @PostMapping("/mergePDF")
  public ResponseEntity<Resource> mergePDF() {
      var file = mergePdfService.mergePDF();
      return ReponseUtil.responseEntity("mergePDF.pdf", file);
  }
  ```
