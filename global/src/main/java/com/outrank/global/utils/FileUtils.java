package com.outrank.global.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Gabriel on 2019/12/18.
 * Email 17600284843@163.com
 * Description: 文件操作工具类
 */
public class FileUtils {

    public static String readAssertRes(Context context, String strAssertFileName) {
        AssetManager manager = context.getAssets();
        String strResourse = "";
        try {
            InputStream open = manager.open(strAssertFileName);
            strResourse = getStringFromInputStream(open);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResourse;
    }

    /**
     * 将InputStream转成String
     *
     * @param open
     * @return
     */
    private static String getStringFromInputStream(InputStream open) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(open));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
