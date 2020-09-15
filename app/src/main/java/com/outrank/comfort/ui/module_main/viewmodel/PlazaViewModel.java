package com.outrank.comfort.ui.module_main.viewmodel;

import android.app.Application;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.LayoutAttentionDynamicBinding;
import com.outrank.comfort.ui.module_main.view.PlazaHeaderView;
import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.base.mvvm.BaseViewModel;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.RxJavaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/10.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class PlazaViewModel extends BaseViewModel {

    public MutableLiveData<List<String>> mPlazaModel = new MutableLiveData<>();

    public PlazaViewModel(@NonNull Application application) {
        super(application);
    }

    public void reqPlazaData() {
        RxJavaUtils.timedTask(200, () -> mPlazaModel.setValue(getFalseData()));
    }


    /**
     * 测试数据
     *
     * @return
     */
    private List<String> getFalseData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            list.add("你好");
        }
        return list;
    }
}
