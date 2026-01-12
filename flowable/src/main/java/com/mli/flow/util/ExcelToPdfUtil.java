package com.mli.flow.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 轉 PDF 工具類 <br/>
 * 支援 .xlsx 格式，包含儲存格底色、動態行高、自動換行、文字垂直置中 <br/>
 * 顏色 灰階顯示
 */
public class ExcelToPdfUtil {

    private static final Logger log = LoggerFactory.getLogger(ExcelToPdfUtil.class);

    // PDF 頁面設定
    private static final float MARGIN = 30;                    // 頁面邊距 (pt)
    private static final float DEFAULT_FONT_SIZE = 10f;        // 預設字體大小 (pt)
    private static final float COLUMN_WIDTH_FACTOR = 0.12f;    // Excel 欄寬轉換係數 (pt)
    private static final float TEXT_PADDING_LEFT_RIGHT = 5f;   // 文字左右內距 (pt)
    private static final float TEXT_PADDING_TOP_BOTTOM = 10f;  // 文字上下內距 (pt)
    private static final float MIN_ROW_HEIGHT = 20f;           // 最小行高 (pt)
    private static final float LINE_SPACING = 2f;              // 行距 (pt)
    private static final float PAGE_NUMBER_Y = 20f;            // 頁碼 Y 座標（從頁面底部起算）
    private static final float PAGE_NUMBER_FONT_SIZE = 10f;     // 頁碼字體大小

    private ExcelToPdfUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 將 Excel byte[] 轉換為 PDF byte[] (直式)
     * @param excelBytes Excel 檔案的二進位資料
     * @return PDF 二進位資料
     * @throws IOException 轉換過程發生 IO 錯誤
     */
    public static byte[] excelToPdf(byte[] excelBytes) throws IOException {
        return convertExcelToPdf(excelBytes, false, false);
    }

    /**
     * 將 Excel byte[] 轉換為 PDF byte[] (橫式)
     * @param excelBytes Excel 檔案的二進位資料
     * @return PDF 二進位資料
     * @throws IOException 轉換過程發生 IO 錯誤
     */
    public static byte[] excelToPdfHorizontal(byte[] excelBytes) throws IOException {
        return convertExcelToPdf(excelBytes, true, false);
    }

    /**
     * 將 Excel byte[] 轉換為 PDF byte[] (直式)
     * @param excelBytes Excel 檔案的二進位資料
     * @param showPageNumber 是否顯示頁碼 (T/F)
     * @return PDF 二進位資料
     * @throws IOException 轉換過程發生 IO 錯誤
     */
    public static byte[] excelToPdf(byte[] excelBytes, boolean showPageNumber) throws IOException {
        return convertExcelToPdf(excelBytes, false, showPageNumber);
    }

    /**
     * 將 Excel byte[] 轉換為 PDF byte[] (橫式)
     * @param excelBytes Excel 檔案的二進位資料
     * @param showPageNumber 是否顯示頁碼 (T/F)
     * @return PDF 二進位資料
     * @throws IOException 轉換過程發生 IO 錯誤
     */
    public static byte[] excelToPdfHorizontal(byte[] excelBytes, boolean showPageNumber) throws IOException {
        return convertExcelToPdf(excelBytes, true, showPageNumber);
    }

    /**
     * 核心轉換方法：將 Excel 轉為 PDF
     * @param excelBytes Excel 二進位資料
     * @param isHorizontal 是否為橫式頁面 (T/F)
     * @param showPageNumber 是否顯示頁碼 (T/F)
     * @return PDF 二進位資料
     * @throws IOException 轉換過程發生 IO 錯誤
     */
    private static byte[] convertExcelToPdf(byte[] excelBytes, boolean isHorizontal, boolean showPageNumber) throws IOException {
        log.info("開始轉換 Excel 到 PDF，Excel 大小: {} bytes, 方向: {}, 顯示頁碼: {}",
                excelBytes.length, isHorizontal ? "橫式" : "直式", showPageNumber);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(excelBytes);
             Workbook workbook = new XSSFWorkbook(bis);
             PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            FontHolder fontHolder = loadFonts(document);

            List<PDPage> allPages = new ArrayList<>();

            int numberOfSheets = workbook.getNumberOfSheets();
            log.info("Excel 包含 {} 個工作表", numberOfSheets);

            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                log.info("處理工作表 [{}]: {}", i, sheet.getSheetName());
                List<PDPage> sheetPages = convertSheetToPdf(document, sheet, workbook, fontHolder, isHorizontal);
                allPages.addAll(sheetPages);
            }

