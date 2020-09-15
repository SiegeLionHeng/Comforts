package com.outrank.comfort.ui.module_main.viewmodel;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.alibaba.fastjson.JSON;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentMinesBinding;
import com.outrank.comfort.ui.module_main.bean.UserInfo;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.global.base.mvvm.BaseViewModel;
import com.outrank.global.global.NetUrl;
import com.outrank.global.net.progress.ReqCallback;
import com.outrank.global.utils.ColorUtil;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2020/6/7.
 * Email 17600284843@163.com
 * Description: 主ViewModel
 */
public class MainViewModel extends BaseViewModel {


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void create() {
        super.create();
    }

}
