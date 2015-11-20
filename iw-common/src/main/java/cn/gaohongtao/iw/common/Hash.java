package cn.gaohongtao.iw.common;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hash工具类
 *
 * Created by gaoht on 15/6/25.
 */
public class Hash {
    
    private static final Logger log = LoggerFactory.getLogger(Hash.class);
    
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private static SecureRandom rnd = new SecureRandom();
    
    public static String sha1(String input) {
        MessageDigest mDigest;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte aResult : result) {
            sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
        }
    
        return sb.toString();
    }
    
    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
    
    public static String signature(Object o, String key) {
        Map<String, Object> paramMap = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(o);
            } catch (IllegalAccessException e) {
                continue;
            }
            if (value == null) {
                continue;
            }
            paramMap.put(field.getName(), value);
        }
        return signatureFromMap(paramMap, key);
    }
    
    public static String signatureFromMap(Map<String, Object> o, String key) {
        Map<String, Object> paramMap = new TreeMap<>(o);
        
        StringBuilder stringSignTemp = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (stringSignTemp.length() > 0) {
                stringSignTemp.append("&");
            }
            stringSignTemp.append(entry.getKey()).append("=").append(entry.getValue().toString());
        }
        
        stringSignTemp.append("&key=").append(key);
        log.info(stringSignTemp.toString());
        return string2MD5(stringSignTemp.toString()).toUpperCase();
    }
    
    /*** 
     * MD5加码 生成32位md5码 
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
        
    }
    
}
