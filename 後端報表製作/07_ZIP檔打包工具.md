# ZIP檔打包工具

此工具，可以將 多個檔案，打包為 ZIP檔，方便提供用戶下載。

```java
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip 打包工具
 */
public class ZipUtil {

    /**
     * 將多個檔案壓縮成 ZIP 格式，並回傳為 byte 陣列
     * @param fileList 要壓縮的檔案 Map 清單（key=檔名, value=檔案內容）
     * @return 產出的 ZIP 檔案資料流（byte[]）
     */
    public static byte[] createZip(Map<String, byte[]> fileList) {
        // 參數驗證
        if (CollectionUtils.isEmpty(fileList)) {
            throw new RuntimeException("要打包的檔案 不可空白!!");
        }

        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)
        ) {
            for (Map.Entry<String, byte[]> file : fileList.entrySet()) {
                String fileName = file.getKey();
                byte[] fileData = file.getValue();

                if (fileName == null || fileName.isBlank()) {
                    throw new RuntimeException("檔名不可為空");
                }
                if (fileData == null) {
                    throw new RuntimeException("檔案內容不可為空: " + fileName);
                }

                // 確保使用相對路徑格式
                String safeFileName = fileName.replaceAll("\\\\", "/");

                zipOutputStream.putNextEntry(new ZipEntry(safeFileName));
                zipOutputStream.write(fileData);
                zipOutputStream.closeEntry();
            }

            // 確保壓縮流正確結束
            zipOutputStream.finish();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Zip 產生失敗: ", e);
        }
    }
}
```

## 範例

- Service
  
  ```java
  import com.example.api.util.ZipUtil;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  import java.util.HashMap;
  import java.util.Map;
  
  @Service
  public class ZipService {
      @Autowired
      private ExcelService excelService;
      @Autowired
      private WordService wordService;
  
      /**
       * Zip 檔案打包
       * @return
       */
      public byte[] generateZip() {
          // 產生 excel
          byte[] excelFile = excelService.excelEach();
  
          // 產生 word
          byte[] wordFile = wordService.generateWord();
  
          // 打包為 zip
          Map<String, byte[]> fileList = new HashMap<>();
          fileList.put("excel.xlsx", excelFile);
          fileList.put("word.docx", wordFile);
  
          return ZipUtil.createZip(fileList);
      }
  }
  ```

- Controller
  
  ```java
  @Operation(summary = "Zip 檔案打包",
          description = "Zip 檔案打包")
  @PostMapping("/generateZip")
  public ResponseEntity<Resource> generateZip() {
  
      var file = zipService.generateZip();
      return ReponseUtil.responseEntity("zipFilt.zip", file);
  }
  ```
