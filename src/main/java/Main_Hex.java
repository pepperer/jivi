import okhttp3.*;
import okio.ByteString;

import java.io.File;
import java.io.IOException;

/**
 * 需求1： String => byte => hex
 * 需求2： hex => byte => String
 */
public class Main_Hex {

   public static void main(String[] args) {
       ByteString byteString = ByteString.encodeUtf8("??");
       System.out.println(byteString.size());
       byte b = byteString.getByte(0);
       System.out.println(b);

       System.out.println(byteString.hex());
   }}
