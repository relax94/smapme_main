package com.studio.a4kings.qr_code_app.Utils;

/**
 * Created by hack on 24.01.16.
 */
public class DataEncryption {
    private static   String secretKey = "secret_key";
    private static String encryptKey = "пятницаянварь";

    public static String encryptString()
    {
        StringBuffer sb = new StringBuffer (secretKey);
        int lenStr = secretKey.length();
        int lenKey = encryptKey.length();
        for ( int i = 0, j = 0; i < lenStr; i++, j++ )
        {
            if ( j >= lenKey ) j = 0;
            sb.setCharAt(i, (char)(secretKey.charAt(i) ^ encryptKey.charAt(j)));
        }
        return sb.toString();
    }
}
