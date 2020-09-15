package com.outrank.global.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.outrank.global.R;
import com.outrank.global.annotation.LeftMode;
import com.outrank.global.annotation.RightType;
import com.outrank.global.utils.ColorUtil;
import com.outrank.global.utils.ScreenUtils;
import com.outrank.global.utils.Tools;


public class Topbar extends FrameLayout implements View.OnClickListener {


    private Context context;
    private View statusBarView;
    private RelativeLayout rl_title;
    private LinearLayout ll_leftOption;
    private LinearLayout ll_rightOption;
    private LinearLayout ll_title;

    private ImageView iv_left;
    private TextView tv_left;

    private TextView tv_title;
    private ImageView iv_arrow_down;

    private ImageView iv_right;
    private TextView tv_right;
    private View topbarDivider;

    public static final int TOPBAR_TYPE_NONE = 0;
    public static final int TOPBAR_TYPE_IMG = 1;
    public static final int TOPBAR_TYPE_TEXT = 2;
    public static final int TOPBAR_TYPE_ANIM = 3;
    public static final int TOPBAR_TYPE_IMG_TEXT = 4;


    public static final int TOPBAR_LEFT_MODE_BACK = 0;
    public static final int TOPBAR_LEFT_MODE_MORE = 1;

    public static final int DEFAULT_TOPBAR_TEXT_COLOR = R.color.white;
    public static final int DEFAULT_LEFT_TYPE = TOPBAR_TYPE_NONE;
    private static final int DEFAULT_LEFT_IMAGE = R.drawable.back_white;

    private static final int DEFAULT_TITLE_TYPE = TOPBAR_TYPE_TEXT;

    public static final int DEFAULT_RIGHT_TYPE = TOPBAR_TYPE_NONE;
    private static final int DEFAULT_RIGHT_IMAGE = R.drawable.back_white;

    private static final int DEFAULT_TOPBAR_LEFT_MODE = TOPBAR_LEFT_MODE_BACK;

    private static final float DEFULT_LEFT_TEXT_SIZE = 16;
    private static final float DEFULT_TITLE_TEXT_SIZE = 18;
    private static final float DEFULT_RIGHT_TEXT_SIZE = 16;

    private static final int DEFULT_LEFT_TEXT_COLOR = R.color.white;
    private static final int DEFULT_TITLE_TEXT_COLOR = R.color.white;
    private static final int DEFULT_RIGHT_TEXT_COLOR = R.color.white;


    private int topbarTextColor;

    private int topbarLeftTextColor;
    private int topbarRightTextColor;
    private int leftType;
    private int leftImage;
    private String leftText;
    private int leftMode;

    private String titleText;
    private int titleImage;
    private int titleType;

    private int rightType;
    private int rightImage;
    private String rightText;

    private int topbarBgColor;
    private Drawable topbarBgDrawable;
    private int statusBarBg;
    private boolean isExtendStatusBar;      //是否将topbar延伸至状态栏
    private boolean statusBarAsTopbarBg;    //状态栏是否采用与topbar相同的背景图片


    private boolean isShowTopbarDivider;    //是否显示标题栏底部的分割线
    private int topbarDividerColor;
    public static final int DEFAULT_TOPBAR_DIVIDER_COLOR = R.color.white;

    private OnClickTopbarLeftListener onClickTopbarLeftListener;
    private OnClickTopbarTitleListener onClickTopbarTitleListener;
    private OnClickTopbarRightListener onClickTopbarRightListener;
    private OnClickBackKeyListener onClickBackKeyListener;

    public interface OnClickBackKeyListener {
        void onClickBackKey();
    }

    public interface OnClickTopbarLeftListener {
        void onClickTopbarLeft();
    }

    public interface OnClickTopbarTitleListener {
        void onClickTopbarTitle();
    }

    public abstract static class OnOpenCloseTopbarTitleListener implements OnClickTopbarTitleListener {
        private boolean isOpened;

        @Override
        public final void onClickTopbarTitle() {
            if (!isOpened) {
                isOpened = true;
                onOpen();
            } else {
                isOpened = false;
                onClose();
            }
        }

        public abstract void onOpen();

        public abstract void onClose();

        public boolean isOpened() {
            return isOpened;
        }

        public void setOpened(boolean opened) {
            isOpened = opened;
        }
    }

    public interface OnClickTopbarRightListener {
        void onClickTopbarRight();
    }


