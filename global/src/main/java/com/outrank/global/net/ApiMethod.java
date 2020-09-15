package com.outrank.global.net;

import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.outrank.global.net.response.PublishResponse;
import com.outrank.global.utils.RetryWithDelayUtils;
import com.outrank.global.utils.RxUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 请求方法
 */
public class ApiMethod<T> {

    private PublishResponse<T> mPublishResponse;

    private volatile static ApiMethod mApiMethod;

    public static ApiMethod getInstance() {
        if (mApiMethod == null) {
            synchronized (ApiMethod.class) {
                if (mApiMethod == null) {
                    mApiMethod = new ApiMethod();
                }
            }
        }
        return mApiMethod;
    }

    /**
     * 封装线程管理和订阅的过程
     */
    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 请求数据
     *
     * @param <T>
     * @param method   请求方式
     * @param url      请求地址
     * @param params   请求参数
     * @param observer
     */
    public static <T> void getData(HttpMethod method, String url, HttpParams params, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.request(method, url, obtClass(), params), observer);
    }


    /**
     * 请求数据(无请求参数)
     *
     * @param <T>
     * @param method   请求方式
     * @param url      请求地址
     * @param observer
     */
    public static <T> void getData(HttpMethod method, String url, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.request(method, url, obtClass(), null), observer);
    }

    /**
     * 上传JSON（携带参数）
     *
     * @param url
     * @param jsonData
     * @param params
     * @param observer
     * @param <T>
     */
    public static <T> void upJson(String url, String jsonData, HttpParams params, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.postJsonRequest(url, obtClass(), jsonData, params), observer);
    }


    /**
     * 上传JSON（不携带参数）
     *
     * @param url
     * @param jsonData
     * @param observer
     * @param <T>
     */
    public static <T> void upJson(String url, String jsonData, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.postJsonRequest(url, obtClass(), jsonData, null), observer);
    }

    /**
     * 上传文件（携带参数）
     *
     * @param url
     * @param file
     * @param params
     * @param observer
     * @param <T>
     */
    public static <T> void upFile(String url, File file, HttpParams params, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.postFileRequest(url, obtClass(), file, params), observer);
    }


    /**
     * 上传文件（不携带参数）
     *
     * @param url
     * @param file
     * @param observer
     * @param <T>
     */
    public static <T> void upFile(String url, File file, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.postFileRequest(url, obtClass(), file, null), observer);
    }

    /**
     * 上传Byte数组(携带参数）
     *
     * @param url
     * @param bytes
     * @param params
     * @param observer
     * @param <T>
     */
    public static <T> void upByte(String url, byte[] bytes, HttpParams params, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.postByteRequest(url, obtClass(), bytes, params), observer);
    }

    /**
     * 上传Byte（不携带参数)
     *
     * @param url
     * @param bytes
     * @param observer
     * @param <T>
     */
    public static <T> void upByte(String url, byte[] bytes, PGObserver<PublishResponse<T>> observer) {
        ApiSubscribe(RxUtils.postByteRequest(url, obtClass(), bytes, null), observer);
    }


    public PublishResponse<T> obtResponse() {
        if (mPublishResponse == null) {
            mPublishResponse = new PublishResponse<>();
        }
        return mPublishResponse;
    }

    public static Class<? extends PublishResponse> obtClass() {
        return ApiMethod.getInstance().obtResponse().getClass();
    }
}
