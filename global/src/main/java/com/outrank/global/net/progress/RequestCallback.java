package com.outrank.global.net.progress;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 网络请求回调接口
 */
public interface RequestCallback<T> {

    void onStart();

    void onSuccess(T result);

    void onComplete();

    void onError();
}
