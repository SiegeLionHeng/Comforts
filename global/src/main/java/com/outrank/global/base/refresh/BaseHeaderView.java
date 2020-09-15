package com.outrank.global.base.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.outrank.global.R;
import com.outrank.global.databinding.LayoutRefreshHeaderBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

/**
 * Created by Administrator on 2020/6/11.
 * Email 17600284843@163.com
 * Description:
 */
public class BaseHeaderView extends InternalAbstract {

    public static String REFRESH_HEADER_PULLING = "下拉可以刷新";//"下拉可以刷新";
    public static String REFRESH_HEADER_LOADING = "正在加载...";//"正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_FINISH = "刷新成功";//"刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";//"刷新失败";
    private LayoutRefreshHeaderBinding binding;

    protected BaseHeaderView(Context context) {
        this(context, null);
    }

    protected BaseHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected BaseHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_refresh_header, this, false);
        addView(binding.getRoot());
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (binding != null) {
            binding.tvRefreshState.setText(success ? REFRESH_HEADER_FINISH : REFRESH_HEADER_FAILED);
        }
        super.onFinish(refreshLayout, success);
        return 500;
    }


    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        if (binding != null) {
            String state = "";
            switch (newState) {
                case PullDownToRefresh: //下拉过程
                    state = REFRESH_HEADER_PULLING;
                    break;
                case ReleaseToRefresh: //松开刷新
                    state = REFRESH_HEADER_RELEASE;
                    break;
                case Refreshing: //loading中
                    state = REFRESH_HEADER_LOADING;
                    break;
            }
            binding.tvRefreshState.setText(state);
        }
    }
}
