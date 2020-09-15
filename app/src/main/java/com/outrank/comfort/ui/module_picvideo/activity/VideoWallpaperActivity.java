package com.outrank.comfort.ui.module_picvideo.activity;

import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dazzle.jvplayer.JVPlayerManager;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityVideoWallparperBinding;
import com.outrank.comfort.ui.module_picvideo.adapter.VideoAdapter;
import com.outrank.comfort.ui.module_picvideo.fragment.VideoListFragment;
import com.outrank.comfort.ui.module_picvideo.fragment.WallpaperFragment;
import com.outrank.comfort.ui.module_picvideo.fragment.WallpapersFragment;
import com.outrank.comfort.ui.module_picvideo.viewmodel.VideoWallpaperViewModel;
import com.outrank.global.base.mvvm.BaseVMActivity;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.utils.Res;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/7.
 * Email 17600284843@163.com
 * Description: 视频壁纸界面
 */
@Route(path = ARouterPath.INTENTKEY_VIDEO_WALLPAPER_ACTIVITY, name = "视频壁纸")
public class VideoWallpaperActivity extends BaseVMActivity<VideoWallpaperViewModel, ActivityVideoWallparperBinding> {

    public final String[] arrayTabs = new String[]{Res.string(R.string.str_video), Res.string(R.string.str_pic)};

    private List<Fragment> fragments;

    private VideoListFragment mVideoListFragment;
    private WallpapersFragment mWallpapersFragment;
    private Fragment currentFragment;

    @Override
    protected View getTargetView() {
        return mBinding.fraContent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_wallparper;
    }

    @Override
    protected void init() {
        mViewModel.mOnMoving.observe(this, integer -> mBinding.topbar.setLeftImage(R.drawable.ic_black));
        // 初始化指示器内容
        initMagicIndicator();
        // 请求视频列表
        mViewModel.reqVideoListData();
        // 初始化视频列表
//        initRecyclerVideoList();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        mVideoListFragment = new VideoListFragment();
        mWallpapersFragment = new WallpapersFragment();
        fragments.add(mVideoListFragment);
        fragments.add(mWallpapersFragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fra_content, mVideoListFragment).commit();
        currentFragment = mVideoListFragment;
    }

    /**
     * 切换Fragment
     *
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.fra_content, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }

    /**
     * 初始化指示器
     */
    private void initMagicIndicator() {
        mBinding.messageIndicator.setNavigator(mViewModel.getCommonNavigator(arrayTabs, mActivity, false));
        mViewModel.mPageSelect.observe(this, this::onPageSelect);
    }

    /**
     * 页面切换
     *
     * @param index
     */
    private void onPageSelect(Integer index) {
        mBinding.messageIndicator.onPageSelected(index);
        // TODO: 2020/6/8 切换Fragment
        switchFragment(fragments.get(index));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBinding.srlVideoView.setOnMultiPurposeListener(mViewModel.onMultiListener);
        mBinding.nsvView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> mViewModel.setScrollTopbarColor(scrollY, mBinding, 0.3f));
        mBinding.topbar.setOnClickBackKeyListener(() -> JVPlayerManager.instance().releaseJVPlayer());
    }

    @Override
    public void onBackPressed() {
        if (JVPlayerManager.instance().onBackPressd()) return;
        JVPlayerManager.instance().releaseJVPlayer();
        super.onBackPressed();
    }
}
