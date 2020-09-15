package com.outrank.global.net.response;

import java.io.Serializable;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 公共请求体封装
 */
public class PublishResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int status;
    public String msg;
    public T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
                "\tstatus=" + status + "\n" +//
                "\tmsg='" + msg + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }
}
