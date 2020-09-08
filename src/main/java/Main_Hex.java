import okio.ByteString;

/**
 * 需求1： String => byte => hex      ok
 * 需求2： hex => byte => String
 */
public class Main_Hex {

    public static void main(String[] args) {
//        method1();
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

    public static void method3() {
        char[] chars = "0123456789ABCDEF".toCharArray();
        String origin = "1aA六";
        byte[] bytes = origin.getBytes();
        String ret = "";

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            int int1 = ((b & 0xF0) >> 4);
            int int2 = (b & 0x0F);
            ret = ret + chars[int1] + chars[int2];
        }

        System.out.println("十六进制 =》" + ret);
    }

}
