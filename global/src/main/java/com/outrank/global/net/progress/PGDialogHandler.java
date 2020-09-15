package com.outrank.global.net.progress;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.outrank.global.dialog.ZLoadDialog;


/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description:ProgressDialog监听Handler
 */
public class PGDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ZLoadDialog mDialog;

    private Context context;
    private PGCancelListener mProgressCancelListener;

    public PGDialogHandler(Context context, PGCancelListener mProgressCancelListener) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    /**
     * 初始化Dialog
     */
    private void initProgressDialog() {
        if (mDialog == null) {
            mDialog = new ZLoadDialog(context);
            mDialog.setOnCancelListener(() -> mProgressCancelListener.onCancelProgress());
            mDialog.show();
        }
    }

    /**
     * 结束Dialog
     */
    private void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.close();
            mDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
