package com.outrank.global.utils;

import android.text.TextUtils;

public class BizUtils {

  private static String BASE_PRODUCTION_PHOTO_URL = "https://lingzhu2-0-production-image.oss-cn-beijing.aliyuncs.com/";
  private static String BASE_SHOP_PHOTO_URL = "https://lingzhu2-0-store-image.oss-cn-beijing.aliyuncs.com/";
  private static String BASE_URL_PREFIX = "https://lingzhu2-0-user-selfie.oss-cn-beijing.aliyuncs.com/";
  private static String BASE_BUSINESS_PHOTO_URL = "https://lingzhu2-0-icon.oss-cn-beijing.aliyuncs.com/";
  private static String BASE_NEWSFEED_PHOTO_URL = "https://lingzhu2-0-newsfeed-image.oss-cn-beijing.aliyuncs.com/";
  private static String BASE_REVIEW_PHOTO_URL = "https://lingzhu2-0-review-image.oss-cn-beijing.aliyuncs.com/";
  private static String BASE_SHOP_ICON_URL = "https://lingzhu2-0-icon.oss-cn-beijing.aliyuncs.com/";

    private static String HTTP = "http";
    private static String GIF = ".gif";
    private static String PNG = ".png";
    private static String THUMB = "?x-oss-process=style/_small";

    /**
     * 用户头像url，(gif动图)
     */
    public static String buildPhotoUrl(String rtag) {
        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_URL_PREFIX + rtag + GIF;
    }

    /**
     * 商品图片url
     */
    public static String buildProductionPhotoUrl(String rtag) {
        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_PRODUCTION_PHOTO_URL + rtag + PNG;
    }

    /**
     * 商铺图片url
     */
    public static String buildShopPhotoUrl(String rtag) {
        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_SHOP_PHOTO_URL + rtag + PNG;
    }

    /**
     * 商业推荐图片url
     */
    public static String buildBusinessPhotoUrl(String rtag) {

        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_BUSINESS_PHOTO_URL + rtag + PNG;
    }

    /**
     * 动态图片url
     */
    public static String buildNewsFeedPhotoUrl(String rtag) {

        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_NEWSFEED_PHOTO_URL + rtag + PNG;
    }

    /**
     * 审核图片url
     */
    public static String buildReviewPhotoUrl(String rtag) {

        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_REVIEW_PHOTO_URL + rtag + PNG;
    }


    /**
     * 用户头像缩略图url
     */
    public static String buildSelfieThumbUrl(String rtag) {

        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_URL_PREFIX + rtag + GIF + THUMB;
    }

    /**
     * 商品图片缩略图
     */
    public static String buildProductThumbUrl(String rtag) {

        if (TextUtils.isEmpty(rtag)) {
            return "";
        }
        if (rtag.startsWith(HTTP)) {
            return rtag;
        }
        return BASE_PRODUCTION_PHOTO_URL + rtag + PNG + THUMB;
    }


}
