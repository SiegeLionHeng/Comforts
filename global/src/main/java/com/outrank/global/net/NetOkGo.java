package com.outrank.global.net;

import android.annotation.SuppressLint;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * Created by Gabriel on 2019/12/5.
 * Email 17600284843@163.com
 * Description: OKGO 单一请求封装
 */
@SuppressLint("CheckResult")
public class NetOkGo {

    public static NetOkGo netOkGo;

    /**
     * 获取网络请求单利
     *
     * @return
     */
    public static NetOkGo getInstance() {
        if (netOkGo == null) {
            synchronized (NetOkGo.class) {
                if (netOkGo == null) {
                    new NetOkGo();
                }
            }
        }
        return netOkGo;
    }


    public static void downloadFile(String url) {

        OkGo.<File>get("")
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {

                    }
                });
    }

    private static void showLoading() {
        // TODO: 2019/12/6 显示弹窗
    }

    private static void hideLoading() {
        // TODO: 2019/12/6 隐藏弹窗
    }
}
