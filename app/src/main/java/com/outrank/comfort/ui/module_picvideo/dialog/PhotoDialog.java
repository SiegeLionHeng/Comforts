package com.outrank.comfort.ui.module_picvideo.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;


import com.outrank.comfort.R;
import com.outrank.comfort.ui.module_picvideo.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoDialog extends DialogFragment {

    private int currentPostion = -1;
    private List<String> imageData = new ArrayList<>();
    private ViewPager viewPager;
    private TextView textView;
    private MyPagerAdapter pagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentPostion = bundle.getInt("currentPostion");
            imageData = bundle.getStringArrayList("imageData");
        }
        View view = inflater.inflate(R.layout.dialog_photo, null);
        initView(view);
        initListener();
        return view;
    }


    @SuppressLint("DefaultLocale")
    private void initView(View view) {
        viewPager = view.findViewById(R.id.dialog_photo_vp);
        textView = view.findViewById(R.id.dialog_photo_tv);
        pagerAdapter = new MyPagerAdapter(getActivity(), imageData);
        viewPager.setAdapter(pagerAdapter);

        textView.setText(String.format("%d/%d", currentPostion + 1, imageData.size()));

        viewPager.setCurrentItem(currentPostion, false);

    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onPageSelected(int position) {
                textView.setText(String.format("%d/%d", position + 1, imageData.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pagerAdapter.setCallBack(this::close);
    }

    private void close() {
        this.dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.PhotoDialog);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // dialog进入和消失的动画
        getDialog().getWindow().getAttributes().windowAnimations = R.style.FragmentDialogAnimation;

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setLayout(width, height); // 使dialog充满真个屏幕
            }
        }
    }
}
