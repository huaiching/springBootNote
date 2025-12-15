package com.example.api.util;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

public class PdfUtil {

    /**
     * word 轉 PDF <br/>
     * poi-tl 陣列類型的 word 因為 過於複雜 不可使用
     * @param wordFile word 檔
     * @return 產出的 PDF 檔案資料流（byte[]）
     */
    public static byte[] wordToPDF(byte[] wordFile) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(wordFile));
            // 設定 表格類的最小高度
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    // 設定 最小高度 500
                    int minHeightInTwips = 500;
                    CTTrPr trPr = row.getCtRow().getTrPr();
                    if (trPr == null) {
                        trPr = row.getCtRow().addNewTrPr();
                    }

                    // 檢查是否已經有 高度 設定
                    CTHeight ctHeight = trPr.getTrHeightList().isEmpty()
                            ? null
                            : trPr.getTrHeightList().get(0);
                    // 無高度 或 小於最小高度，調整為 最小高度
                    if (ctHeight == null) {
                        ctHeight = trPr.addNewTrHeight();
                        ctHeight.setVal(BigInteger.valueOf(minHeightInTwips));
                        ctHeight.setHRule(STHeightRule.AT_LEAST);

                    } else {
                        BigInteger currentHeight = (BigInteger) ctHeight.getVal();

                        // 如果當前高度小於最小高度，則更新為最小高度
                        if (currentHeight != null && currentHeight.compareTo(BigInteger.valueOf(minHeightInTwips)) < 0) {
                            ctHeight.setVal(BigInteger.valueOf(minHeightInTwips));
                            if (ctHeight.getHRule() == null || !ctHeight.getHRule().equals(STHeightRule.AT_LEAST)) {
                                ctHeight.setHRule(STHeightRule.AT_LEAST);
                            }
                        }
                    }
                }
            }
            // 建立 PDF
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, outputStream, options);
        } catch (Exception e) {
            throw new RuntimeException("word 轉 PDF 失敗: ", e);
        }
        return outputStream.toByteArray();
    }

}
