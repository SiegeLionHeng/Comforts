package com.outrank.global.interfaces;

import android.os.Environment;


import com.outrank.global.global.AppGlobal;

import java.io.File;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 存储文件路径
 */
public interface FilePath {
    /**
     * 文件存储根地址
     */
    String ROOT_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + "OF";

    /**
     * 普通文件存储路径
     */
    String CUSTOM_FILE_PATH = ROOT_PATH + File.separator + "files";
    /**
     * 系统私有data目录
     */
    String DATA_PATH = AppGlobal.getInstance().getApplicationContext().getFilesDir()
            .getAbsolutePath(); // data/data目录
    /**
     * 闪屏图片下载路径
     */
    String AD_PATH = DATA_PATH + File.separator + "ads";
    /**
     * 安装包下载路径
     */
    String DOWNLOAD_APK_PATH = DATA_PATH + File.separator + "apks";
}
