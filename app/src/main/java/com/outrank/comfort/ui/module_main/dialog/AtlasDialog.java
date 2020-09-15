package com.outrank.comfort.ui.module_main.dialog;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.DialogSelectAtlasBinding;
import com.outrank.global.base.BaseDialog;
import com.outrank.global.global.Const;

/**
 * Created by Administrator on 2020/6/6.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class AtlasDialog extends BaseDialog<DialogSelectAtlasBinding> {

    private OnDialogClickListener onDialogClickListener;

    public AtlasDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_select_atlas;
    }

    @Override
    protected void initData() {
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void setListener() {
        mBinding.tvSetBg.setOnClickListener(view -> {
            if (onDialogClickListener != null) {
                onDialogClickListener.OnDialogClick(Const.AtlasSelect.ATLAS_SELECT_BACKGROUND);
            }
        });
        mBinding.tvSetHeader.setOnClickListener(view -> {
            if (onDialogClickListener != null) {
                onDialogClickListener.OnDialogClick(Const.AtlasSelect.ATLAS_SELECT_HEADER);
            }
        });
        mBinding.tvCancel.setOnClickListener(view -> cancelDialog());
    }

    @Override
    protected boolean onSetFillWidth() {
        return true;
    }

    public interface OnDialogClickListener {
        void OnDialogClick(String flag);
    }

    public void addOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }
}
