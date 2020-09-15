package com.outrank.comfort.ui.module_picvideo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.global.base.mvvm.BaseViewModel;
import com.outrank.global.global.NetUrl;
import com.outrank.global.net.progress.ReqCallback;

import java.util.List;

public class PicListViewModel extends BaseViewModel {

    public MutableLiveData<List<PicListBean>> mPicListBeans = new MutableLiveData<>();

    public PicListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void create() {
        super.create();
        get(NetUrl.REQ_PORT_LIST_PIC_URL, new ReqCallback<List<PicListBean>>() {
            @Override
            public void onSuccess(List<PicListBean> result) {
                String parse = result.toString();
                List<PicListBean> picListBeans = JSON.parseArray(parse, PicListBean.class);
                mPicListBeans.setValue(picListBeans);
            }
        });
    }
}
