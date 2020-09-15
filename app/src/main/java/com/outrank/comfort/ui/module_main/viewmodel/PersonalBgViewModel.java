package com.outrank.comfort.ui.module_main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.global.base.mvvm.BaseViewModel;
import com.outrank.global.global.NetUrl;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.net.progress.ReqCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/5.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class PersonalBgViewModel extends BaseViewModel {

    public MutableLiveData<List<PicListBean>> picListBeans = new MutableLiveData<>();

    public PersonalBgViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void create() {
        super.create();
        reqListPost();
    }

    public void reqListPost() {
        get(NetUrl.REQ_PORT_LIST_PIC_URL, new ReqCallback<List<PicListBean>>() {
            @Override
            public void onSuccess(List<PicListBean> result) {
                String s = result.toString();
                List<PicListBean> list = JSON.parseArray(s, PicListBean.class);
                if (isEmpty(list)) picListBeans.setValue(list);
                showSuccess();
            }

            @Override
            public void onError() {
                super.onError();
                showError();
            }
        });
    }

    /**
     * 获取图集列表
     *
     * @param bean
     * @return
     */
    public ArrayList<String> obtationAtlasList(PicListBean bean) {
        ArrayList<String> imgUrlList = new ArrayList<>();
        for (int i = 1; i <= bean.getDetailSize(); i++) {
            String imgUrl = ApiStrategy.baseUrl.concat(String.format(bean.getDetailTitle(), i)).concat(".jpg");
            imgUrlList.add(imgUrl);
        }
        return imgUrlList;
    }
}
