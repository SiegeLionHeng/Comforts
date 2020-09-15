package com.outrank.global.net.convert;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.convert.Converter;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: FastJSON 解析器
 */
public class JsonConvert<T> implements Converter<T> {

    private Class<T> mClass;

    public JsonConvert(Class<T> tClass) {
        mClass = tClass;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        String string = body.string();
        return JSON.parseObject(string, mClass);
    }
}
