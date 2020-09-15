package com.outrank.comfort.ui.module_main.activity;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityPdfBinding;
import com.outrank.comfort.ui.module_main.viewmodel.PdfViewModel;
import com.outrank.global.base.mvvm.BaseVMActivity;
import com.outrank.global.base.mvvm.BaseVMFragment;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2020/6/12.
 * Email 17600284843@163.com
 * Description:
 */
@Route(path = ARouterPath.INTENTKEY_PDF_ACTIVITY, name = "PDF展示")
public class PdfActivity extends BaseVMActivity<PdfViewModel, ActivityPdfBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    protected View getTargetView() {
        return mBinding.pvView;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void init() {
        int integer = getIntent().getIntExtra("integer", 1);
        String format = ApiStrategy.baseUrl.concat(String.format("/picture/pdf_content/PDF015 (%d).PDF", integer));
        LogUtils.e("format00>" + format);
        executorAsyncTask(format);
    }

    /**
     * 执行异步任务
     */
    @SuppressLint("StaticFieldLeak")
    private void executorAsyncTask(String mPdfUrl) {
        new AsyncTask<String, Void, InputStream>() {
            @Override
            protected InputStream doInBackground(String... strs) {
                URL url = null;
                try {
                    url = new URL(strs[0]);
                    HttpURLConnection connection = (HttpURLConnection)
                            url.openConnection();
                    connection.setRequestMethod("GET");//试过POST 可能报错
                    connection.setDoInput(true);
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    //实现连接
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        //这里给过去就行了
                        return connection.getInputStream();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                super.onPostExecute(inputStream);
                mBinding.pvView.fromStream(inputStream)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .onPageScroll((page, positionOffset) -> {

                        })
                        .onError(t -> ToastUtils.showToast("error" + t.getMessage()))
                        .enableAnnotationRendering(false)
                        .password(null)
                        .scrollHandle(null)
                        .load();
            }
        }.execute(mPdfUrl);
    }
}
