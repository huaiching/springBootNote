# 報表 - html 轉 PDF 檔案

## 安裝依賴

- html 轉 PDF：
  使用套件 `openhtmltopdf`

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>com.openhtmltopdf</groupId>
            <artifactId>openhtmltopdf-pdfbox</artifactId>
            <version>1.0.10</version>
        </dependency>
```

## 工具

- htmlToPdf
  功能：html 轉 PDF 的 核心轉換程式
  
  輸入：TemplateEngine、樣板檔案路徑、資料內容

```java
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

/**
 * PDF 匯出工具
 */
public class ExportPdfUtil {

    /**
     * html 轉 PDF
     * @param templateEngine Thymeleaf 的 TemplateEngine，用於解析 HTML 樣板
     * @param modelFile 樣板檔案 (resources/templates/{templateName}.html)
     * @param dataList 資料內容
     * @return
     */
    public static byte[] htmlToPdf(TemplateEngine templateEngine, String modelFile, Map<String, Object> dataList) {
        // 設定變數
        Context context = new Context();
        context.setVariables(dataList);

        // 生成 HTML
        String html = templateEngine.process(modelFile, context);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // HTML 轉 PDF
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            // 設定中文字型
            File fontFile = new File("src/main/resources/templates/fonts/kaiu.ttf");
            builder.useFont(fontFile, "標楷體");
            File fontFile2 = new File("src/main/resources/templates/fonts/3of9Barcode.ttf");
            builder.useFont(fontFile2, "條碼");
            // 資料輸出
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF 生成失敗", e);
        }
    }
}
```

## 使用方式

程式開發流程，可以分成以下幾個步驟：

1. `html 樣板檔` 設定 `底稿`、`套印變數`、`中文字型`。
   
   - `樣版檔` 放在 `resources/templates/` 裡面。
   
   - `中文字型`：放在 `resources/templates/fonts` 裡面。

2. 取得資料，對應 樣板檔變數
   
   將資料整理成 `Context`，並透過 `context.setVariable(key, value);` 設定資料。
   
   - `key` 設定 `樣板檔 變數名稱`。
   
   - `value` 設定 `顯示的數值`。

3. 使用 上方的工具，將 `TemplateEngine`、`樣版路徑`、`Map 變數資料` 作為參數傳入。
   
   - `TemplateEngine`：透過 spring boot 注入生成
     
     ```java
     @Autowired
     private TemplateEngine templateEngine;
     ```
   
   - `樣版路徑`：只要設定 `resources/templates/` 後面的路徑
     
     如：`resources/templates/policy.html` 只需要設定 `policy.html`

## html 樣板檔

### 1. 頁面整體樣式

- 頁面 內文 的 整體的 字型設定 與 字體大小設定。

- 必須要有 中文字型，否則 中文會顯示 `#`。

```css
body {
  font-family: "標楷體", "Noto Serif CJK TC", sans-serif;
  font-size: 14px; /* 預設字體大小 */
}
```

### 3. 頁眉、頁腳 與 頁面布局

透過 CSS 的 `@page` 規則，控制紙張大小、邊距、頁眉、頁腳。

以下為範例與頁碼語法說明：

#### 3.1. 每一頁都要顯示

```css
@page {
  size: A4; /* 紙張樣式：A4 = 直式 A4 ; A4 landscape = 橫式 A4 */
  margin: 110pt 30pt 30pt 30pt; /* 頁面邊距：上下左右 30pt */
  @top-center {
    content: element(header) element(headerInfo); /* 頁眉顯示 header 和 headerInfo 元素內容 */
    padding-top: 30px;
  }
  @bottom-center {
    font-family: "標楷體"; /* 頁腳字體 */
    content: "第 " counter(page) " 頁 / 共 " counter(pages) " 頁"; /* 頁腳顯示頁碼 */
    padding-bottom: 30px;
  }
}

header {
  display: block; /* 頁眉為塊級元素 */
  text-align: center; /* 文字居中 */
  font-weight: bold; /* 文字加粗 */
  font-size: 16px; /* 頁眉字體大小 */
  position: running(header); /* 定義為運行元素，確保每頁顯示 */
}
.headerInfo {
  width: 100%; /* 表格寬度填滿容器 */
  font-size: 14px; /* 字體大小 */
  position: running(headerInfo); /* 定義為運行元素，確保每頁顯示 */
}
/* 頁碼顯示樣式 */
.page-number::after {
  content: "頁碼： " counter(page) " / " counter(pages) ;
  font-family: "標楷體";
  font-size: 14px;
}
```

