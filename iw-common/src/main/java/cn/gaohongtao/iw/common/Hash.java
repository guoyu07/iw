package cn.gaohongtao.iw.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by gaoht on 15/6/25.
 */
public class Hash {

    public static String sha1(String input) {
        MessageDigest mDigest;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
