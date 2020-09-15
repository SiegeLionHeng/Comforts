package com.outrank.comfort.ui.module_picvideo.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.dazzle.jvplayer.JVPlayer;
import com.dazzle.jvplayer.JVPlayerManager;
import com.dazzle.jvplayer.LogUtils;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityVideoWallparperBinding;
import com.outrank.comfort.ui.module_picvideo.bean.VideoUrlBean;
import com.outrank.comfort.ui.module_picvideo.bean.WallpaperBean;
import com.outrank.comfort.utils.OnMultiListener;
import com.outrank.global.base.mvvm.BaseViewModel;
import com.outrank.global.global.NetUrl;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.net.progress.ReqCallback;
import com.outrank.global.utils.ColorUtil;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.ScreenUtils;
import com.outrank.global.utils.ToastUtils;
import com.outrank.global.view.ColorFlipPagerTitleView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import net.lucode.hackware.magicindicator.abs.IPagerNavigator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/7.
 * Email 17600284843@163.com
 * Description: 视频图集VM
 */
public class VideoWallpaperViewModel extends BaseViewModel {

    /**
     * 视频列表数据回调
     */
    public MutableLiveData<List<VideoUrlBean>> mVideoUrlBean = new MutableLiveData<>();

    /**
     * 图片列表数据回调
     */
    public MutableLiveData<List<WallpaperBean>> mWallpaperBean = new MutableLiveData<>();

    /**
     * 图片列表Loadmore
     */
    public MutableLiveData<List<WallpaperBean>> mWallMoreBean = new MutableLiveData<>();

    /**
     * 页面切换内容
     */
    public MutableLiveData<Integer> mPageSelect = new MutableLiveData<>();
    /**
     * 页面切换内容
     */
    public MutableLiveData<Integer> mOnMoving = new MutableLiveData<>();
    /**
     * 页面切换内容
     */
    public MutableLiveData<Integer> mOnSetCurrentPage = new MutableLiveData<>();

    /**
     * 页面切换内容
     */
    public MutableLiveData<Integer> mWallSelect = new MutableLiveData<>();


    public VideoWallpaperViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 请求视频接口
     */
    public void reqVideoListData() {
        showLoading();
        List<VideoUrlBean> videoUrlBeans = dealWithList("http://sqbj-public.oss-cn-beijing.aliyuncs.com/picture/017d88ca95400000_o_20200723_181844.mp4");
        mVideoUrlBean.setValue(videoUrlBeans);
//
//        get(NetUrl.REQ_PORT_LIST_VIDEO_WALLPAPER_URL, new ReqCallback<List<VideoUrlBean>>() {
//            @Override
//            public void onSuccess(List<VideoUrlBean> result) {
//                if (isEmpty(result)) {
//
//                }
//            }
//
//            @Override
//            public void onError() {
//                showError();
//            }
//        });
    }

    @SuppressLint("DefaultLocale")
    public void reqWallListData(int page) {
        String forUrl = String.format(NetUrl.REQ_PORT_LIST_WALL_PAPER_URL, page);
        LogUtils.e("forUrl-->" + forUrl);
        get(forUrl, new ReqCallback<List<WallpaperBean>>() {
            @Override
            public void onSuccess(List<WallpaperBean> result) {
                if (isEmpty(result)) {
                    mWallpaperBean.setValue(parseArray(result.toString(), WallpaperBean.class));
                }
            }

            @Override
            public void onError() {
                mOnSetCurrentPage.setValue(page);
                ToastUtils.showToast("暂无更多");
            }
        });
    }


