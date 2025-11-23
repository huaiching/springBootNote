# 報表 - Word 檔案

使用 `poi-tl` 這個套件，來產生 Excel 檔案。

此套件 源自於 `apache.poi` 並進行優化，可以透過 `樣版檔` 來快速產生 Word 檔案。

其運作流程為

1. 樣版檔 設定 `底稿` 與 `套印變數`。

2. Java 設定 資料內容
   
   - 透過 `Map 的 key` 設定 `樣版檔 變數名稱`。
   
   - 透過 `Map 的 value` 設定 `顯示的數值`。

3. 將 Java 資料內容 套入 樣版檔 中，即可產生 Word 檔案。

![](./image/poi_tl_00.png)

---

## 安裝依賴

```xml
        <!-- apache.poi -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>5.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.3</version>
        </dependency>
        <!-- poi-tl -->
        <dependency>
            <groupId>com.deepoove</groupId>
            <artifactId>poi-tl</artifactId>
            <version>1.12.1</version>
            <!-- 排除其自帶的 POI -->
            <exclusions>
                <exclusion>
                    <groupId>org.apache.poi</groupId>
                    <artifactId>*</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

## 設定 - 樣版檔

1. 於 `/resources/templates/` 資料夾中 擺放樣版檔 (docx)。

2. 樣版檔 使用 office word 進行設計，設計時須注意：
   
   - 使用 `{{ }}` 來設定變數，這個變數要對應 `Java 資料內容 的 key` 相同。
     
     ```java
     // Java 資料內容
     Map<String, Object> context = new HashMap<>();
     context.put("names", clnt.getNames());
     context.put("clientId", clnt.getClientId());
     context.put("sex", SexEnum.getDescByCode(clnt.getSex()));
     ```

![](./image/poi_tl_01.png)

## 設定 - Java 資料內容

1. 先透過 任意方式 取得你想顯示的資料。

2. 使用 Map 來設定 Java 的 資料內容。
   
   ```java
   Map<String, Object> context = new HashMap<>();
   context.put("變數名稱", 數值);
   ```

3. 使用 下面的 util，即可 生成 Word 檔案。

## 工具

此工具為 poi-tl 產生 Word 檔案的語法，共提供兩種方法：

- 參數為 `context` 的方法，針對套印 一筆資料 的方法。

- 參數為 `contextList` 的方法，針對 多筆資料 的合併套印。

```java
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Word 匯出工具（使用 poi-tl）
 */
public class ExportWordUtil {

    /**
     * 產生 Word 檔案
     *
     * @param modelFile 樣版路徑（例如："templates/document_template.docx"，位於 classpath）
     * @param context   資料內容（Map 對應樣版中 {{key}} 欄位）
     * @return 產出的 Word 檔案資料流（byte[]）
     */
    public static byte[] generateWord(String modelFile, Map<String, Object> context) {
        // 參數驗證
        if (StringUtils.isEmpty(modelFile)) {
            throw new RuntimeException("樣版路徑 不可空白!!");
        }
        if (context == null) {
            throw new RuntimeException("資料內容 不可空白!!");
        }

        // 產生檔案
        try (
                InputStream inputStream = new ClassPathResource(modelFile).getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            // 載入樣版並填入資料
            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(context);

            // 將結果寫入 outputStream 並關閉資源
            template.writeAndClose(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Word 產生失敗，樣版路徑：" + modelFile, e);
        }
    }


    /**
     * 產生 Word 檔案 (合併列印)
     *
     * @param modelFile 樣版路徑（例如："templates/document_template.docx"，位於 classpath）
     * @param contextList   資料內容 清單（Map 對應樣版中 {{key}} 欄位）
     * @return 產出的 Word 檔案資料流（byte[]）
     */
    public static byte[] generateWord(String modelFile, List<Map<String, Object>> contextList) {
        // 參數驗證
        if (StringUtils.isEmpty(modelFile)) {
            throw new RuntimeException("樣版路徑 不可空白!!");
        }
        if (CollectionUtils.isEmpty(contextList)) {
            throw new RuntimeException("資料內容 不可空白!!");
        }

        // 產生檔案
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 使用第一個文件作為基礎
            var firstWord = generateWord(modelFile, contextList.get(0));
            NiceXWPFDocument mainWord = new NiceXWPFDocument(new ByteArrayInputStream(firstWord));

            try {
                // 合併後續文件
                for (int i = 1; i < contextList.size(); i++) {
                    // 在合併前先加入分頁符
                    addPageBreak(mainWord);

                    var tmpWord = generateWord(modelFile, contextList.get(i));
                    NiceXWPFDocument subWord = new NiceXWPFDocument(new ByteArrayInputStream(tmpWord));
                    mainWord = mainWord.merge(subWord);
                    subWord.close();
                }

                mainWord.write(outputStream);
                return outputStream.toByteArray();

            } catch (Exception e) {
                throw new RuntimeException("Word 合併失敗：", e);
            } finally {
                mainWord.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Word 產生失敗，樣板路徑：" + modelFile, e);
        }
    }

    /**
     * 在文件末尾加入分頁符
     */
    private static void addPageBreak(NiceXWPFDocument doc) {
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);  // 加入分頁符
    }
}
```

## 範例

- Service
  
  - 先透過 ClntRepository 取得 要顯示的資料。
  
  - 再配合 樣版檔變數，設定 context。
  
  - 最後透過 工具(util) 執行。
  
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
  }
  ```

- Controller
  
  ```java
  @RestController
  @Tag(name = "Word 報表匯出測試")
  @RequestMapping("/export/word")
  public class ExportWordController {
      @Autowired
      private ExportService exportService;
  
      @Operation(summary = "Word 報表測試",
              description = "Word 報表測試",
              operationId = "test")
      @GetMapping("/test")
      public ResponseEntity<Resource> test(@RequestParam String clientId) {
  
          var file = exportService.wordTest(clientId);
          return ExportReponseUtil.responseEntity("客戶證號明細表.docx", file);
      }
  }
  ```

- 成果
  
  ![](./image/poi_tl_02.png)
