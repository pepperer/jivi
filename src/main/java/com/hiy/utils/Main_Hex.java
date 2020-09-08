package com.hiy.utils;

import okio.ByteString;
import okio.Okio;

/**
 * 需求1： String => byte => hex      ok
 * 需求2： hex => byte => String ok
 * 总结
 */
public class Main_Hex {

    public static void main(String[] args) {
//        method1();
//        method1_1();
//        System.out.println("====>");
//        method2();
//        System.out.println("====>");
        method3();
    }

    public static void method1() {
        ByteString byteString = ByteString.encodeUtf8("1aA六");
        System.out.println(byteString.size());
        System.out.println("十六进制 => " + byteString.hex());


    }

    public static void method1_1() {
        ByteString byteString = ByteString.decodeHex("316141E585AD");
        System.out.println("结果 => " + byteString.toString());


    }

    public static void method2() {
        String origin = "1aA六";
        char[] originChar = origin.toCharArray();

        String ret = "";
        for (int i = 0; i < originChar.length; i++) {
            int ch = originChar[i];
            String s4 = Integer.toHexString(ch);
            ret += s4;
        }

        System.out.println("十六进制 : " + ret);
    }

    public static int decodeHexDigit(char c) {

        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        throw new IllegalArgumentException("Unexpected hex digit: " + c);
    }


    /**
     * 函数总结
     */
    public static void method3() {
        // 16进制 映射表
        char[] chars = "0123456789ABCDEF".toCharArray();
        String origin = "1aA六";

        byte[] bytes = origin.getBytes();
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            // 8位
            byte b = bytes[i];
            // 取高四位
            int int1 = ((b & 0xF0) >> 4);
            // 取低四位
            int int2 = (b & 0x0F);
            // 取出高四位和低四位对应的字符
            ret = ret + chars[int1] + chars[int2];
        }
        System.out.println("十六进制 =》" + ret);
        System.out.println("字符串 => 十六进制结束");


        // =========== //
        // 转char[]
        char[] char2 = ret.toCharArray();
        int length = ret.length() / 2;
        byte[] bytes1 = new byte[length];
        // 1char = 4位， 2char = 8位 = 1byte
        for (int i = 0; i < length; i++) {
            // 取出对应的char值
            char a = char2[i * 2];
            char b = char2[i * 2 + 1];
            // 获取对应的十六进制的制， 前一个 左移动四位做高四位， 后一个做第四位
            int aInt = decodeHexDigit(a) << 4;
            int bInt = decodeHexDigit(b);
            // 因为 位运算的优先级较低， 所以需要分开写  + 起来 生成byte
            byte cByte = (byte) (aInt + bInt);

            // char  => 数字
            bytes1[i] = cByte;
            System.out.println(" byte  : " + cByte);
            System.out.println("");
            System.out.println("");
        }
        // byte => String
        System.out.println("十六进制 ==> String : " + new String(bytes1));
    }



    public static void  method4() {
    }

}
