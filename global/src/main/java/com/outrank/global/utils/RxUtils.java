/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.outrank.global.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.net.convert.JsonConvert;

import java.io.File;

import io.reactivex.Observable;

/**
 * Created by Gabriel on 2020/06/04.
 * Email 17600284843@163.com
 * Description: 网路请求Rx工具类
 */
public class RxUtils {

    public static <T> Observable<T> request(HttpMethod method, String url, Class<T> clazz, HttpParams params) {
        url = ApiStrategy.baseUrl.concat(url);
        LogUtils.e("url-->" + url);
        Request<T, ? extends Request> request;
        if (method == HttpMethod.GET) request = OkGo.get(url);
        else if (method == HttpMethod.POST) request = OkGo.post(url);
        else if (method == HttpMethod.PUT) request = OkGo.put(url);
        else if (method == HttpMethod.DELETE) request = OkGo.delete(url);
        else if (method == HttpMethod.HEAD) request = OkGo.head(url);
        else if (method == HttpMethod.PATCH) request = OkGo.patch(url);
        else if (method == HttpMethod.OPTIONS) request = OkGo.options(url);
        else if (method == HttpMethod.TRACE) request = OkGo.trace(url);
        else request = OkGo.get(url);
        if (params != null) request.params(params);
        if (clazz != null) {
            request.converter(new JsonConvert<>(clazz));
        }
        return request.adapt(new ObservableBody<>());
    }

    /**
     * 上传JSON
     *
     * @param url
     * @param clazz
     * @param jsonData
     * @param <T>
     * @return
     */
    public static <T> Observable<T> postJsonRequest(String url, Class<T> clazz, String jsonData, HttpParams params) {
        url = ApiStrategy.baseUrl.concat(url);
        PostRequest<T> request = OkGo.post(url);
        if (params != null) request.params(params);
        request.upJson(jsonData);
        if (clazz != null) request.converter(new JsonConvert(clazz));
        return request.adapt(new ObservableBody<>());
    }

    /**
     * 上传文件
     *
     * @param url
     * @param clazz
     * @param file
     * @param <T>
     * @return
     */
    public static <T> Observable<T> postFileRequest(String url, Class<T> clazz, File file, HttpParams params) {
        url = ApiStrategy.baseUrl.concat(url);
        PostRequest<T> request = OkGo.post(url);
        if (params != null) request.params(params);
        request.upFile(file);
        if (clazz != null) request.converter(new JsonConvert(clazz));
        return request.adapt(new ObservableBody<>());
    }

    /**
     * 上传二进制内容
     *
     * @param url
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> Observable<T> postByteRequest(String url, Class<T> clazz, byte[] bytes, HttpParams params) {
        url = ApiStrategy.baseUrl.concat(url);
        PostRequest<T> request = OkGo.post(url);
        if (params != null) request.params(params);
        request.upBytes(bytes);
        if (clazz != null) request.converter(new JsonConvert(clazz));
        return request.adapt(new ObservableBody<>());
    }
}