```html
<header class="header">
  <div>三商美邦人壽保險股份有限公司</div>
  <div>保單資料表</div>
</header>

<div class="headerInfo">
  <table>
    <tr style="border: none">
      <td style="border: none; text-align: left">報表代碼：text001</td>
      <td style="border: none; text-align: right">【機密資料】</td>
    </tr>
    <tr style="border: none">
      <td style="border: none; text-align: left">列印單位：90251</td>
      <td style="border: none; text-align: right" class="page-number"></td>
    </tr>
  </table>
  <hr/>
</div>
```

- `@top-center`：頁眉 設定
  
  - `padding-top`：上方的留白寬度設定
  
  - 若 頁眉 需要顯示 頁碼，則 該行 必須透過 CSS 進行設定 。
    
    > 內容 設定在 `content` 中。

- `@bottom-center`：頁腳 設定
  
  - `padding-bottom`：下方的留白寬度設定

- 因為 `@top-center` 和 `@bottom-center` 不會吃 `@page` 裡面的邊寬設定，
  
  因此 需要另外進行設定。

- `headerInfo` 範例設定是 兩行，當行數不同時，需自行調整 邊寬 和 留白 設定。

- **頁碼語法說明**：
  
  - `counter(page)`：顯示當前頁碼，從 1 開始計數。
  - `counter(pages)`：顯示總頁數，自動計算。
  - **範例輸出**：`第 1 頁 / 共 5 頁`。 

#### 3.2. 第一頁才要顯示

```css
@page :first {
  @bottom-right {
    content: element(bottomRight);
  }
}
@page :not(:first) {
  @bottom-right {
    content: none;
  }
}
.bottomRight {
  font-size: 14px;
  text-align: right;
  position: running(bottomRight); /* 定義為運行元素 */
}

/* 條碼樣式 */
.barcode {
  font-family: "條碼";
  font-size: 30px;
  text-align: right;
}
```

```html
<div class="bottomRight">
  <div class="barcode">TEST123456</div>
  <div style="text-align: right">TEST123456</div>
</div>
```

此範例為 第一頁 右下角 顯示 條碼

- `@page :first`：第一頁 的設定

- `@page :not(:first)`：非 第一頁 的設定

### 4. 分隔線

```css
hr {
  border: 0.1px solid #000; /* 細實線分隔 */
}
```

```html
<hr/>
```

- **說明**：`hr` 用於區分報表區塊。
- 根據需要 可以調整 分隔線的 粗細設定。

### 5. 強制換頁

透過 CSS 來達成 強制換頁，有需要的可以直接使用。

```css
.pageChange {
  page-break-before: always; /* 在元素前強制換頁 */
}
```

```html
<div class="pageChange"></div>
```

### 6. Thymeleaf 動態數據處理

變數使用 `${變數名稱}` 標示，下面會簡單介紹如何使用。

#### 6.1 單值數據

```html
<div>姓名：<span th:text="${names}"></span></div>
```

- **說明**：`th:text="${policyNo}"` 顯示後端傳入的 `policyNo` 值。

#### 6.2 表格數據循環

- 當有 陣列資料 需要顯示，可以透過 `<table>` 來呈現資料。

- 建議在設計欄位的時候，可以使用 `Excel` 進行設計，
  
  並用 合併儲存格 的方式，來調整寬度 (這樣會比較好控制)。

