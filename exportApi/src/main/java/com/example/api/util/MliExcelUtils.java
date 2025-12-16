
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.SheetUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.helpers.ColumnHelper;
import org.jxls.common.Context;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetView;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSheetViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MliExcelUtils {
    private static final Logger log = LoggerFactory.getLogger(MliExcelUtils.class);
    private static final String FONT_DF_KAI_SB_PATH = "fonts/DFKai-SB.ttf";
    public static final Pattern EL_EXTRACT = Pattern.compile("\\$\\{([^}]+)}");
    public static final int BUFFER_SIZE = 4096;

    public MliExcelUtils() {
    }

    public static byte[] convertExcelToPDF(byte[] excel) throws IOException, DocumentException {
        ByteArrayInputStream bis = new ByteArrayInputStream(excel);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(bis);
        Document document = new Document();
        PdfWriter.getInstance(document, os);
        document.open();
        PdfPTable pdfPTable = createPDFTableFromExcel(workbook);
        document.add(pdfPTable);
        document.close();
        bis.close();
        os.close();
        return os.toByteArray();
    }

    private static PdfPTable createPDFTableFromExcel(XSSFWorkbook workbook) throws DocumentException, IOException {
        XSSFSheet worksheet = workbook.getSheetAt(0);
        PdfPTable table = new PdfPTable(worksheet.getRow(0).getLastCellNum());

        for(Row row : worksheet) {
            if (row.getRowNum() != 0) {
                for(int i = 0; i < row.getPhysicalNumberOfCells(); ++i) {
                    Cell cell = row.getCell(i);
                    String cellValue;
                    switch (cell.getCellType()) {
                        case STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            cellValue = String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()));
                            break;
                        case BLANK:
                        default:
                            cellValue = "";
                    }

                    PdfPCell cellPdf = new PdfPCell(new Phrase(cellValue, getCellStyle(cell)));
                    setBackgroundColor(cell, cellPdf);
                    setCellAlignment(cell, cellPdf);
                    table.addCell(cellPdf);
                }
            }
        }

        return table;
    }

    private static Font getCellStyle(Cell cell) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("fonts/DFKai-SB.ttf", "Identity-H", true);
        return new Font(baseFont, 12.0F, 0);
    }

    private static void setCellAlignment(Cell cell, PdfPCell cellPdf) {
        CellStyle cellStyle = cell.getCellStyle();
        HorizontalAlignment horizontalAlignment = cellStyle.getAlignment();
        switch (horizontalAlignment) {
            case LEFT:
                cellPdf.setHorizontalAlignment(0);
                break;
            case CENTER:
                cellPdf.setHorizontalAlignment(1);
                break;
            case JUSTIFY:
            case FILL:
                cellPdf.setVerticalAlignment(3);
                break;
            case RIGHT:
                cellPdf.setHorizontalAlignment(2);
        }

    }

    private static void setBackgroundColor(Cell cell, PdfPCell cellPdf) {
        short bgColorIndex = cell.getCellStyle().getFillForegroundColor();
        if (bgColorIndex != IndexedColors.AUTOMATIC.getIndex()) {
            XSSFColor bgColor = (XSSFColor)cell.getCellStyle().getFillForegroundColorColor();
            if (bgColor != null) {
                byte[] rgb = bgColor.getRGB();
                if (rgb != null && rgb.length == 3) {
                    cellPdf.setBackgroundColor(new Color(rgb[0] & 255, rgb[1] & 255, rgb[2] & 255));
                }
            }
        }

    }

    private static void autoSizeColumn(int column, boolean useMergedCells, XSSFSheet sheet) {
        double width = SheetUtil.getColumnWidth(sheet, column, useMergedCells);
        log.info("origin width is:" + width);
        if (width != (double)-1.0F) {
            width *= (double)300.0F;
            int maxColumnWidth = 65280;
            if (width > (double)maxColumnWidth) {
                width = (double)maxColumnWidth;
            }

            log.info("changed width is:" + width);
            ColumnHelper columnHelper = sheet.getColumnHelper();
            sheet.setColumnWidth(column, Math.toIntExact(Math.round(width)));
            columnHelper.setColBestFit((long)column, true);
        }

    }

    public static ByteArrayOutputStream getByteArrayOutputStream(InputStream templateIs, Context context, List<Context> centerContexts, ByteArrayOutputStream baos, Class<?> clazz) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(templateIs);
        XSSFSheet sheet = wb.getSheetAt(0);
        Header header = sheet.getHeader();
        String right = header.getRight();
        Matcher matcher = EL_EXTRACT.matcher(right);

        while(matcher.find()) {
            Object runVar = context.getRunVar(matcher.group(1));
            if (null != runVar) {
                right = right.replace(matcher.group(0), runVar.toString());
            }
        }

        InputStream headerIs = new ByteArrayInputStream(baos.toByteArray());
        ByteArrayOutputStream headerBaos = new ByteArrayOutputStream();

        try {
            XSSFWorkbook headerWb = new XSSFWorkbook(headerIs);
            int sheetsNumber = headerWb.getNumberOfSheets();

            for(int i = 0; i < sheetsNumber; ++i) {
                XSSFSheet sheetAt = headerWb.getSheetAt(i);
                Header header1 = sheetAt.getHeader();
                String center = header.getCenter();
                if (CollectionUtils.isNotEmpty(centerContexts) && centerContexts.size() >= i + 1) {
                    for(Matcher centerMatcher = EL_EXTRACT.matcher(center); centerMatcher.find(); center = center.replace(centerMatcher.group(0), String.valueOf(((Context)centerContexts.get(i)).getRunVar(centerMatcher.group(1))))) {
                    }
                }

                header1.setCenter(center);
                header1.setRight(right);
                if (Objects.nonNull(clazz)) {
                    Field[] fields = clazz.getFields();

                    for(int columnIndex = 0; columnIndex < fields.length; ++columnIndex) {
                        autoSizeColumn(i, false, sheet);
                    }
                }

                sheetAt.setMargin((short)2, sheet.getMargin((short)2));
                sheetAt.setMargin((short)3, sheet.getMargin((short)3));
                sheetAt.setMargin((short)0, sheet.getMargin((short)0));
                sheetAt.setMargin((short)1, sheet.getMargin((short)1));
                CTSheetView view = sheetAt.getCTWorksheet().getSheetViews().getSheetViewArray(0);
                view.setView(STSheetViewType.PAGE_LAYOUT);
            }

            headerWb.write(headerBaos);
        } finally {
            headerIs.close();
            headerBaos.close();
        }

        return headerBaos;
    }

    public static void setBorder(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
    }

    public static <T> byte[] convertExcelToByte(Workbook workbook) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        workbook.write(out);
        return out.toByteArray();
    }
}
