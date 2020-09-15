package com.outrank.global.net.progress;


import com.outrank.global.net.response.PublishResponse;

/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: 简单请求结果封装
 */
public interface OnNext<T> {

    void onNext(T t);
}