- 表格整體的 CSS 樣式設計，這裡設定為
  
  - 寬度 100% 佔滿畫面
  
  - 細框線 + 標題 淺藍色
  
  - 文字居中 + 字體 14px
  
  - 跨頁時 要重複顯示 標題。
  
  ```css
  /* 表格整體樣式 */
  table {
    width: 100%; /* 表格寬度填滿容器 */
    border-collapse: collapse; /* 合併邊框，避免雙線效果 */
    -fs-table-paginate: paginate; /* 啟用表格分頁功能 */
    -fs-page-break-min-height: 1.5cm; /* 確保分頁時有足夠空間 */
  }
  /* 表格通用樣式 */
  th, td {
    border: 0.1mm solid; /* 儲存格邊框為 0.1mm 實線 */
    padding: 6px; /* 儲存格內間距 */
    text-align: center; /* 文字居中 */
    font-size: 14px; /* 字體大小 */
    line-height: 1.2; /* 統一行高，確保對齊 */
  }
  /* 表格顏色樣式 - 表頭顏色 */
  th {
    background-color: #66bce1; /* 表頭背景色為淺藍色 */
  }
  /* 表頭分頁設置 */
  thead {
    display: table-header-group; /* 確保表頭在每頁重複顯示 */
  }
  /* 表格內容行分頁設置 */
  tbody tr {
    page-break-inside: avoid; /* 避免行內分頁 */
    break-inside: avoid; /* 現代分頁控制，確保行完整性 */
  }
  ```

##### 6.2.1. 單行的表格

- 透過 CSS 設定 奇數行 為 淺灰色底，使其比較好閱讀。

- `<colgroup>`：表格的 欄位數量 與 占比。
  
  - 透過 `width` 來調整 每一行 的占比。

- `<thead>`：表格的 標題
  
  - 寬度 在 `<colgroup>` 裡面設定。　　　

- `<tbody>`：表格的 內容 (顯示數據)
  
  - `<tbody class="color2">`：套用 CSS 樣式。
  
  - `<tr th:each="a : ${addrList}">`：遍歷 `addrList` 列表，`a` 為單筆數據。
  
  - `<td th:text="${a.addrInd}"></td>`：顯示 `a` 物件的 `addrInd` 屬性數據。
    
    > 有需要可以透過 `style="text-align: right;"` 調整 文字位置。

```css
/* 表格顏色樣式 - 奇數行顏色 */
.color2 tr:nth-child(odd) {
  background-color: #f2f2f2; /* 奇數行淺灰 */
}
```

```html
<table>
  <colgroup>
    <col style="width: 14%;"/>
    <col style="width: 56%;"/>
    <col style="width: 28%;"/>
  </colgroup>
  <thead>
    <tr>
      <th>地址指示</th>
      <th>地址</th>
      <th>電話</th>
    </tr>
  </thead>
  <tbody class="color2">
    <tr th:each="a : ${addrList}">
      <td th:text="${a.addrInd}"></td>
      <td th:text="${a.address}"></td>
      <td th:text="${a.tel}"></td>
    </tr>
  </tbody>
</table>
```

##### 6.2.2. 多行的表格

如果 每一筆 的數據過多，單行 無法呈現，可以使用此方法。

- 為了 資料好辨識，透過 CSS 修改 每筆資料 第一行 的底色。

- `<colgroup>`：表格的 欄位數量 與 占比。
  
  - 每一行 的 欄位數量 必須相同。
  
  - 建議 每一欄都設定等寬，透過 合併儲存格 來調整 欄位的寬度。
  
  - 建議使用 Excel 來進行 版型設計。

- `<thead>`：表格的 標題
  
  - 假設 需要 兩行，就會有 兩組 `<tr>`。
  
  - 所有欄位 都透過 合併儲存格 來 設定 佔比。
    
    如：`colspan="2"`，數字為 佔幾格。

