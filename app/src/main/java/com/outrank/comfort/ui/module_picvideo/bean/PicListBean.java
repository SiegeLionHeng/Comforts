package com.outrank.comfort.ui.module_picvideo.bean;

import java.io.Serializable;

public class PicListBean implements Serializable {

    /**
     * detailTitle : /picture/NewThread/00/pic_0 (%d)
     * imgUrl : /picture/NewThread/00/pic_0 (1).jpg
     * detailSize : 29
     */

    private String detailTitle;
    private String imgUrl;
    private int detailSize;

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getDetailSize() {
        return detailSize;
    }

    public void setDetailSize(int detailSize) {
        this.detailSize = detailSize;
    }
}
