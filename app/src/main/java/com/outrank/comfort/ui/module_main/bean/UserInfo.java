package com.outrank.comfort.ui.module_main.bean;

import android.graphics.drawable.Drawable;

import com.outrank.comfort.R;
import com.outrank.global.utils.Res;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2020/6/6.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class UserInfo implements Serializable {

    /**
     * nickname : 消愁918469
     * uid : 100001
     * signature :
     * praise : 1.2亿
     * attention : 0
     * fans : 1346万
     * icon_bg : /picture/NewThread/07/pic_7 (5).jpg
     * icon_header : /picture/NewThread/07/pic_7 (5).jpg
     * is_vip : 1
     * vip_level : 7
     * ad_list : [{"ad_title":"广告位","ad_img":"/picture/user_center/ic_advertising_space.png"}]
     */

    private String nickname;
    private String uid;
    private String signature;
    private String praise;
    private String attention;
    private String fans;
    private String icon_bg;
    private String icon_header;
    private String is_vip;
    private String vip_level;
    private String sex; // 1:男；2:女
    private List<AdListBean> ad_list;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getIcon_bg() {
        return icon_bg;
    }

    public void setIcon_bg(String icon_bg) {
        this.icon_bg = icon_bg;
    }

    public String getIcon_header() {
        return icon_header;
    }

    public void setIcon_header(String icon_header) {
        this.icon_header = icon_header;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getVip_level() {
        return vip_level;
    }

    public void setVip_level(String vip_level) {
        this.vip_level = vip_level;
    }

    public List<AdListBean> getAd_list() {
        return ad_list;
    }

    public void setAd_list(List<AdListBean> ad_list) {
        this.ad_list = ad_list;
    }

    public static class AdListBean {
        /**
         * ad_title : 广告位
         * ad_img : /picture/user_center/ic_advertising_space.png
         */

        private String ad_title;
        private String ad_img;

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getAd_img() {
            return ad_img;
        }

        public void setAd_img(String ad_img) {
            this.ad_img = ad_img;
        }
    }
}