            // 如果需要顯示頁碼，在所有頁面繪製完成後再畫頁碼
            if (showPageNumber) {
                drawPageNumbers(document, allPages, fontHolder.font);
            }

            document.save(baos);
            byte[] pdfBytes = baos.toByteArray();
            log.info("轉換完成，PDF 大小: {} bytes", pdfBytes.length);
            return pdfBytes;

        } catch (Exception e) {
            log.error("Excel 轉 PDF 失敗", e);
            throw new IOException("Excel 轉 PDF 失敗: " + e.getMessage(), e);
        }
    }

    /**
     * 載入中文字體（標楷體）
     * 若載入失敗則使用預設 Helvetica 字體
     * @param document PDF 文件物件
     * @return 字體持有者
     * @throws IOException 字體載入失敗
     */
    private static FontHolder loadFonts(PDDocument document) throws IOException {
        PDFont font;
        try {
            ClassPathResource fontResource = new ClassPathResource("templates/fonts/kaiu.ttf");
            try (InputStream fontStream = fontResource.getInputStream()) {
                font = PDType0Font.load(document, fontStream);
                log.info("成功載入中文字體");
            }
        } catch (Exception e) {
            log.warn("載入中文字體失敗，使用預設字體", e);
            font = PDType1Font.HELVETICA;
        }
        return new FontHolder(font);
    }

    /**
     * 將單個工作表轉換為 PDF 頁面（支援跨頁）
     * @param document     PDF 文件物件
     * @param sheet        Excel 工作表
     * @param workbook     Excel 工作簿
     * @param fontHolder   字體持有者
     * @param isHorizontal 是否為橫式
     * @throws IOException 繪製過程發生 IO 錯誤
     */
    private static List<PDPage> convertSheetToPdf(PDDocument document, Sheet sheet, Workbook workbook,
                                                  FontHolder fontHolder, boolean isHorizontal) throws IOException {
        List<PDPage> createdPages = new ArrayList<>();

        if (sheet.getPhysicalNumberOfRows() == 0) {
            log.warn("工作表 [{}] 為空，跳過", sheet.getSheetName());
            return createdPages;
        }

        PDRectangle pageSize = isHorizontal
                ? new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth())
                : PDRectangle.A4;

        float[] columnWidths = calculateColumnWidths(sheet, pageSize.getWidth());
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        boolean displayGridLines = sheet.isDisplayGridlines();

        float yPosition = pageSize.getHeight() - MARGIN;

        PDPage page = new PDPage(pageSize);
        document.addPage(page);
        createdPages.add(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        try {
            int lastRowNum = sheet.getLastRowNum();
            for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                float rowHeight = calculateRowHeight(row, columnWidths, fontHolder, mergedRegions, workbook);

                // 預留頁碼空間，避免最後一行被蓋住
                if (yPosition - rowHeight < MARGIN + PAGE_NUMBER_Y + 20) {
                    contentStream.close();
                    page = new PDPage(pageSize);
                    document.addPage(page);
                    createdPages.add(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = pageSize.getHeight() - MARGIN;
                }

                drawRow(contentStream, row, yPosition, rowHeight, columnWidths, rowIndex,
                        fontHolder, mergedRegions, displayGridLines, workbook);

                yPosition -= rowHeight;
            }
        } finally {
            if (contentStream != null) {
                contentStream.close();
            }
        }

        return createdPages;
    }

    /**
     * 繪製單行資料（包含底色、邊框、文字）
     * @param contentStream 內容串流
     * @param row           當前行
     * @param yPosition     當前行 Y 座標（頁面頂部起算）
     * @param rowHeight     當前行高度
     * @param columnWidths  各欄寬度陣列
     * @param rowIndex      行索引
     * @param fontHolder    字體持有者
     * @param mergedRegions 合併儲存格區域
     * @param displayGridLines 是否顯示格線
     * @param workbook      Excel 工作簿
     * @throws IOException 繪製過程發生 IO 錯誤
     */
    private static void drawRow(PDPageContentStream contentStream, Row row, float yPosition,
                                float rowHeight, float[] columnWidths, int rowIndex,
                                FontHolder fontHolder, List<CellRangeAddress> mergedRegions,
                                boolean displayGridLines, Workbook workbook) throws IOException {
        float xPosition = MARGIN;
        int lastCellNum = row.getLastCellNum();

        PDFont font = fontHolder.font;

        for (int cellIndex = 0; cellIndex < lastCellNum; cellIndex++) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell == null) {
                xPosition += columnWidths[cellIndex];
                continue;
            }

            CellStyle style = cell.getCellStyle();
            float fontSize = DEFAULT_FONT_SIZE;

            // 取得字體大小
            if (style != null && style.getFontIndexAsInt() >= 0) {
                org.apache.poi.ss.usermodel.Font excelFont = workbook.getFontAt(style.getFontIndexAsInt());
                short fontHeight = excelFont.getFontHeightInPoints();
                if (fontHeight > 0) {
                    fontSize = fontHeight;
                }
            }

            float cellWidth;
            CellRangeAddress merged = getMergedRegion(mergedRegions, rowIndex, cellIndex);
            if (merged != null && merged.getFirstRow() == rowIndex && merged.getFirstColumn() == cellIndex) {
                cellWidth = 0;
                for (int i = merged.getFirstColumn(); i <= merged.getLastColumn(); i++) {
                    cellWidth += columnWidths[i];
                }
            } else {
                cellWidth = columnWidths[cellIndex];
            }

            // 1. 繪製底色（背景）
            drawCellBackground(contentStream, cell, xPosition, yPosition, cellWidth, rowHeight);

            // 2. 繪製邊框（只在合併儲存格起始位置或非合併儲存格）
            if (merged == null || (merged.getFirstRow() == rowIndex && merged.getFirstColumn() == cellIndex)) {
                drawCellBorders(contentStream, cell, xPosition, yPosition, cellWidth, rowHeight, displayGridLines);
            }

            // 3. 繪製文字（確保在最上層）
            String cellValue = getCellValueAsString(cell);
            if (!cellValue.isEmpty()) {
                HorizontalAlignment alignment = getHorizontalAlignment(cell);
                drawTextWithWrap(contentStream, font, fontSize, cellValue,
                        xPosition, yPosition, rowHeight, cellWidth, alignment);
            }

            xPosition += columnWidths[cellIndex];
        }
    }

    /**
     * 繪製儲存格底色（背景）
     * @param contentStream 內容串流
     * @param cell          儲存格
     * @param x             X 座標
     * @param y             Y 座標
     * @param width         寬度
     * @param height        高度
     * @throws IOException 繪製過程發生 IO 錯誤
     */
    private static void drawCellBackground(PDPageContentStream contentStream, Cell cell,
                                           float x, float y, float width, float height) throws IOException {
        if (cell == null) return;

        CellStyle style = cell.getCellStyle();
        if (style == null) return;

        short fgIndex = style.getFillForegroundColor();
        short bgIndex = style.getFillBackgroundColor();

        // 判斷有無設定底色
        short finalIndex = bgIndex;
        if (finalIndex == IndexedColors.AUTOMATIC.getIndex() || finalIndex < 0) {
            finalIndex = fgIndex;
        }

        if (finalIndex == IndexedColors.AUTOMATIC.getIndex() || finalIndex < 0) {
            return; // 無底色
        }

        // 取得顏色（目前固定淺灰 #D9D9D9）
        Color bgColor = new Color(217, 217, 217);

        contentStream.setNonStrokingColor(bgColor);
        contentStream.fillRect(x, y - height, width, height);
    }

    /**
     * 計算行高（根據文字內容自動調整）
     * @param row           當前行
     * @param columnWidths  各欄寬度
     * @param fontHolder    字體持有者
     * @param mergedRegions 合併儲存格區域
     * @param workbook      Excel 工作簿
     * @return 計算出的行高（pt）
     * @throws IOException 字體計算過程發生 IO 錯誤
     */
    private static float calculateRowHeight(Row row, float[] columnWidths, FontHolder fontHolder,
                                            List<CellRangeAddress> mergedRegions, Workbook workbook) throws IOException {
        float maxHeight = MIN_ROW_HEIGHT;

        for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell == null) continue;

            CellStyle style = cell.getCellStyle();
            float fontSize = DEFAULT_FONT_SIZE;

            if (style != null && style.getFontIndexAsInt() >= 0) {
                org.apache.poi.ss.usermodel.Font excelFont = workbook.getFontAt(style.getFontIndexAsInt());
                short fontHeight = excelFont.getFontHeightInPoints();
                if (fontHeight > 0) {
                    fontSize = fontHeight;
                }
            }

            String text = getCellValueAsString(cell);
            if (text.isEmpty()) continue;

            float cellWidth = getCellWidth(columnWidths, mergedRegions, row.getRowNum(), cellIndex);
            float availableWidth = cellWidth - 2 * TEXT_PADDING_LEFT_RIGHT;

            PDFont font = fontHolder.font;
            int lineCount = getTextLineCount(font, fontSize, text, availableWidth);

            float lineHeight = fontSize + LINE_SPACING;
            float cellHeight = lineCount * lineHeight + 2 * TEXT_PADDING_TOP_BOTTOM;

            maxHeight = Math.max(maxHeight, cellHeight);
        }

        return maxHeight;
    }

    /**
     * 繪製文字（自動換行 + 垂直置中）
     * @param contentStream 內容串流
     * @param font          PDF 字體
     * @param fontSize      字體大小
     * @param text          文字內容
     * @param x             X 座標
     * @param y             Y 座標（行頂部）
     * @param rowHeight     行高
     * @param cellWidth     儲存格寬度
     * @param alignment     水平對齊方式
     * @throws IOException 繪製過程發生 IO 錯誤
     */
    private static void drawTextWithWrap(PDPageContentStream contentStream,
                                         PDFont font,
                                         float fontSize,
                                         String text,
                                         float x,
                                         float y,
                                         float rowHeight,
                                         float cellWidth,
                                         HorizontalAlignment alignment) throws IOException {
        if (text == null || text.isEmpty()) return;

        float availableWidth = cellWidth - 2 * TEXT_PADDING_LEFT_RIGHT;
        List<String> lines = splitTextIntoLines(font, fontSize, text, availableWidth);

        float lineHeight = fontSize + LINE_SPACING;
        float textTotalHeight = lines.size() * lineHeight;

        float startY = y - (rowHeight - textTotalHeight) / 2 - TEXT_PADDING_TOP_BOTTOM;

        // 明確設定文字顏色為黑色（防止繼承底色）
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(font, fontSize);

        for (String line : lines) {
            float lineWidth = font.getStringWidth(line) / 1000f * fontSize;
            float textX;
            switch (alignment) {
                case CENTER:
                    textX = x + TEXT_PADDING_LEFT_RIGHT + (availableWidth - lineWidth) / 2;
                    break;
                case RIGHT:
                    textX = x + TEXT_PADDING_LEFT_RIGHT + availableWidth - lineWidth;
                    break;
                default:
                    textX = x + TEXT_PADDING_LEFT_RIGHT;
                    break;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(textX, startY);
            contentStream.showText(line);
            contentStream.endText();

            startY -= lineHeight;
        }
    }

    /**
     * 將文字切割成多行（自動換行）
     * @param font      PDF 字體
     * @param fontSize  字體大小
     * @param text      原始文字
     * @param maxWidth  可用寬度
     * @return 切割後的行列表
     * @throws IOException 字體計算過程發生 IO 錯誤
     */
    private static List<String> splitTextIntoLines(PDFont font, float fontSize, String text, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        float currentWidth = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float charWidth = font.getStringWidth(String.valueOf(c)) / 1000f * fontSize;

            if (currentWidth + charWidth > maxWidth && currentLine.length() > 0) {
                lines.add(currentLine.toString());
                currentLine.setLength(0);
                currentWidth = 0;
            }

            currentLine.append(c);
            currentWidth += charWidth;
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    /**
     * 計算文字需要多少行（用於行高計算）
     * @param font      PDF 字體
     * @param fontSize  字體大小
     * @param text      文字內容
     * @param maxWidth  可用寬度
     * @return 所需行數
     * @throws IOException 字體計算過程發生 IO 錯誤
     */
    private static int getTextLineCount(PDFont font, float fontSize, String text, float maxWidth) throws IOException {
        if (text.isEmpty()) return 0;

        int lineCount = 1;
        StringBuilder currentLine = new StringBuilder();
        float currentWidth = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float charWidth = font.getStringWidth(String.valueOf(c)) / 1000f * fontSize;

            if (currentWidth + charWidth > maxWidth && currentLine.length() > 0) {
                lineCount++;
                currentLine.setLength(0);
                currentWidth = 0;
            }

            currentLine.append(c);
            currentWidth += charWidth;
        }

        return lineCount;
    }

    /**
     * 取得儲存格實際寬度（考慮合併儲存格）
     * @param columnWidths  欄寬陣列
     * @param mergedRegions 合併儲存格區域
     * @param rowIndex      行索引
     * @param cellIndex     欄索引
     * @return 儲存格寬度
     */
    private static float getCellWidth(float[] columnWidths, List<CellRangeAddress> mergedRegions,
                                      int rowIndex, int cellIndex) {
        CellRangeAddress merged = getMergedRegion(mergedRegions, rowIndex, cellIndex);
        if (merged != null && merged.getFirstRow() == rowIndex && merged.getFirstColumn() == cellIndex) {
            float width = 0;
            for (int i = merged.getFirstColumn(); i <= merged.getLastColumn(); i++) {
                width += columnWidths[i];
            }
            return width;
        }
        return columnWidths[cellIndex];
    }

    /**
     * 查找儲存格是否屬於合併區域
     * @param mergedRegions 合併儲存格列表
     * @param rowIndex      行索引
     * @param cellIndex     欄索引
     * @return 合併區域物件，若無則返回 null
     */
    private static CellRangeAddress getMergedRegion(List<CellRangeAddress> mergedRegions,
                                                    int rowIndex, int cellIndex) {
        for (CellRangeAddress region : mergedRegions) {
            if (region.isInRange(rowIndex, cellIndex)) {
                return region;
            }
        }
        return null;
    }

    /**
     * 取得儲存格水平對齊方式
     * @param cell 儲存格
     * @return 水平對齊列舉
     */
    private static HorizontalAlignment getHorizontalAlignment(Cell cell) {
        if (cell == null) return HorizontalAlignment.LEFT;

        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            HorizontalAlignment alignment = cellStyle.getAlignment();
            if (alignment == HorizontalAlignment.GENERAL) {
                CellType cellType = cell.getCellType();
                if (cellType == CellType.NUMERIC || cellType == CellType.BOOLEAN) {
                    return HorizontalAlignment.RIGHT;
                }
                return HorizontalAlignment.LEFT;
            }
            return alignment;
        }
        return HorizontalAlignment.LEFT;
    }

    /**
     * 將儲存格值轉為字串
     * @param cell 儲存格
     * @return 字串值
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()));
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()));
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            case BLANK:
            default:
                return "";
        }
    }

    /**
     * 繪製儲存格邊框
     * @param contentStream    內容串流
     * @param cell             儲存格
     * @param x                X 座標
     * @param y                Y 座標
     * @param width            寬度
     * @param height           高度
     * @param displayGridLines 是否顯示格線
     * @throws IOException 繪製過程發生 IO 錯誤
     */
    private static void drawCellBorders(PDPageContentStream contentStream, Cell cell,
                                        float x, float y, float width, float height,
                                        boolean displayGridLines) throws IOException {
        if (cell == null) return;

        CellStyle style = cell.getCellStyle();
        if (style == null) return;

        BorderStyle topStyle = style.getBorderTop();
        BorderStyle bottomStyle = style.getBorderBottom();
        BorderStyle leftStyle = style.getBorderLeft();
        BorderStyle rightStyle = style.getBorderRight();

        boolean hasCustomBorder = (topStyle != BorderStyle.NONE || bottomStyle != BorderStyle.NONE ||
                leftStyle != BorderStyle.NONE || rightStyle != BorderStyle.NONE);

        if (!hasCustomBorder) return;

        if (topStyle != BorderStyle.NONE) {
            drawSingleBorderLine(contentStream, x, y, x + width, y, topStyle, style.getTopBorderColor());
        }
        if (bottomStyle != BorderStyle.NONE) {
            drawSingleBorderLine(contentStream, x, y - height, x + width, y - height, bottomStyle, style.getBottomBorderColor());
        }
        if (leftStyle != BorderStyle.NONE) {
            drawSingleBorderLine(contentStream, x, y, x, y - height, leftStyle, style.getLeftBorderColor());
        }
        if (rightStyle != BorderStyle.NONE) {
            drawSingleBorderLine(contentStream, x + width, y, x + width, y - height, rightStyle, style.getRightBorderColor());
        }
    }

    /**
     * 繪製單條邊框線
     * @param contentStream 內容串流
     * @param x1            起點 X
     * @param y1            起點 Y
     * @param x2            終點 X
     * @param y2            終點 Y
     * @param borderStyle   邊框樣式
     * @param colorIndex    顏色索引
     * @throws IOException 繪製過程發生 IO 錯誤
     */
    private static void drawSingleBorderLine(PDPageContentStream contentStream,
                                             float x1, float y1, float x2, float y2,
                                             BorderStyle borderStyle, short colorIndex) throws IOException {
        float lineWidth = getBorderWidth(borderStyle);
        contentStream.setLineWidth(lineWidth);
        contentStream.setStrokingColor(Color.BLACK);

        if (borderStyle == BorderStyle.DASHED) {
            contentStream.setLineDashPattern(new float[]{3, 1}, 0);
        } else if (borderStyle == BorderStyle.DOTTED) {
            contentStream.setLineDashPattern(new float[]{1, 1}, 0);
        } else {
            contentStream.setLineDashPattern(new float[]{}, 0);
        }

        contentStream.moveTo(x1, y1);
        contentStream.lineTo(x2, y2);
        contentStream.stroke();

        contentStream.setLineDashPattern(new float[]{}, 0);
    }

    /**
     * 取得邊框線寬
     * @param style 邊框樣式
     * @return 線寬 (pt)
     */
    private static float getBorderWidth(BorderStyle style) {
        switch (style) {
            case THICK:
                return 1.5f;
            case MEDIUM:
            case MEDIUM_DASHED:
                return 1.0f;
            default:
                return 0.5f;
        }
    }

    /**
     * 計算各欄寬度（考慮頁面寬度縮放）
     * @param sheet     Excel 工作表
     * @param pageWidth 頁面可用寬度
     * @return 各欄寬度陣列
     */
    private static float[] calculateColumnWidths(Sheet sheet, float pageWidth) {
        int maxColumns = 0;
        for (Row row : sheet) {
            if (row.getLastCellNum() > maxColumns) {
                maxColumns = row.getLastCellNum();
            }
        }

        float[] columnWidths = new float[maxColumns];
        float totalWidth = 0;

        for (int i = 0; i < maxColumns; i++) {
            int excelColumnWidth = sheet.getColumnWidth(i);
            columnWidths[i] = excelColumnWidth * COLUMN_WIDTH_FACTOR;
            totalWidth += columnWidths[i];
        }

        float availableWidth = pageWidth - 2 * MARGIN;
        if (totalWidth > availableWidth) {
            float scaleFactor = availableWidth / totalWidth;
            for (int i = 0; i < maxColumns; i++) {
                columnWidths[i] *= scaleFactor;
            }
            log.info("欄位總寬度超過頁面，已縮放至 {}%", (int)(scaleFactor * 100));
        }

        return columnWidths;
    }

    /**
     * 頁碼繪製方法
     * @param document
     * @param pages
     * @throws IOException
     */
    private static void drawPageNumbers(PDDocument document, List<PDPage> pages, PDFont chineseFont) throws IOException {
        int totalPages = pages.size();

        for (int i = 0; i < pages.size(); i++) {
            PDPage page = pages.get(i);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                    PDPageContentStream.AppendMode.APPEND, true, true)) {

                String pageText = "第 " + (i + 1) + " 頁 / 共 " + totalPages + " 頁";

                float textWidth = chineseFont.getStringWidth(pageText) / 1000f * PAGE_NUMBER_FONT_SIZE;
                float x = (page.getMediaBox().getWidth() - textWidth) / 2; // 置中
                float y = PAGE_NUMBER_Y;

                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(chineseFont, PAGE_NUMBER_FONT_SIZE);
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(pageText);
                contentStream.endText();
            }
        }
    }

    /**
     * 字體持有者（避免重複載入）
     */
    private static class FontHolder {
        final PDFont font;

        FontHolder(PDFont font) {
            this.font = font;
        }
    }
}