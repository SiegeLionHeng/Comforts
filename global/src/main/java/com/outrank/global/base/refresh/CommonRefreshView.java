package com.outrank.global.base.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;


/**
 * Created by Administrator on 2020/6/11.
 * Email 17600284843@163.com
 * Description: 公共刷新View
 */
public class CommonRefreshView extends SmartRefreshLayout {

    private BaseHeaderView baseHeaderView;

    public CommonRefreshView(Context context) {
        this(context, null);
    }

    public CommonRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        baseHeaderView = new BaseHeaderView(context);
        baseHeaderView.setLayoutParams(layoutParams);
        addView(baseHeaderView, 0);
    }

}
