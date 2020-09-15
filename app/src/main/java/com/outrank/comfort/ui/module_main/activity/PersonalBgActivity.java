package com.outrank.comfort.ui.module_main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityPersonalBgBinding;
import com.outrank.comfort.databinding.ItemPersonalBgBinding;
import com.outrank.comfort.ui.module_main.adapter.PersonalBgAdapter;
import com.outrank.comfort.ui.module_main.view.SelectPhotoHeaderView;
import com.outrank.comfort.ui.module_main.viewmodel.PersonalBgViewModel;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.global.base.adapter.ViewWrpper;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.base.mvvm.BaseVMActivity;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.global.Const;
import com.outrank.global.utils.DensityUtils;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.helper.RecyclerViewHelper;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/5.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
@Route(path = ARouterPath.INTENTKEY_PERSONAL_BG_ACTIVITY, name = "个性背景")
public class PersonalBgActivity extends BaseVMActivity<PersonalBgViewModel, ActivityPersonalBgBinding> {

    private PersonalBgAdapter bgAdapter;

    @Override
    protected View getTargetView() {
        return mBinding.srlView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_bg;
    }

    @Override
    protected void init() {
        // 初始化列表加载
        initRecycleView();
    }


    /**
     * 初始化列表加载
     */
    private void initRecycleView() {
        bgAdapter = new PersonalBgAdapter(mActivity);
        mBinding.rvView.setAdapter(bgAdapter);
        mBinding.rvView.setLayoutManager(RecyclerViewHelper.grid(mActivity, 3));
        mBinding.rvView.addItemDecoration(RecyclerViewHelper.gridDecoration(DensityUtils.dp(2), DensityUtils.dp(2), DensityUtils.dp(5), DensityUtils.dp(5)));
        mViewModel.picListBeans.observe(this, this::update);
    }

    private void update(List<PicListBean> picListBeans) {
        bgAdapter.update(picListBeans);
        // 初始化列表Header
        initRecycleHeader();
    }

    private void initRecycleHeader() {
        ViewWrpper viewParent = new ViewWrpper(bgAdapter);
        SelectPhotoHeaderView headerView = new SelectPhotoHeaderView(mActivity);
        headerView.fillView("", mBinding.rvView, viewParent);
        mBinding.rvView.setAdapter(viewParent);
        viewParent.notifyDataSetChanged();
    }


    @Override
    protected void initListener() {
        super.initListener();
        bgAdapter.setOnItemClickListener((holder, model) -> {
            LogUtils.e("model-->" + JSON.toJSONString(model));
            ArrayList<String> atlasList = mViewModel.obtationAtlasList(model);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(Const.KEY_IMG_URL_ATLAS, atlasList);
            intentByRouter(ARouterPath.INTENTKEY_ATLAS_LIST_ACTIVITY, bundle);
        });
    }

    @Override
    protected void onReloadListener(View v) {
        super.onReloadListener(v);
        mViewModel.reqListPost();
    }

    @Subscriber(tag = Const.EVENTBUS_PERSONAL_BG_ACTIVITY)
    public void finishThis(String finish) {
        mViewModel.finish();
    }
}
