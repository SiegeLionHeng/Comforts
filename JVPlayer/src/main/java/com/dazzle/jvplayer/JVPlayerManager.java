package com.dazzle.jvplayer;

/**
 * Created by Gabriel on 2019/3/27.
 * Email 17600284843@163.com
 * Description:视频播放器管理器.
 */
public class JVPlayerManager {

    private JVPlayer mVideoPlayer;

    private JVPlayerManager() {
    }

    private static JVPlayerManager sInstance;

    public static synchronized JVPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new JVPlayerManager();
        }
        return sInstance;
    }

    public JVPlayer getCurrentJVPlayer() {
        return mVideoPlayer;
    }

    public void setCurrentJVPlayer(JVPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseJVPlayer();
            mVideoPlayer = videoPlayer;
        }
    }

    public void suspendJVPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying())) {
            mVideoPlayer.pause();
        }
    }

    public void resumeJVPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused())) {
            mVideoPlayer.restart();
        }
    }

    public void releaseJVPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }

    public boolean onBackPressd() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }
}
