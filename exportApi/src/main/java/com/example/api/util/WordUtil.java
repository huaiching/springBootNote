package com.example.api.util;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Word 匯出工具（使用 poi-tl）
 */
public class WordUtil {

    /**
     * 產生 Word 檔案
     *
     * @param modelFile 樣版路徑 (resources/templates/{modelFile})
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

        // 樣板位置
        String model = "/templates/" + modelFile;

        // 產生檔案
        try (
                InputStream inputStream = new ClassPathResource(model).getInputStream();
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
     * 產生 Word 檔案
     *
     * @param
     * @param context
     * @return
     */
    /**
     *
     * @param modelFile 樣版路徑 (resources/templates/{modelFile})
     * @param configure 列表渲染設定
     * @param context 資料內容（Map 對應樣版中 {{key}} 欄位）
     * @return 產出的 Word 檔案資料流（byte[]）
     */
    public static byte[] generateWordList(String modelFile, Configure configure, Map<String, Object> context) {
        // 參數驗證
        if (StringUtils.isEmpty(modelFile)) {
            throw new RuntimeException("樣版路徑 不可空白!!");
        }
        if (context == null) {
            throw new RuntimeException("資料內容 不可空白!!");
        }

        // 樣板位置
        String model = "/templates/" + modelFile;

        // 產生檔案
        try (
                InputStream inputStream = new ClassPathResource(model).getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            // 載入樣版並填入資料
            XWPFTemplate template = XWPFTemplate.compile(inputStream, configure).render(context);

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
     * @param modelFile 樣版路徑 (resources/templates/{modelFile})
     * @param contextList   資料內容 清單（Map 對應樣版中 {{key}} 欄位）
     * @return 產出的 Word 檔案資料流（byte[]）
     */
    public static byte[] generateWordMerge(String modelFile, List<Map<String, Object>> contextList) {
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
                    XWPFParagraph paragraph = mainWord.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.addBreak(BreakType.PAGE);
                    // 產出 並 合併 後續文件
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
            throw new RuntimeException("Word 產生失敗，樣版路徑：" + modelFile, e);
        }
    }

    /**
     * Word 多檔合併
     * @param wordFileList 要合併的 Word 資料流 清單
     * @return
     */
    public static byte[] mergeWord(List<byte[]> wordFileList) {
        // 產生檔案
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 使用第一個文件作為基礎
            NiceXWPFDocument mainWord = new NiceXWPFDocument(new ByteArrayInputStream(wordFileList.get(0)));

            try {
                // 合併後續文件
                for (int i = 1; i < wordFileList.size(); i++) {
                    // 在合併前先加入分頁符
                    XWPFParagraph paragraph = mainWord.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.addBreak(BreakType.PAGE);
                    // 合併後續文件
                    NiceXWPFDocument subWord = new NiceXWPFDocument(new ByteArrayInputStream(wordFileList.get(i)));
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
            throw new RuntimeException("Word 產生失敗", e);
        }


    }
}
