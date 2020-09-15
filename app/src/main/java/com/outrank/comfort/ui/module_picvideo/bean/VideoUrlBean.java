package com.outrank.comfort.ui.module_picvideo.bean;

/**
 * Created by Administrator on 2020/6/7.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class VideoUrlBean {

    /**
     * video_url : /video/mp4/video (%d).mp4
     * video_title : video_%d
     * video_size : 16
     */

    private String video_url;
    private String title;
    private int video_size;
    private long length;
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }


    public int getVideo_size() {
        return video_size;
    }

    public void setVideo_size(int video_size) {
        this.video_size = video_size;
    }
}
