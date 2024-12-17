package edu.whut.cs.se.udpagent.util;

/**
 * 字节相关工具
 * @author huangaokai
 * @date 2024/5/30
 **/
public class ByteUtils {
    /**
     * 截选byteArray从start到end的byte转为long类型的10进制数
     * @param byteArray
     * @param start 开始下标
     * @param end 结束下标（左闭右开）
     * @return long
     */
    public static long byteArrayToDecimal(byte[] byteArray, int start, int end) {
        if (byteArray == null || start < 0 || end > byteArray.length || start >= end) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }
        long decimalValue = 0;
        for (int i = start; i < end; i++) {
            decimalValue = (decimalValue << 8) | (byteArray[i] & 0xFF);
        }
        return decimalValue;
    }

    /**
     * byte数组转16进制string
     * @param byteArray
     * @return string
     */
    public static String byteArrayToHex(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
