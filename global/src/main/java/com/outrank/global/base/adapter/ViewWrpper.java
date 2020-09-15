package com.outrank.global.base.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.utils.WrapperUtils;


/**
 * Created by Gabriel on 2019/4/2.
 * Email 17600284843@163.com
 * Description: 列表头部和脚部封装
 */
public class ViewWrpper<VDB extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<VDB>> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter<BaseViewHolder> mInnerAdapter;

    public ViewWrpper(RecyclerView.Adapter<BaseViewHolder> adapter) {
        mInnerAdapter = adapter;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new BaseViewHolder(mHeaderViews.get(viewType));
        } else if (mFooterViews.get(viewType) != null) {
            return new BaseViewHolder(mFooterViews.get(viewType));
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, (layoutManager, oldLookup, position) -> {
            int viewType = getItemViewType(position);
            if (mHeaderViews.get(viewType) != null) {
                return layoutManager.getSpanCount();
            } else if (mFooterViews.get(viewType) != null) {
                return layoutManager.getSpanCount();
            }
            if (oldLookup != null)
                return oldLookup.getSpanSize(position);
            return 1;
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }


    public void addHeaderView(View view, int index) {
        mHeaderViews.put(index + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFooterViews.clear();
        mFooterViews.put(BASE_ITEM_TYPE_FOOTER, view);
    }

    public View getFooterView() {
        return mFooterViews.get(BASE_ITEM_TYPE_FOOTER);
    }

    public void removeAllFooterViews() {
        mFooterViews.clear();
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    public int getHeaderViewPosition(View view) {
        return mHeaderViews.indexOfValue(view);
    }

    public int getFooterViewPosition(View view) {
        return mFooterViews.indexOfValue(view);
    }

    public void removeHeader(View view) {
        mHeaderViews.removeAt(getHeaderViewPosition(view));
    }
}
