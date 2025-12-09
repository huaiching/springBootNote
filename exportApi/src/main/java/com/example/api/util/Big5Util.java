package com.example.api.util;

public class Big5Util {
    /**
     * 計算字串長度 (全形 佔 2) <br/>
     * - 英文、數字、符號 = 1 <br/>
     * - 中文、全形標點、全形空格 = 2 <br/>
     * - Emoji 或特殊符號 = 2
     *
     * @param text 輸入字串
     * @return 計算後的長度
     */
    public static int length(String text) {
        if (text == null || text.isEmpty()) return 0;

        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (isEastAsianWide(c)) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

    /**
     * 截取子字串 (全形 佔 2)
     *
     * @param text       原始字串
     * @param startPos   起始位置（從 0 開始，中文佔 2）
     * @param endPos     結束位置（不包含，中文佔 2）
     * @return 截取後的字串
     * 示例：substring("測試ABC中文", 2, 6) → "ABC中"
     */
    public static String substring(String text, int startPos, int endPos) {
        if (text == null || text.isEmpty() || startPos >= endPos) return "";
        if (startPos < 0) startPos = 0;

        StringBuilder sb = new StringBuilder();
        int currentPos = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int charWidth = isEastAsianWide(c) ? 2 : 1;

            // 已經超過結束位置
            if (currentPos >= endPos) break;

            // 還沒到起始位置
            if (currentPos + charWidth <= startPos) {
                currentPos += charWidth;
                continue;
            }

            // 在範圍內，加入字元
            if (currentPos + charWidth > startPos) {
                sb.append(c);
                currentPos += charWidth;
            }
        }

        return sb.toString();
    }


    /**
     * 是否為全形或中文
     */
    private static boolean isEastAsianWide(char c) {
        // 常見中文範圍
        if (c >= 0x4E00 && c <= 0x9FFF) return true;  // CJK 統一表意文字
        if (c >= 0x3400 && c <= 0x4DBF) return true;  // CJK 擴充 A
        if (c >= 0xF900 && c <= 0xFAFF) return true;  // 相容字
        if (c >= 0xFF01 && c <= 0xFF5E) return true;  // 全形英文與符號
        if (c == 0x3000) return true;                 // 全形空格
        if (c >= 0x2000 && c <= 0x206F) return true;  // 一般標點區
        // Emoji 與其他也保守算 2
        if (Character.isIdeographic(c) || c >= 0x1F000) return true;

        return false;
    }
}