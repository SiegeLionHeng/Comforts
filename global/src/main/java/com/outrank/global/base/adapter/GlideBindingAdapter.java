package com.outrank.global.base.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.outrank.global.R;
import com.outrank.global.utils.BizUtils;
import com.outrank.global.utils.DensityUtils;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.glide.GlideCircleTransform;

public class GlideBindingAdapter {
    private static final String TAG = "GlideBindingAdapter";
    private static final int default_round = DensityUtils.dp(4);   //默认矩形圆角角度,单位:dp
    private static final float default_border_width = 2; //默认圆形边框粗度,单位dp
    private static Drawable defaultProductDrawable = Res.drawable(R.drawable.default_product);

    /**
     * 加载矩形圆角图片(用Glide)
     */
    @BindingAdapter(value = {"roundImageUrl", "round_angle"}, requireAll = false)
    public static void setRoundImageUrl(ImageView imageView, String url, float angle) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.default_product)
                .error(R.drawable.default_product)
                .transforms(new CenterCrop(), new RoundedCorners(angle > 0 ? DensityUtils.dp(angle) : default_round));
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(String.format("加载图片错误：setRoundImageUrl()/%s/%s", url, e.getMessage()));
        }
    }

    /**
     * 加载圆形图片(用Glide)
     *
     * @param imageView        图片对象
     * @param circle_image_url 图片url
     */
    @BindingAdapter({"circle_image_url"})
    public static void setCircleImageUrl(ImageView imageView, String circle_image_url) {
        setCircleImageUrl(imageView, circle_image_url, 0, Color.WHITE);
    }


    /**
     * 加载圆形图片(用Glide)
     *
     * @param imageView        图片对象
     * @param circle_image_url 图片url
     * @param border_width     边框粗度
     */
    @BindingAdapter({"circle_image_url", "border_width", "border_color"})
    public static void setCircleImageUrl(ImageView imageView, String circle_image_url, float border_width, @ColorInt int border_color) {
        Context context = imageView.getContext();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideCircleTransform(border_width, border_color))
                .placeholder(R.drawable.ic_replace_header_bg)
                .error(R.drawable.ic_replace_header_bg)
                .priority(Priority.HIGH);
        //.skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        try {
            Glide.with(context)
                    .load(circle_image_url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(String.format("加载图片错误：setCircleImageUrl()/%s/%s", circle_image_url, e.getMessage()));
        }
    }
}
