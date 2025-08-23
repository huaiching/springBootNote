# ResponseEntity 工具

這個 ResponseEntity 是針對 檔案下載 API 進行設計，
檔名 放置於 `Header` 的 `content-disposition` 傳輸給前端使用。

---

## 工具

```java
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ExportReponseUtil {

    /**
     * 產生 檔案下載的 ResponseEntity
     * @param fileName 檔案名稱
     * @param fileByte 檔案資料流
     * @return
     */
    public static ResponseEntity<Resource> responseEntity(String fileName, byte[] fileByte) {
        Charset utf8 = StandardCharsets.UTF_8;
        // 文件打包
        Resource resource = new ByteArrayResource(fileByte);
        // 文件下载
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment",
                URLEncoder.encode(fileName, utf8));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
```
