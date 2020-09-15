package com.outrank.global.utils.helper;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.outrank.global.utils.DensityUtils;


/**
 * Created by Gabriel on 2019/12/12.
 * Email 17600284843@163.com
 * Description: RecyclerView 列表工具类
 */
public class RecyclerViewHelper {

    /**
     * 获取不可纵向滑动的LinearLayoutManager
     *
     * @param context
     * @param orientation
     * @return
     */
    public static LinearLayoutManager noVerScrollManager(Context context, boolean orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        orientation(linearLayoutManager, orientation);
        return linearLayoutManager;
    }

    /**
     * 获取不可横向滑动的LinearlayoutManager
     *
     * @param context     上下文
     * @param orientation
     * @return
     */
    public static LinearLayoutManager noHorScrollManager(Context context, boolean orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        orientation(linearLayoutManager, orientation);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        return linearLayoutManager;
    }

    /**
     * 设置列表展示方向
     *
     * @param manager    线性管理器
     * @param isVertical true：VERTICAL  false：HORIZONTAL
     */
    public static void orientation(LinearLayoutManager manager, boolean isVertical) {
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL :
                LinearLayoutManager.HORIZONTAL);
    }


    /**
     * 获取LinearlayoutManager
     *
     * @param context 上下文
     * @return
     */
    public static LinearLayoutManager linear(Context context) {
        return new LinearLayoutManager(context);
    }

    /**
     * 获取GridLayoutManager
     *
     * @param context 上下文
     * @param line    列数
     * @return
     */
    public static GridLayoutManager grid(Context context, int line) {
        return new GridLayoutManager(context, line);
    }

    /**
     * 获取GridLayoutManager
     *
     * @param context     上下文
     * @param line        列数
     * @param orientation 列表方向
     * @return GridLayoutManager
     */
    public static GridLayoutManager grid(Context context, int line, int orientation) {
        return new GridLayoutManager(context, line, orientation, false);
    }


    /**
     * 获取粘性头部ItemDecoration
     *
     * @param adapter
     * @return
     */
    public static StickHeaderDecoration stick(StickHeaderDecoration.StickProvider adapter) {
        return new StickHeaderDecoration(adapter);
    }

    /**
     * 获取线性列表
     *
     * @param width Item之间的宽度
     * @return
     */
    public static LinearSpaceItemDecoration linearHorizontally(int width) {
        return new LinearSpaceItemDecoration(15, width, true);
    }

    /**
     * 获取现行列表ItemDecoration
     *
     * @param inclusiveWidth 左右两边的距离
     * @param width          Item之间的宽度
     * @return
     */
    public static LinearSpaceItemDecoration linearHorizontally(int inclusiveWidth, int width) {
        return new LinearSpaceItemDecoration(inclusiveWidth, width, true);
    }


    /**
     * 获取纵向列表ItemDecoration
     *
     * @param width
     * @return
     */
    public static LinearSpaceItemDecoration linearVertical(int width) {
        return new LinearSpaceItemDecoration(0, width, false);
    }

    /**
     * 获取纵向列表ItemDecoration
     *
     * @param inclusiveWidth 横向左右宽度
     * @param width          纵向高度
     * @return
     */
    public static LinearSpaceItemDecoration linearVertical(int inclusiveWidth, int width) {
        return new LinearSpaceItemDecoration(inclusiveWidth, width, false);
    }

    /**
     * 获取GridSpaceDecoration
     *
     * @param horizontal 内部水平距离(dp)
     * @param vertical   内部垂直距离(dp)
     * @return GridSpaceDecoration
     */
    public static GridSpaceDecoration gridDecoration(int horizontal, int vertical) {
        return new GridSpaceDecoration(DensityUtils.dp(horizontal), DensityUtils.dp(vertical));
    }

    /**
     * 获取GridSpaceDecoration
     *
     * @param horizontal 内部水平距离（dp）
     * @param vertical   内部垂直距离(dp)
     * @param left       最左边距离(dp). 默认为0
     * @param right      最右边距离(dp), 默认为0
     * @return GridSpaceDecoration
     */
    public static GridSpaceDecoration gridDecoration(int horizontal, int vertical, int left, int right) {
        return new GridSpaceDecoration(DensityUtils.dp(horizontal),
                DensityUtils.dp(vertical),
                DensityUtils.dp(left),
                DensityUtils.dp(right));
    }

    /**
     * 获取GridSpaceDecoration
     *
     * @param horizontal 内部水平距离（dp）
     * @param vertical   内部垂直距离(dp)
     * @param left       最左边距离(dp). 默认为0
     * @param right      最右边距离(dp), 默认为0
     * @param top        最顶端的距离(dp), 默认为0
     * @param bottom     最低端的距离(dp), 默认为0
     * @return GridSpaceDecoration
     */
    public static GridSpaceDecoration gridDecoration(int horizontal, int vertical, int left, int right, int top, int bottom) {
        return new GridSpaceDecoration(DensityUtils.dp(horizontal),
                DensityUtils.dp(vertical),
                DensityUtils.dp(left),
                DensityUtils.dp(right),
                DensityUtils.dp(top),
                DensityUtils.dp(bottom));
    }


    /**
     * 设置SpanSizeLookup
     *
     * @param lookup
     * @return
     */
    public static GridLayoutManager.SpanSizeLookup lookup(SpanSizeLookup lookup) {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return lookup.spanSize(position);
            }
        };
    }
}