    public Topbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.layout_topbar, this);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        isExtendStatusBar = array.getBoolean(R.styleable.Topbar_topbarIsExtendStatusBar, false);
        statusBarAsTopbarBg = array.getBoolean(R.styleable.Topbar_statusBarAsTopbarBg, false);

        topbarBgDrawable = array.getDrawable(R.styleable.Topbar_topbarBgDrawable);

        topbarBgColor = array.getResourceId(R.styleable.Topbar_topbarBgColor, context.getResources().getColor(R.color.topbar_background));

        statusBarBg = array.getResourceId(R.styleable.Topbar_statusBarBg, context.getResources().getColor(R.color.topbar_background));

        topbarTextColor = array.getColor(R.styleable.Topbar_topbarTextColor, context.getResources().getColor(DEFAULT_TOPBAR_TEXT_COLOR));
        topbarLeftTextColor = array.getColor(R.styleable.Topbar_topbarLeftTextColor, context.getResources().getColor(DEFAULT_TOPBAR_TEXT_COLOR));
        topbarRightTextColor = array.getColor(R.styleable.Topbar_topbarRightTextColor, context.getResources().getColor(DEFAULT_TOPBAR_TEXT_COLOR));

        leftType = array.getInt(R.styleable.Topbar_topbarLeftType, DEFAULT_LEFT_TYPE);
        leftImage = array.getResourceId(R.styleable.Topbar_topbarLeftImage, DEFAULT_LEFT_IMAGE);
        leftText = array.getString(R.styleable.Topbar_topbarLeftText);
        leftMode = array.getInt(R.styleable.Topbar_topbarLeftMode, DEFAULT_TOPBAR_LEFT_MODE);

        titleType = array.getInt(R.styleable.Topbar_topbarTitleType, DEFAULT_TITLE_TYPE);
        titleImage = array.getResourceId(R.styleable.Topbar_topbarTitleImage, 0);//默认中间标题栏没有图片
        titleText = array.getString(R.styleable.Topbar_topbarTitleText);

        rightType = array.getInt(R.styleable.Topbar_topbarRightType, DEFAULT_RIGHT_TYPE);
        rightImage = array.getResourceId(R.styleable.Topbar_topbarRightImage, DEFAULT_RIGHT_IMAGE);
        rightText = array.getString(R.styleable.Topbar_topbarRightText);

        isShowTopbarDivider = array.getBoolean(R.styleable.Topbar_isShowTopbarDivider, true);
        topbarDividerColor = array.getColor(R.styleable.Topbar_topbarDividerColor, context.getResources().getColor(DEFAULT_TOPBAR_DIVIDER_COLOR));


        initViews();
        initValues();
        setOnClickListeners();

        array.recycle();
    }

    public void initViews() {
        statusBarView = findViewById(R.id.statusBarView);
        rl_title = findViewById(R.id.rl_title);

        ll_leftOption = findViewById(R.id.ll_leftOption);
        ll_title = findViewById(R.id.ll_title);
        ll_rightOption = findViewById(R.id.ll_rightOption);

        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);

        tv_title = findViewById(R.id.tv_title);
        //标题栏长度最长为屏幕宽度的60%，防止遮挡左右按钮
        tv_title.setMaxWidth((int) (Tools.getScreenWidth() * 0.6));

        iv_arrow_down = findViewById(R.id.iv_arrow_down);
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        topbarDivider = findViewById(R.id.topbarDivider);

    }

    private void initValues() {
        controlStatusBar_titleBar();
        controlLeftWight();
        controlTitleWight();
        controlRightWight();

        topbarDivider.setVisibility(isShowTopbarDivider ? VISIBLE : GONE);
        topbarDivider.setBackgroundColor(topbarDividerColor);
    }

    private void controlStatusBar_titleBar() {
        if (isExtendStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏与标题栏背景融为一体
            /*statusBarView.getLayoutParams().height = ScreenUtils.getStatusHeight(context);

            if (statusBarAsTopbarBg) {
                if(topbarBgDrawable!=null){
                    //1.如果有topbarBgDrawable,则优先使用topbarBgDrawable
                    statusBarView.setBackground(topbarBgDrawable);
                }else{
                    //2.否则使用topbarBgColor
                    statusBarView.setBackgroundResource(topbarBgColor);
                }

                rl_title.setBackgroundColor(Color.TRANSPARENT);
                setBackgroundResource(topbarBg);
            } else {
                statusBarView.setBackgroundResource(statusBarBg);
            }

            rl_title.setBackgroundColor(Color.TRANSPARENT);
            setBackgroundResource(topbarBgColor);*/

            //状态栏与标题栏背景融为一体
            statusBarView.getLayoutParams().height = ScreenUtils.getStatusHeight();

            if (statusBarAsTopbarBg) {
                if (topbarBgDrawable != null) {
                    statusBarView.setBackground(topbarBgDrawable);
                } else {
                    statusBarView.setBackgroundResource(topbarBgColor);
                }
            } else {
                statusBarView.setBackgroundResource(statusBarBg);
            }

            rl_title.setBackgroundColor(Color.TRANSPARENT);

            if (topbarBgDrawable != null) {
                setBackground(topbarBgDrawable);
            } else {
                setBackgroundResource(topbarBgColor);
            }
        } else {
            statusBarView.setVisibility(GONE);
            rl_title.setBackgroundResource(topbarBgColor);
        }
    }

    private void controlLeftWight() {
        switch (leftType) {
            case TOPBAR_TYPE_IMG:
                tv_left.setVisibility(GONE);
                iv_left.setImageResource(leftImage);
                break;
            case TOPBAR_TYPE_TEXT:
                iv_left.setVisibility(GONE);
                tv_left.setText(leftText);
                tv_left.setTextColor(topbarLeftTextColor);
                tv_left.setVisibility(VISIBLE);
                break;
            case TOPBAR_TYPE_IMG_TEXT:
                iv_left.setVisibility(VISIBLE);
                iv_left.setImageResource(leftImage);
                tv_left.setVisibility(VISIBLE);
                tv_left.setText(leftText);
                tv_left.setTextColor(topbarLeftTextColor);
                ll_leftOption.setVisibility(VISIBLE);
                break;
            default:
                ll_leftOption.setVisibility(GONE);
        }
    }

    private void controlTitleWight() {
        switch (titleType) {
            case TOPBAR_TYPE_IMG:
                tv_title.setVisibility(GONE);
                iv_arrow_down.setVisibility(VISIBLE);
                iv_arrow_down.setImageResource(titleImage);
                break;
            case TOPBAR_TYPE_TEXT:
                tv_title.setTextColor(topbarTextColor);
                tv_title.setText(titleText);
                tv_title.setVisibility(VISIBLE);
                iv_arrow_down.setVisibility(GONE);
                break;
            case TOPBAR_TYPE_ANIM:
                tv_title.setTextColor(topbarTextColor);
                tv_title.setText(titleText);
                tv_title.setVisibility(VISIBLE);
                iv_arrow_down.setVisibility(VISIBLE);
                break;
        }
    }


    private void controlRightWight() {
        switch (rightType) {
            case TOPBAR_TYPE_IMG:
                ll_rightOption.setVisibility(VISIBLE);
                tv_right.setVisibility(GONE);
                iv_right.setImageResource(rightImage);
                iv_right.setVisibility(VISIBLE);
                break;
            case TOPBAR_TYPE_TEXT:
                ll_rightOption.setVisibility(VISIBLE);
                iv_right.setVisibility(GONE);
                tv_right.setText(rightText);
                tv_right.setVisibility(VISIBLE);
                tv_left.setTextColor(topbarLeftTextColor);
                tv_right.setTextColor(topbarRightTextColor);
                break;

            case TOPBAR_TYPE_IMG_TEXT:
                iv_right.setVisibility(VISIBLE);
                iv_right.setImageResource(rightImage);
                tv_right.setVisibility(VISIBLE);
                tv_right.setText(rightText);
                tv_right.setTextColor(topbarRightTextColor);
                ll_rightOption.setVisibility(VISIBLE);
                break;

            default:
                ll_rightOption.setVisibility(GONE);
        }
    }


    int clickTime;


    private void setOnClickListeners() {

        tv_title.setOnClickListener(view -> clickTime++);
        tv_title.setOnLongClickListener(view -> {

            if (clickTime == 5) {
                try {
                    Context context = view.getContext();
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        Toast.makeText(activity, activity.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }
            return false;
        });

        iv_left.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        ll_title.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == iv_left || v == tv_left) {
            switch (leftMode) {
                case TOPBAR_LEFT_MODE_MORE:
                    if (onClickTopbarLeftListener != null) {
                        onClickTopbarLeftListener.onClickTopbarLeft();
                    }
                    break;
                default:
                    try {
                        Activity activity = (Activity) this.context;
                        if (onClickBackKeyListener != null) {
                            onClickBackKeyListener.onClickBackKey();
                        }
                        activity.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        } else if (v == iv_right || v == tv_right) {
            if (onClickTopbarRightListener != null) {
                onClickTopbarRightListener.onClickTopbarRight();
            }
        } else if (v == ll_title) {
            if (onClickTopbarTitleListener != null) {
                onClickTopbarTitleListener.onClickTopbarTitle();
            }
        }
    }

    public void openTitleIcon() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_arrow_down, "rotation", -180);
        objectAnimator.setDuration(getResources().getInteger(R.integer.topbar_titile_anim_duration));
        objectAnimator.start();
    }

    public void closeTitleIcon() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_arrow_down, "rotation", 0);
        objectAnimator.setDuration(getResources().getInteger(R.integer.topbar_titile_anim_duration));
        objectAnimator.start();
    }


    /*==========================set方法-begin==========================*/
    public void setOnClickTopbarLeftListener(OnClickTopbarLeftListener onClickTopbarLeftListener) {
        this.onClickTopbarLeftListener = onClickTopbarLeftListener;
    }

    public void setOnClickTopbarTitleListener(OnClickTopbarTitleListener onClickTopbarTitleListener) {
        this.onClickTopbarTitleListener = onClickTopbarTitleListener;
    }

    public void setOnClickTopbarRightListener(OnClickTopbarRightListener onClickTopbarRightListener) {
        this.onClickTopbarRightListener = onClickTopbarRightListener;
    }

    public void setTopbarTitleText(String titleText) {
        this.titleText = titleText;
        this.tv_title.setText(titleText);
    }

    public void setTopbarTitleTextColor(@ColorInt int color) {
        this.topbarTextColor = color;
        tv_title.setTextColor(color);
    }

    public void setTopbarLeftTextColor(@ColorInt int color) {
        this.topbarTextColor = color;
        tv_left.setTextColor(color);
    }


    public void setTopbarBackgroundResourceId(int resid) {
        topbarBgColor = context.getResources().getColor(resid);
        controlStatusBar_titleBar();
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
        controlLeftWight();
    }

    public int getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(int leftImage) {
        this.leftImage = leftImage;
        iv_left.setImageResource(leftImage);
    }

    public void setLeftImageDrawable(Drawable drawable) {
        this.leftImage = leftImage;
        iv_left.setImageDrawable(drawable);
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        tv_left.setText(leftText);
    }

    public int getLeftMode() {
        return leftMode;
    }

    public void setLeftMode(@LeftMode int leftMode) {
        this.leftMode = leftMode;
    }

    public String getTitleText() {
        return titleText;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(@RightType int rightType) {
        this.rightType = rightType;
        controlRightWight();
    }

    public int getRightImage() {
        return rightImage;
    }

    public void setRightImage(int rightImage) {
        this.rightImage = rightImage;
        setRightType(TOPBAR_TYPE_IMG);
        controlRightWight();
    }

    public void setRightImgDrawable(VectorDrawableCompat drawableCompat) {
        if (rightType == TOPBAR_TYPE_IMG) {
            iv_right.setImageDrawable(drawableCompat);
        }
    }


    public void setStatusBarBackgroundColor(int color) {
        statusBarView.setBackgroundColor(color);
    }


    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        setRightType(TOPBAR_TYPE_TEXT);
        controlRightWight();
    }


    public void setRightTextColor(int color) {
        tv_right.setTextColor(color);
        setRightType(TOPBAR_TYPE_TEXT);
        controlRightWight();
    }

    public ImageView getIv_title() {
        return iv_arrow_down;
    }

    public ImageView getIv_right() {
        return iv_right;
    }

    public TextView getTv_right() {
        return tv_right;
    }

    public TextView getTv_Title() {
        return tv_title;
    }

    public ImageView getIv_left() {
        return iv_left;
    }

    public void setOnClickBackKeyListener(OnClickBackKeyListener onClickBackKeyListener) {
        this.onClickBackKeyListener = onClickBackKeyListener;
    }

    public View getTopbarDivider() {
        return topbarDivider;
    }

    public LinearLayout getLl_rightOption() {
        return ll_rightOption;
    }

    /*==========================set方法-end==========================*/
}
