package com.outrank.comfort.ui.module_main.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentMinesBinding;
import com.outrank.comfort.databinding.LayoutContentViewBinding;
import com.outrank.comfort.databinding.LayoutHeadViewBinding;
import com.outrank.comfort.databinding.LayoutHeaderZoomViewBinding;
import com.outrank.comfort.ui.module_main.bean.UserInfo;
import com.outrank.comfort.ui.module_main.viewmodel.MinesViewModel;
import com.outrank.global.base.adapter.GlideBindingAdapter;
import com.outrank.global.base.mvvm.BaseVMFragment;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.global.Const;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.utils.DensityUtils;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.ToastUtils;
import com.outrank.global.utils.Tools;

import org.simple.eventbus.Subscriber;

import java.util.List;

public class MinesFragment extends BaseVMFragment<MinesViewModel, FragmentMinesBinding> {

    private LayoutContentViewBinding mContentBinding;
    private LayoutHeadViewBinding mHeadView;
    private LayoutHeaderZoomViewBinding mZoomBinding;

    private final float mScale = 0.45f;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mines;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mViewModel.obtainUserInfo();
        mZoomBinding = inflate(R.layout.layout_header_zoom_view);
        mHeadView = inflate(R.layout.layout_head_view);
        mContentBinding = inflate(R.layout.layout_content_view);
        mBinding.ptzsvView.setHeaderView(mHeadView.getRoot());
        mBinding.ptzsvView.setScrollContentView(mContentBinding.getRoot());
        mBinding.ptzsvView.setZoomView(mZoomBinding.getRoot());
        mBinding.ptzsvView.setParallax(false);
        mBinding.ptzsvView.setCustomHeaderHeight(getActivity().getWindowManager(), mScale);
        mViewModel.mUserInfo.observe(this, this::showUserInfoSuccess);
        mHeadView.tvVipText.setTypeface(Tools.fonts(Const.FONT.FONT_SHARP_WORD_STYLE_TYPEFACE));
        mHeadView.tvVipEquity.setTypeface(Tools.fonts(Const.FONT.FONT_SHARP_WORD_STYLE_TYPEFACE));
    }

    /**
     * 请求用户信息回调接口
     *
     * @param userInfo
     */
    private void showUserInfoSuccess(UserInfo userInfo) {
        // 设置昵称
        mBinding.tbPerCenter.setLeftText(userInfo.getNickname());
        // 初始化头部Img
        String headerUrl = ApiStrategy.baseUrl.concat(userInfo.getIcon_header());
        GlideBindingAdapter.setCircleImageUrl(mHeadView.ivHeader, headerUrl, DensityUtils.dp(2), Color.WHITE);
        // 初始化背景IMG
        String bgImgUrl = ApiStrategy.baseUrl.concat(userInfo.getIcon_bg());
        GlideBindingAdapter.setRoundImageUrl(mZoomBinding.ivZoomView, bgImgUrl, 0);
        // 设置title
        mHeadView.tvNickname.setText(userInfo.getNickname());
        String signature = TextUtils.isEmpty(userInfo.getSignature()) ? Res.string(R.string.str_mine_signature) : userInfo.getSignature();
        mHeadView.tvMineSignature.setText(signature);
        // 性别
        mHeadView.ivUserSex.setImageDrawable(mViewModel.getSexDrawable(userInfo.getSex()));
        // 性别背景
        mHeadView.rlUserSexBg.setBackground(mViewModel.getSexBackground(userInfo.getSex()));
        // 获赞数
        mHeadView.tvLikeCount.setText(userInfo.getPraise().concat("获赞"));
        // 关注数
        mHeadView.tvAttention.setText(userInfo.getAttention().concat("关注"));
        // 粉丝数
        mHeadView.tvFansCount.setText(userInfo.getFans().concat("粉丝"));
        // 广告位
        List<UserInfo.AdListBean> ad_list = userInfo.getAd_list();
        if (ad_list != null && ad_list.size() != 0) {
            String adImg = ApiStrategy.baseUrl.concat(ad_list.get(0).getAd_img());
            GlideBindingAdapter.setRoundImageUrl(mContentBinding.ivAdPosition, adImg, DensityUtils.dp(5));
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        // topbar颜色渐变
        mBinding.ptzsvView.addScrollChangedListener((left, top, oldLeft, oldTop) -> mViewModel.setScrollTopbarColor(top, mBinding, mScale));
        // 切换背景
        mZoomBinding.ivZoomView.setOnClickListener(view -> intentByRouter(ARouterPath.INTENTKEY_PERSONAL_BG_ACTIVITY));
        // 跳转个人中心
        mHeadView.rlPersonalData.setOnClickListener(view -> ToastUtils.showToast("跳转个人中心"));
        mHeadView.ivHeader.setOnClickListener(view -> ToastUtils.showToast("跳转个人中心"));
        // 跳转会员中心
        mHeadView.rlVipView.setOnClickListener(view -> ToastUtils.showToast("跳转会员中心"));
        // 跳转视频壁纸
        mContentBinding.llVideo.setOnClickListener(view -> intentByRouter(ARouterPath.INTENTKEY_VIDEO_WALLPAPER_ACTIVITY));
    }

    @Subscriber(tag = Const.EVENTBUS_CHANGED_PERSONAL_BG_TAG)
    public void changedPersonalBg(String bgUrl) {
        LogUtils.e("bgUrl-->" + bgUrl);
        GlideBindingAdapter.setRoundImageUrl(mZoomBinding.ivZoomView, bgUrl, 0);
    }

    @Subscriber(tag = Const.EVENTBUS_CHANGED_PERSONAL_HEADER_TAG)
    public void changedPersonalHeader(String bgUrl) {
        GlideBindingAdapter.setCircleImageUrl(mHeadView.ivHeader, bgUrl, DensityUtils.dp(3), Color.WHITE);
    }

    @Override
    protected boolean bindEvent() {
        return true;
    }
}
