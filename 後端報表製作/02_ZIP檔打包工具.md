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
public class ExportZipUtil {

    /**
     * 將多個檔案壓縮成 ZIP 格式，並回傳為 byte 陣列
     * @param fileList 要壓縮的檔案清單（檔名 -> 檔案內容）
     * @return 壓縮後的 ZIP 檔案內容（byte 陣列）
     */
    public static byte[] createZip(Map<String, byte[]> fileList) {
        // 參數驗證
        if (CollectionUtils.isEmpty(fileList)) {
            throw new RuntimeException("要打包的檔案 不可空白!!");
        }

        // 產生檔案
        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
       ) {
            for (Map.Entry<String, byte[]> file : fileList.entrySet()) {
                String fileName = file.getKey();
                byte[] fileData = file.getValue();

                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                zipOutputStream.write(fileData);
                zipOutputStream.closeEntry();
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Zip 產生失敗: ", e);
        }
    }
}
```
