package com.outrank.comfort.ui.module_main.fragment;

import android.os.Bundle;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentMessageBinding;
import com.outrank.comfort.ui.module_main.viewmodel.MainViewModel;
import com.outrank.global.base.mvvm.BaseVMFragment;

public class MessageFragment extends BaseVMFragment<MainViewModel, FragmentMessageBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
