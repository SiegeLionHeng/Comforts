package com.outrank.comfort.ui.module_main.fragment;

import android.os.Bundle;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentChoicenessBinding;
import com.outrank.comfort.ui.module_main.viewmodel.MainViewModel;
import com.outrank.global.base.mvvm.BaseVMFragment;

public class ChoicenessFragment extends BaseVMFragment<MainViewModel, FragmentChoicenessBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_choiceness;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
