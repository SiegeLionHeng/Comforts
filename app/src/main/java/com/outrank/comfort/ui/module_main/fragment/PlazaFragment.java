package com.outrank.comfort.ui.module_main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentPlazaBinding;
import com.outrank.comfort.databinding.LayoutAttentionDynamicBinding;
import com.outrank.comfort.ui.module_main.adapter.PlazaAdapter;
import com.outrank.comfort.ui.module_main.listener.OnPlazaHeaderClickListener;
import com.outrank.comfort.ui.module_main.listener.OnPlazaScroll;
import com.outrank.comfort.ui.module_main.view.AttentionDynamicView;
import com.outrank.comfort.ui.module_main.view.PlazaHeaderView;
import com.outrank.comfort.ui.module_main.viewmodel.PlazaViewModel;
import com.outrank.global.base.adapter.ViewWrpper;
import com.outrank.global.base.mvvm.BaseVMFragment;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.ToastUtils;
import com.outrank.global.utils.helper.RecyclerViewHelper;

import java.util.List;

public class PlazaFragment extends BaseVMFragment<PlazaViewModel, FragmentPlazaBinding> implements OnPlazaHeaderClickListener {


    private PlazaAdapter plazaAdapter;
    private int totalScrollY;
    private PlazaHeaderView headerView;
    private AttentionDynamicView attentionDynamicView;
    private ViewWrpper parent;
    private OnPlazaScroll onPlazaScroll;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_plaza;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPlazaRecyclerView();
        mViewModel.reqPlazaData();
    }

    private void initPlazaRecyclerView() {
        plazaAdapter = new PlazaAdapter(mContext);
        mBinding.rvPlazaView.setLayoutManager(RecyclerViewHelper.linear(mContext));
        mBinding.rvPlazaView.setAdapter(plazaAdapter);
        mViewModel.mPlazaModel.observe(this, this::onPlazaSuccess);
    }

    private void onPlazaSuccess(List<String> strings) {
        plazaAdapter.update(strings);
        parent = new ViewWrpper(plazaAdapter);
        headerView = new PlazaHeaderView(getActivity());
        headerView.fillView("", mBinding.rvPlazaView, parent);
        attentionDynamicView = new AttentionDynamicView(getActivity());
        attentionDynamicView.fillView("", mBinding.rvPlazaView, parent);
        mBinding.rvPlazaView.setAdapter(parent);
        parent.notifyDataSetChanged();
        initListener();
    }

    private void initListener() {
        onPlazaScroll = new OnPlazaScroll(mBinding.fraContent, headerView, inflate(R.layout.layout_attention_dynamic));
        mBinding.rvPlazaView.addOnScrollListener(onPlazaScroll);
        onPlazaScroll.getInflate().tvSendDynamic.setOnClickListener(view -> skipSendDynamic());
        attentionDynamicView.setDynamicListener(this::skipSendDynamic);
        headerView.setPlazaHeaderClickListener(this);
    }


    /**
     * 跳转发送动态页面
     */
    private void skipSendDynamic() {
        ToastUtils.showToast("发送动态页面");
    }

    @Override
    public void OnSearchResClick() {
        intentByRouter(ARouterPath.INTENTKEY_RESOURCE_ACTIVITY);
    }

    @Override
    public void OnMakeFriendsClick() {

    }
}
