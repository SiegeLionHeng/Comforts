package com.outrank.global.net;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.outrank.global.global.AppGlobal;
import com.outrank.global.utils.NetUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 网络策略
 */
public class ApiStrategy {
    // 公共Url
//    public static String baseUrl = "http://172.18.97.22:8080/";
//    public static String baseUrl = "http://172.18.97.225:8080/";
    public static String baseUrl = "http://192.168.31.12:8080";
    // 读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 7676;
    // 连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 7676;
    // 设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;


    public ApiStrategy() {
        // TODO: 2019/12/6 网络策略1. 设置缓存 2. 设置公共请求头
        // 设置缓存
        File cacheFile = new File(AppGlobal.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //创建一个OkHttpClient并设置超时时间
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
//                .addInterceptor(headerInterceptor)
//                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();
        // 设置公共请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put("", "");
        OkGo.getInstance().addCommonHeaders(headers);
        OkGo.getInstance().setOkHttpClient(client);
//        mApiService = ApiService.getInstance();
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetUtils.isNetworkAvailable(AppGlobal.getInstance())) {
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl
                            .FORCE_NETWORK : CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetUtils.isNetworkAvailable(AppGlobal.getInstance())) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" +
                            CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    };

}
