package com.outrank.global.utils.helper;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.global.utils.DensityUtils;


/**
 * Created by Gabriel on 2019/12/16.
 * Email 17600284843@163.com
 * Description:
 */
public class LinearSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mWidth;
    private int mInclusiveWidth;
    private boolean isHorizontal;

    public LinearSpaceItemDecoration(int inclusiveWidth, int width, boolean isHorizontal) {
        mWidth = width;
        mInclusiveWidth = inclusiveWidth;
        this.isHorizontal = isHorizontal;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager != null) {
            if (isHorizontal) {
                int lastCount = manager.getItemCount() - 1;
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = DensityUtils.dp(mInclusiveWidth);
                } else if (parent.getChildAdapterPosition(view) == lastCount) {
                    outRect.right = DensityUtils.dp(mInclusiveWidth);
                    outRect.left = mWidth;
                } else {
                    outRect.left = mWidth;
                }
            } else {
                outRect.set(DensityUtils.dp(mInclusiveWidth), 0, DensityUtils.dp(mInclusiveWidth), DensityUtils.dp(mWidth));
            }
        }
    }
}