    /**
     * 处理返回数据
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    private List<VideoUrlBean> dealWithList(String url) {
        List<VideoUrlBean> newList = new ArrayList<>();
        VideoUrlBean newVideoUrl = new VideoUrlBean();
        newVideoUrl.setVideo_url(url);
        newVideoUrl.setTitle("title");
        newVideoUrl.setImageUrl(ApiStrategy.baseUrl.concat("/picture/NewThread/11/pic_11 (67).jpg"));
        newVideoUrl.setLength(60000);
        newList.add(newVideoUrl);
        return newList;
    }


    /**
     * 处理返回数据
     *
     * @param result
     * @return
     */
    @SuppressLint("DefaultLocale")
    private List<VideoUrlBean> dealWithList(List<VideoUrlBean> result) {
        List<VideoUrlBean> results = parseArray(result.toString(), VideoUrlBean.class);
        List<VideoUrlBean> newList = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            VideoUrlBean oldVideo = results.get(i);
            for (int k = 1; k <= oldVideo.getVideo_size(); k++) {
                VideoUrlBean newVideoUrl = new VideoUrlBean();
                String url = ApiStrategy.baseUrl.concat(String.format(oldVideo.getVideo_url(), k));
                newVideoUrl.setVideo_url(url);
                newVideoUrl.setTitle(String.format(oldVideo.getTitle(), k));
                newVideoUrl.setImageUrl(ApiStrategy.baseUrl.concat("/picture/NewThread/11/pic_11 (67).jpg"));
                newVideoUrl.setLength(60000);
                newList.add(newVideoUrl);
            }
        }
        return newList;
    }


    public IPagerNavigator getCommonNavigator(String[] arrayTabs, Activity mActivity, boolean isEnable) {
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setEnablePivotScroll(isEnable);//多指示器模式，可以滑动
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return arrayTabs.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorFlipPagerTitleView simplePagerTitleView = getColorFlipPagerTitleView(context, index, arrayTabs[index]);
                simplePagerTitleView.setOnClickListener(v -> onPageSelected(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return getLinePagerIndicator(context);
            }
        });
        return commonNavigator;
    }

    /**
     * 切換数据源
     *
     * @param index
     */
    private void onPageSelected(int index) {
        mPageSelect.setValue(index);
    }


    public IPagerNavigator getNavigator(String[] arrayTabs, Activity mActivity, boolean isEnable) {
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setEnablePivotScroll(isEnable);//多指示器模式，可以滑动
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return arrayTabs.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorFlipPagerTitleView simplePagerTitleView = getColorFlipPagerTitleView(context, index, arrayTabs[index]);
                simplePagerTitleView.setOnClickListener(v -> onFragmentSelect(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return getLinePagerIndicator(context);
            }
        });
        return commonNavigator;
    }

    /**
     * 图集切换
     *
     * @param index
     */
    private void onFragmentSelect(int index) {
        mWallSelect.setValue(index);
    }

    private LinePagerIndicator getLinePagerIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(UIUtil.dip2px(context, 4));
        indicator.setLineWidth(UIUtil.dip2px(context, 20));
        indicator.setRoundRadius(UIUtil.dip2px(context, 2));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        indicator.setColors(Res.color(R.color.background_weight));
        return indicator;
    }

    private ColorFlipPagerTitleView getColorFlipPagerTitleView(Context context, int index, String arrayTab) {
        ColorFlipPagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
        simplePagerTitleView.setPadding(20, 14, 20, 14);
        simplePagerTitleView.setText(arrayTab);
        simplePagerTitleView.setNormalColor(Res.color(R.color.black_text));
        simplePagerTitleView.setTextColor(Res.color(R.color.black_text));
        simplePagerTitleView.setSelectedColor(Res.color(R.color.background_weight));
        simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        simplePagerTitleView.setBackgroundColor(Color.TRANSPARENT);
        return simplePagerTitleView;
    }


    /**
     * 设置TitleBG颜色
     *
     * @param top
     * @param binding
     * @param mScale
     */
    public void setScrollTopbarColor(int top, ActivityVideoWallparperBinding binding, float mScale) {
        int screenHeight = ScreenUtils.getScreenHeight();
        float zoomViewHeight = screenHeight * mScale;
        float fraction = top / zoomViewHeight;
        if (fraction > 0 && fraction < 1) {
            int bgColor = ColorUtil.getNewColorByStartEndColor(fraction, R.color.transparent, R.color.white);
            int textColor = ColorUtil.getNewColorByStartEndColor(fraction, R.color.transparent, R.color.black_text);
            VectorDrawableCompat vectorDrawable = ColorUtil.getVectorDrawable(fraction, R.color.white, R.color.black_text, R.drawable.ic_black);
            binding.topbar.setLeftImageDrawable(vectorDrawable);
            binding.topbar.setBackgroundColor(bgColor);
            binding.topbar.setTopbarTitleTextColor(textColor);
        } else if (fraction == 0) {
            binding.topbar.setLeftImageDrawable(ColorUtil.setVectorColor(R.color.white, R.drawable.ic_black));
            binding.topbar.setBackgroundColor(Res.color(R.color.transparent));
            binding.topbar.setTopbarTitleTextColor(Res.color(R.color.transparent));
        } else if (fraction >= 1) {
            binding.topbar.setLeftImageDrawable(ColorUtil.setVectorColor(R.color.black_text, R.drawable.ic_black));
            binding.topbar.setBackgroundColor(Res.color(R.color.white));
            binding.topbar.setTopbarTitleTextColor(Res.color(R.color.black_text));
        }
    }

    /**
     * 清楚其他列表播放视频
     *
     * @param viewHolder
     */
    public void onViewHolderRelease(RecyclerView.ViewHolder viewHolder) {
        JVPlayer vPlayer = viewHolder.itemView.findViewById(R.id.v_player);
        if (vPlayer == JVPlayerManager.instance().getCurrentJVPlayer()) {
            JVPlayerManager.instance().releaseJVPlayer();
        }
    }

    /**
     * SmartRefresh滑动监听器
     */
    public OnMultiListener onMultiListener = new OnMultiListener() {
        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
            super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
            mOnMoving.setValue(null);
        }

        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            super.onRefresh(refreshLayout);
            refreshLayout.finishRefresh();
        }
    };

}
