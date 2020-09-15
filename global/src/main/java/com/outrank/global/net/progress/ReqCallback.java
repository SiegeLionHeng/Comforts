package com.outrank.global.net.progress;

/**
 * Created by Gabriel on 2020/06/04.
 * Email 17600284843@163.com
 * Description:网络请求回调抽象类
 */
public abstract class ReqCallback<T> implements RequestCallback<T> {
    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(T result) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError() {

    }
}