- `<tbody>`：表格的 內容 (顯示數據)
  
  - 所有欄位 都透過 合併儲存格 來 設定 佔比，
    
    與 `<thead>` 的設定相同。
  
  - `th:each="d : ${b.coInfoList}"`
    
    遍歷 `coInfoList` 列表，`d` 為單筆數據。
  
  - `th:text="${d.clientIdent}"`
    
    顯示 `d` 物件的 `clientIdent` 屬性數據。
  
  - `th:with="c=${b.poInfo}"`：設定 別名。
  
  - 若 數據 需要 兩層迴圈 顯示。
    
    - 第一層 設定在 `<tbody>`。
      
      如：`<tbody th:each="b : ${policyList}">`
      
      　　此處 變數 為 JAVA 中 設定的名稱。
    
    - 第二層 設定在 `<tr>`。
      
      如：`<tr class="color1" th:with="c=${b.poInfo}">`
      
      　　此數 變數 為 第一層數據 中的 陣列資料。
  
  - `#numbers.formatDecimal(c.faceAmt, 0, 'COMMA', 2, 'POINT')`：
    
    - **參數 1 (c.faceAmt)**：要格式化的數字。
    - **參數 2 (0)**：最小整數位數，0 表示不強制補零。
    - **參數 3 ('COMMA')**：千位分隔符，使用逗號（,）；`NONE` 為 不需要。
    - **參數 4 (2)**：小數點後位數，保留 2 位。
    - **參數 5 ('POINT')**：小數點符號，使用點號（.）；`NONE` 為 不需要。
    - **範例輸出**：`1234567.89` 格式化為 `1,234,567.89`。

```css
/* 表格顏色樣式 - 表身顏色 */
.color1 td {
  background-color: #c6dee8;
}
```

```html
<table>
  <colgroup>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
    <col style="width: 9%;"/>
  </colgroup>
  <thead>
    <tr>
      <th colspan="2">保單號碼</th>
      <th colspan="1">狀態</th>
      <th colspan="2">生效日期</th>
      <th colspan="2">繳費日期</th>
      <th colspan="1">理賠</th>
      <th colspan="1">批註</th>
      <th colspan="1">告知</th>
      <th colspan="1">弱體</th>
    </tr>
    <tr>
      <th colspan="1"></th>
      <th colspan="1">關係</th>
      <th colspan="2">險種</th>
      <th colspan="1">版數</th>
      <th colspan="2">保額</th>
      <th colspan="2">生效日期</th>
      <th colspan="2">變更生效日</th>
    </tr>
  </thead>
  <tbody th:each="b : ${policyList}">
    <tr class="color1" th:with="c=${b.poInfo}">
      <td colspan="2" th:text="${c.policyNo}"></td>
      <td colspan="1" th:text="${c.poStsCode}"></td>
      <td colspan="2" th:text="${c.poIssueDate}"></td>
      <td colspan="2" th:text="${c.paidToDate}"></td>
      <td colspan="1" th:text="${c.claimInd}"></td>
      <td colspan="1" th:text="${c.remarkInd}"></td>
      <td colspan="1" th:text="${c.informInd}"></td>
      <td colspan="1" th:text="${c.weakInd}"></td>
    </tr>
    <tr th:each="d : ${b.coInfoList}">
      <td colspan="1"></td>
      <td colspan="1" th:text="${d.clientIdent}"></td>
      <td colspan="2" th:text="${d.planCode}"></td>
      <td colspan="1" th:text="${d.rateScale}"></td>
      <td colspan="2" style="text-align: right;" th:text="${#numbers.formatDecimal(d.faceAmt, 0, 'COMMA', 2, 'POINT')}"></td>
      <td colspan="2" th:text="${d.coIssueDate}"></td>
      <td colspan="2" th:text="${d.coChangeDate}"></td>
    </tr>
  </tbody>
</table>
```

範例的數據，對應 JAVA 數據為

```java
public class PolicyDTO {
    private PoInfoDTO poInfo;
    private List<CoInfoDTO> coInfoList;

    ... setting 和 getting
}
```

## JAVA 設定

