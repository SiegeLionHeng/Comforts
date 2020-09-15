package com.outrank.comfort.ui.module_main.fragment;

import android.os.Bundle;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentCreationBinding;
import com.outrank.comfort.ui.module_main.viewmodel.MainViewModel;
import com.outrank.global.base.mvvm.BaseVMFragment;

public class CreationFragment extends BaseVMFragment<MainViewModel, FragmentCreationBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_creation;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
