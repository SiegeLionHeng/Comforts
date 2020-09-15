package com.outrank.comfort.ui.module_main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.outrank.global.base.mvvm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/12.
 * Email 17600284843@163.com
 * Description:
 */
public class ResourceViewModel extends BaseViewModel {
    public ResourceViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Integer> getResData() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 225; i++) {
            list.add(i);
        }
        return list;
    }
}