- 資料取得後，透過 Context 進行設定。
  
  ```java
  Context context = new Context();
  context.setVariable("policyNo", policyDTO.getPolicyNo());
  context.setVariable("poStsCode", policyDTO.getPoStsCode());
  context.setVariable("o1Name", policyDTO.getO1Name());
  context.setVariable("i1Name", policyDTO.getI1Name());
  context.setVariable("poIssueDate", policyDTO.getPoIssueDate());
  context.setVariable("coverages", policyDTO.getCoList());
  ```

- 透過 utils 的工具，進行檔案產出。
  
  ```java
  ExportPdfUtil.htmlToPdf(templateEngine, "policy.html", context);
  ```

## 範例

- 樣板檔
  
  ```html
  <!DOCTYPE html>
  <html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8" />
    <style>
      /* 設置頁面整體樣式 */
      body {
        font-family: "標楷體", "Noto Serif CJK TC", sans-serif;
        font-size: 14px; /* 預設字體大小 */
      }
  
      /* 頁面佈局設定 */
      @page {
        size: A4; /* 紙張樣式：A4 = 直式 A4 ; A4 landscape = 橫式 A4 */
        margin: 110pt 30pt 60pt 30pt;
        @top-center {
          content: element(header) element(headerInfo); /* 頁眉顯示 header 和 headerInfo 元素內容 */
          padding-top: 30px;
        }
        @bottom-center {
          font-family: "標楷體"; /* 頁腳字體 */
          content: "第 " counter(page) " 頁 / 共 " counter(pages) " 頁"; /* 頁腳顯示頁碼 */
          padding-bottom: 30px;
        }
      }
      @page :first {
        @bottom-right {
          content: element(bottomRight);
        }
      }
      @page :not(:first) {
        @bottom-right {
          content: none;
        }
      }
  
      /* 頁眉樣式 */
      header {
        display: block; /* 頁眉為塊級元素 */
        text-align: center; /* 文字居中 */
        font-weight: bold; /* 文字加粗 */
        font-size: 16px; /* 頁眉字體大小 */
        position: running(header); /* 定義為運行元素，確保每頁顯示 */
      }
      .headerInfo {
        width: 100%; /* 表格寬度填滿容器 */
        font-size: 14px; /* 字體大小 */
        position: running(headerInfo); /* 定義為運行元素，確保每頁顯示 */
      }
      .bottomRight {
        font-size: 14px;
        text-align: right;
        position: running(bottomRight); /* 定義為運行元素 */
      }
  
      /* 分隔線樣式 */
      hr {
        border: 0.1px solid #000;
      }
  
      /* 強制換頁設定 */
      .pageChange {
        page-break-before: always;
      }
  
      /* 頁碼顯示樣式 */
      .page-number::after {
        content: "頁碼： " counter(page) " / " counter(pages) ;
        font-family: "標楷體";
        font-size: 14px;
      }
  
      /* 條碼樣式 */
      .barcode {
        font-family: "條碼";
        font-size: 30px;
        text-align: right;
      }
  
      /* 表格整體樣式 */
      table {
        width: 100%; /* 表格寬度填滿容器 */
        border-collapse: collapse; /* 合併邊框，避免雙線效果 */
        -fs-table-paginate: paginate; /* 啟用表格分頁功能 */
        -fs-page-break-min-height: 1.5cm; /* 確保分頁時有足夠空間 */
      }
  
      /* 表格通用樣式 */
      th, td {
        border: 0.1mm solid; /* 儲存格邊框為 0.1mm 實線 */
        padding: 6px; /* 儲存格內間距 */
        text-align: center; /* 文字居中 */
        font-size: 14px; /* 字體大小 */
        line-height: 1.2; /* 統一行高，確保對齊 */
      }
  
      /* 表格顏色樣式 - 表頭顏色 */
      th {
        background-color: #66bce1; /* 表頭背景色為淺藍色 */
      }
      /* 表格顏色樣式 - 表身顏色 */
      .color1 td {
        background-color: #c6dee8;
      }
      /* 表格顏色樣式 - 奇數行顏色 */
      .color2 tr:nth-child(odd) {
        background-color: #f2f2f2; /* 奇數行淺灰 */
      }
  
      /* 重點文字顏色樣式 */
      .highlight {
        color: red; /* 文字顏色 */
        background-color: #f5f5f5; /* 背景色 */
        font-weight: bold; /* 加粗 */
        padding: 2px 4px; /* 內間距 */
      }
  
      /* 表頭分頁設置 */
      thead {
        display: table-header-group; /* 確保表頭在每頁重複顯示 */
      }
  
      /* 表格內容行分頁設置 */
      tbody tr {
        page-break-inside: avoid; /* 避免行內分頁 */
        break-inside: avoid; /* 現代分頁控制，確保行完整性 */
      }
    </style>
  </head>
  <body>
  <header class="header">
    <div>三商美邦人壽保險股份有限公司</div>
    <div>客戶資料表</div>
  </header>
  
  <div class="headerInfo">
    <table>
      <tr style="border: none">
        <td style="border: none; text-align: left">報表代碼：text001</td>
        <td style="border: none; text-align: right">【機密資料】</td>
      </tr>
      <tr style="border: none">
        <td style="border: none; text-align: left">列印單位：90251</td>
        <td style="border: none; text-align: right" class="page-number"></td>
      </tr>
    </table>
    <hr/>
  </div>
  
  <div class="bottomRight">
    <div class="barcode">TEST123456</div>
    <div style="text-align: right">TEST123456</div>
  </div>
  
  <h3>基本資料</h3>
  <div>姓名：<span th:text="${names}"></span></div>
  <div>性別：<span th:text="${sex}"></span></div>
  <div>年齡：<span th:text="${age}"></span></div>
  
  <br/>
  
  <h3>聯絡資料</h3>
  <table>
    <colgroup>
      <col style="width: 14%;"/>
      <col style="width: 56%;"/>
      <col style="width: 28%;"/>
    </colgroup>
    <thead>
      <tr>
        <th style="width: 1%;">地址指示</th>
        <th style="width: 4%;">地址</th>
        <th style="width: 2%;">電話</th>
      </tr>
    </thead>
    <tbody class="color2">
      <tr th:each="a : ${addrList}">
        <td style="width: 1%; text-align: center;" th:text="${a.addrInd}"></td>
        <td style="width: 4%; text-align: center;" th:text="${a.address}"></td>
        <td style="width: 2%; text-align: center;" th:text="${a.tel}"></td>
      </tr>
    </tbody>
  </table>
  
  <br/>
  
  <h3>保單資料</h3>
  <table>
    <colgroup>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
      <col style="width: 9%;"/>
    </colgroup>
    <thead>
      <tr>
        <th colspan="2">保單號碼</th>
        <th colspan="1">狀態</th>
        <th colspan="2">生效日期</th>
        <th colspan="2">繳費日期</th>
        <th colspan="1">理賠</th>
        <th colspan="1">批註</th>
        <th colspan="1">告知</th>
        <th colspan="1">弱體</th>
      </tr>
      <tr>
        <th colspan="1"></th>
        <th colspan="1">關係</th>
        <th colspan="2">險種</th>
        <th colspan="1">版數</th>
        <th colspan="2">保額</th>
        <th colspan="2">生效日期</th>
        <th colspan="2">變更生效日</th>
      </tr>
    </thead>
    <tbody th:each="b : ${policyList}">
      <tr class="color1" th:with="c=${b.poInfo}">
        <td colspan="2" th:text="${c.policyNo}"></td>
        <td colspan="1" th:text="${c.poStsCode}"></td>
        <td colspan="2" th:text="${c.poIssueDate}"></td>
        <td colspan="2" th:text="${c.paidToDate}"></td>
        <td colspan="1" th:text="${c.claimInd}"></td>
        <td colspan="1" th:text="${c.remarkInd}"></td>
        <td colspan="1" th:text="${c.informInd}"></td>
        <td colspan="1" th:text="${c.weakInd}"></td>
      </tr>
      <tr th:each="d : ${b.coInfoList}">
        <td colspan="1"></td>
        <td colspan="1" th:text="${d.clientIdent}"></td>
        <td colspan="2" th:text="${d.planCode}"></td>
        <td colspan="1" th:text="${d.rateScale}"></td>
        <td colspan="2" style="
        text-align: right;" th:text="${#numbers.formatDecimal(d.faceAmt, 0, 'COMMA', 2, 'POINT')}"></td>
        <td colspan="2" th:text="${d.coIssueDate}"></td>
        <td colspan="2" th:text="${d.coChangeDate}"></td>
      </tr>
    </tbody>
  </table>
  
  <br/>
  
  <div class="pageChange"/>
  <p>備註：</p>
  <p>此為<span class="highlight">練習用範例</span>，相關資料均為假資料。</p>
  </body>
  </html>
  ```

