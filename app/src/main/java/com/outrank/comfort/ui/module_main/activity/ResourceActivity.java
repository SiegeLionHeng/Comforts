package com.outrank.comfort.ui.module_main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityResourceBinding;
import com.outrank.comfort.databinding.ItemResourceBinding;
import com.outrank.comfort.ui.module_main.adapter.ResourceAdapter;
import com.outrank.comfort.ui.module_main.viewmodel.ResourceViewModel;
import com.outrank.global.base.BaseActivity;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.base.mvvm.BaseVMActivity;
import com.outrank.global.base.mvvm.BaseVMFragment;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.helper.RecyclerViewHelper;

import java.util.List;

/**
 * Created by Administrator on 2020/6/12.
 * Email 17600284843@163.com
 * Description:
 */
@Route(path = ARouterPath.INTENTKEY_RESOURCE_ACTIVITY, name = "资源搜索界面")
public class ResourceActivity extends BaseVMActivity<ResourceViewModel, ActivityResourceBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_resource;
    }

    @Override
    protected View getTargetView() {
        return mBinding.topbar;
    }

    @Override
    protected void init() {
        showSuccess();
        List<Integer> resData = mViewModel.getResData();
        LogUtils.e("resdata-->" + resData.size());
        mBinding.rvView.setLayoutManager(RecyclerViewHelper.grid(this, 3));
        ResourceAdapter adapter = new ResourceAdapter(this);
        mBinding.rvView.setAdapter(adapter);
        adapter.update(resData);
        adapter.setOnItemClickListener((holder, index) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("integer", index);
            intentByRouter(ARouterPath.INTENTKEY_PDF_ACTIVITY, bundle);
        });
    }
}
