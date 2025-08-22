# OpenHTMLtoPDF 樣板檔

此文件 會逐一進行說明，但是 使用時 可以複製 範例檔案 進行修改即可。

## 1. 檔案結構

以下是範例檔案的 HTML 結構，透過註解說明各部分功能：

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8" />
  <style>
    /* CSS 樣式定義，控制頁面佈局、字體、表格等 */ 
  </style>
</head>
<body>
  <header class="header">
    <!-- 頁眉內容，如公司名稱或報表標題 -->
  </header>
  <table class="headerInfo">
    <!-- 報表資訊表格，如報表代碼、列印單位 -->
  </table>
  <hr/>
  <!-- 分隔線，區分報表區塊 -->
  <h2>標題</h2>
  <p>單值數據： <span th:text="${variable}"></span></p>
  <!-- 動態數據展示，使用 Thymeleaf 語法 -->
  <table>
    <!-- 數據表格，顯示多筆數據 -->
  </table>
  <div class="new-pages">
    <!-- 強制換頁區塊，如備註或附加內容 -->
  </div>
</body>
</html>
```

## 2. 頁眉、頁腳 與 頁面布局

透過 CSS 的 `@page` 規則，控制紙張大小、邊距、頁眉、頁腳。

以下為範例與頁碼語法說明：

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

## 3. 分隔線

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

## 4. 強制換頁

```css
.new-pages {
  page-break-before: always; /* 在元素前強制換頁 */
}
```

```html
<div class="new-pages"/>
```

- **說明**：`.new-pages` 強制換頁。

## 5. Thymeleaf 動態數據處理

### 5.1 單值數據

```html
<p>保單號碼　: <span th:text="${policyNo}"></span></p>
```

- **說明**：`th:text="${policyNo}"` 顯示後端傳入的 `policyNo` 值。

### 5.2 表格數據循環

```html
<tr th:each="c : ${coverages}">
  <td th:text="${c.coverageNo}"></td>
  <td th:text="${c.planCode}"></td>
  <td th:text="${c.rateScale}"></td>
  <td th:text="${#numbers.formatDecimal(c.faceAmt, 0, 'COMMA', 2, 'POINT')}"></td>
</tr>
```

- **說明**：
  - `th:each="c : ${coverages}"`：遍歷 `coverages` 列表，`c` 為單筆數據。
  - `th:text="${c.coverageNo}"`：顯示 `c` 物件的屬性。
  - `#numbers.formatDecimal(c.faceAmt, 0, 'COMMA', 2, 'POINT')`：
    - **參數 1 (c.faceAmt)**：要格式化的數字。
    - **參數 2 (0)**：最小整數位數，0 表示不強制補零。
    - **參數 3 ('COMMA')**：千位分隔符，使用逗號（,）；如不需要 設定為 `NONE`。
    - **參數 4 (2)**：小數點後位數，保留 2 位。
    - **參數 5 ('POINT')**：小數點符號，使用點號（.）。
    - **範例輸出**：`1234567.89` 格式化為 `1,234,567.89`。

### 範例：

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

    /* 分隔線樣式 */
    hr {
      border: 0.1px solid #000;
    }

    /* 表格整體樣式 */
    table {
      width: 100%; /* 表格寬度填滿容器 */
      border-collapse: collapse; /* 合併邊框，避免雙線效果 */
      -fs-table-paginate: paginate; /* 啟用表格分頁功能 */
      -fs-page-break-min-height: 1.5cm; /* 確保分頁時有足夠空間 */
    }

    /* 表格儲存格樣式 */
    th, td {
      border: 0.1mm solid; /* 儲存格邊框為 0.1mm 實線 */
      padding: 6px; /* 儲存格內間距 */
      text-align: center; /* 文字居中 */
      font-size: 14px; /* 字體大小 */
      line-height: 1.2; /* 統一行高，確保對齊 */
    }

    /* 表頭儲存格樣式 */
    th {
      background-color: #f0f0f0; /* 表頭背景色為淺灰色 */
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

    /* 強制換頁設定 */
    .new-pages {
      page-break-before: always;
    }

    /* 頁碼顯示樣式 */
    .page-number::after {
      content: "頁碼： " counter(page) " / " counter(pages) ;
      font-family: "標楷體";
      font-size: 14px;
    }
  </style>
</head>
<body>
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

<h2>保單資料</h2>
<p>保單號碼　: <span th:text="${policyNo}"></span></p>
<p>保單狀態　: <span th:text="${poStsCode}"></span></p>
<p>要保人姓名: <span th:text="${o1Name}"></span></p>
<p>被保人姓名: <span th:text="${i1Name}"></span></p>
<p>保單生效日: <span th:text="${poIssueDate}"></span></p>

<hr/>

<h2>保障資料</h2>
<table>
  <thead>
  <tr>
    <th>保障序號</th>
    <th>險種代碼</th>
    <th>險種版數</th>
    <th>保額</th>
  </tr>
  </thead>
  <tbody>
  <tr th:each="c : ${coverages}">
    <td style="width: 8%; text-align: center;" th:text="${c.coverageNo}"></td>
    <td style="width: 8%; text-align: center;" th:text="${c.planCode}"></td>
    <td style="width: 8%; text-align: center;" th:text="${c.rateScale}"></td>
    <td style="width: 10%; text-align: right;" th:text="${#numbers.formatDecimal(c.faceAmt, 0, 'COMMA', 2, 'POINT')}"></td>
  </tr>
  </tbody>
</table>

<div class="new-pages"/>
<p>備註：</p>
<p>此為練習用範例，相關資料均為假資料。</p>
</body>
</html>
```