- service
  
  ```java
  import com.example.api.dto.htmltopdf.AddrDTO;
  import com.example.api.dto.htmltopdf.CoInfoDTO;
  import com.example.api.dto.htmltopdf.PoInfoDTO;
  import com.example.api.dto.htmltopdf.PolicyDTO;
  import com.example.api.util.ExportPdfUtil;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  import org.thymeleaf.TemplateEngine;
  import org.thymeleaf.context.Context;
  
  import java.util.ArrayList;
  import java.util.List;
  
  @Service
  public class PdfService {
  
      @Autowired
      private TemplateEngine templateEngine;
  
      public byte[] generatePolicyPdf() {
          // 模擬資料
          // 基本資料
          String names = "測試員 A123456789";
          String sex = "男性";
          Integer age = 25;
          // 聯絡資料
          List<AddrDTO> addrList = new ArrayList<>();
          for (int i = 1 ; i <= 10 ; i++) {
              AddrDTO addr = new AddrDTO();
              addr.setAddrInd(String.valueOf(i));
              addr.setAddress("台北市內湖區石潭路58號"+i+"樓");
              addr.setTel("02-23455511");
              addrList.add(addr);
          }
          // 保單資料
          List<PolicyDTO> policyList = new ArrayList<>();
          for (int i = 0 ; i < 5 ; i++) {
              PolicyDTO policyDTO = new PolicyDTO();
              // 保單
              PoInfoDTO poInfo = new PoInfoDTO();
              poInfo.setPolicyNo("10000000000"+i);
              poInfo.setPoStsCode("42");
              poInfo.setPoIssueDate("100/01/10");
              poInfo.setPaidToDate("115/01/10");
              poInfo.setClaimInd("N");
              poInfo.setRemarkInd("N");
              poInfo.setInformInd("N");
              poInfo.setWeakInd("N");
              policyDTO.setPoInfo(poInfo);
              // 保障
              List<CoInfoDTO> coInfoList = new ArrayList<>();
              for (int j = 1 ; j <= 3 ; j++) {
                  CoInfoDTO coInfo = new CoInfoDTO();
                  coInfo.setClientIdent("I1");
                  coInfo.setPlanCode("ABCD"+i);
                  coInfo.setRateScale("0");
                  coInfo.setFaceAmt(1000000.00);
                  coInfo.setCoIssueDate("100/01/10");
                  coInfo.setCoChangeDate("100/01/20");
                  coInfoList.add(coInfo);
              }
              policyDTO.setCoInfoList(coInfoList);
              policyList.add(policyDTO);
          }
  
          // 設定變數
          Context context = new Context();
          context.setVariable("names", names);
          context.setVariable("sex", sex);
          context.setVariable("age", age);
          context.setVariable("addrList", addrList);
          context.setVariable("policyList", policyList);
  
          return ExportPdfUtil.htmlToPdf(templateEngine, "客戶資料表.html", context);
      }
  }
  ```

- controller
  
  ```java
  @RestController
  public class PdfController {
  
      @Autowired
      private PdfService pdfService;
  
      @GetMapping("/policy/pdf")
      public ResponseEntity<Resource> generatePdf() {
          var file = pdfService.generatePolicyPdf();
          return ExportReponseUtil.responseEntity("policy.pdf", file);
      }
  }
  ```
