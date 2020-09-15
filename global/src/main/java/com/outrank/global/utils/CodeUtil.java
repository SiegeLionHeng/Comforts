package com.outrank.global.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: 加密工具类
 */
public class CodeUtil {
    /**
     * 加密的密钥
     */
    public static String KEY = "9xRpT5lQ";

    /**
     * 加密方法
     *
     * @param encryptString 加密的文字
     * @return String 加密后的文字
     */
    @SuppressLint("GetInstance")
    public static String Encode(String encryptString) {

        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), "DES");
        Cipher cipher = null;
        byte[] encryptedData = null;
        try {
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            if (null != encryptString) {
                encryptedData = cipher.doFinal(encryptString.getBytes());
            } else {
                return "";
            }
        } catch (Exception e) {
            new RuntimeException(String.format(" CodeUtil Encode%s", e.toString()));
            e.printStackTrace();
        }
        return Base64.encodeToString(encryptedData, Base64.NO_WRAP);
        //return Base64.encode(encryptedData);
    }


    /**
     * 解密方法
     *
     * @param decryptString 需要解密的文字
     * @return String 解密后的文字
     * @throws Exception
     */
    public static String Decode(String decryptString) {

        byte[] byteMi = null;
        if (null != decryptString) {
            //byteMi = Base64.decode(decryptString);
            try {
                byteMi = Base64.decode(decryptString, Base64.NO_WRAP);
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
        //设置加密密钥
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), "DES");
        byte[] decryptedData = null;
        try {
            @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decryptedData = cipher.doFinal(byteMi);
        } catch (Exception e) {
            //new RuntimeException(" CodeUtil Decode"+e.toString());
            //e.printStackTrace();
            return "";
        }
        //对返回的字符串进行截取
        return new String(decryptedData);
    }

    /**
     * 2018-09
     * 版本更新，加密算法再添加URLDncode
     */
    public static String urlDecode(String response) {
        String urlDecodeStr = null;
        try {
            urlDecodeStr = URLDecoder.decode(response, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Decode(urlDecodeStr);
    }

    /**
     * 2018-09-7
     * 版本更新，加密算法再添加URLEncode
     */
    public static String urlEncode(String response) {
        String urlEndodeStr = null;
        try {
            urlEndodeStr = URLEncoder.encode(response, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Encode(urlEndodeStr);
    }

    /***
     * encode by base64
     */
    public static String encodeBase64(String input) throws Exception {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    /***
     * decode by base64
     */
    public static String decodeBase64(String encodedString) throws Exception {
        return new String(Base64.decode(encodedString, Base64.DEFAULT));
    }

}
