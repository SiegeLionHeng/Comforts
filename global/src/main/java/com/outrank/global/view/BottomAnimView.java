package com.outrank.global.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outrank.global.R;
import com.outrank.global.global.Const;
import com.outrank.global.utils.ColorUtil;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.Tools;

public class BottomAnimView extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    private int mDefaultImage;
    private String mDefaultText;
    private int mDefaultColor;

    public BottomAnimView(Context context) {
        super(context);
        initView(null);
    }

    public BottomAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_img_text_view, this);
        textView = v.findViewById(R.id.tv_text);
        imageView = v.findViewById(R.id.iv_img);
        //attrs判空
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BottomAnimView);
            mDefaultText = a.getString(R.styleable.BottomAnimView_default_text);
            mDefaultImage = a.getResourceId(R.styleable.BottomAnimView_default_image, R.drawable.ic_square);
            mDefaultColor = a.getColor(R.styleable.BottomAnimView_default_color, Res.color(R.color.color_666));
            imageView.setImageDrawable(ColorUtil.setVectorColor(Const.COLOR_TEXT_666, mDefaultImage));
            textView.setText(TextUtils.isEmpty(mDefaultText) ? "index" : mDefaultText);
            textView.setTextColor(mDefaultColor);
            a.recycle();
        }
    }

    public void setSelectStyle(int position) {
        imageView.setImageDrawable(ColorUtil.setVectorColor(Const.COLOR_PRIMARY, Tools.currentDraw(position)));
        textView.setTextColor(Res.color(R.color.base_blue));
        startAnim();
    }

    public void resert(int position) {
        textView.setTextColor(Res.color(R.color.color_666));
        imageView.setImageDrawable(ColorUtil.setVectorColor(Const.COLOR_TEXT_666, Tools.currentDraw(position)));
    }

    public void startAnim() {
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.8f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.8f, 1.2f, 1f);
        animatorSetsuofang.setDuration(150);
        animatorSetsuofang.setInterpolator(new AccelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();
    }
}
