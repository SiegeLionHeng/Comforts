package com.outrank.comfort.ui.module_picvideo.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityPicListBinding;
import com.outrank.comfort.ui.module_picvideo.adapter.PicListAdapter;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.comfort.ui.module_picvideo.dialog.PhotoDialog;
import com.outrank.comfort.ui.module_picvideo.viewmodel.PicListViewModel;
import com.outrank.global.base.mvvm.BaseVMActivity;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.utils.LogUtils;

import java.util.ArrayList;

@Route(path = ARouterPath.INTENTKEY_PIC_LIST_ACTIVITY, name = "图片列表页")
public class PicListActivity extends BaseVMActivity<PicListViewModel, ActivityPicListBinding> {

    @Override
    protected View getTargetView() {
        return mBinding.srlView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_list;
    }

    @Override
    protected void init() {
        PicListAdapter adapter = new PicListAdapter(mActivity);
        mBinding.rvView.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvView.setAdapter(adapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mBinding.rvView);//初始化数据
        adapter.setOnImageClickListenerl(this::OnImgClick);
        // 设置数据
        mViewModel.mPicListBeans.observe(this, adapter::update);
    }

    private void OnImgClick(PicListBean bean) {
        int detailSize = bean.getDetailSize();
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i <= detailSize; i++) {
            String format = String.format(bean.getDetailTitle(), i);
            String url = ApiStrategy.baseUrl.concat(format).concat(".jpg");
            LogUtils.e("bean.getDetailTitle()-->" + url);
            data.add(url);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("currentPostion", 0);
        bundle.putStringArrayList("imageData", data);
        PhotoDialog photoDialog = new PhotoDialog();
        photoDialog.setArguments(bundle);
        photoDialog.show(getSupportFragmentManager(), "");
    }
}
