package com.outrank.comfort.ui.module_picvideo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dazzle.jvplayer.JVPlayer;
import com.dazzle.jvplayer.LogUtils;
import com.dazzle.jvplayer.TxJVPlayerController;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ItemVideoBinding;
import com.outrank.comfort.databinding.ItemVideoNewBinding;
import com.outrank.comfort.ui.module_picvideo.bean.VideoUrlBean;
import com.outrank.global.base.adapter.GlideBindingAdapter;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2020/6/7.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class VideoAdapter extends BaseRecyAdapter<VideoUrlBean, ItemVideoNewBinding> {


    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public int itemLayout(ViewGroup parent, int viewType) {
        return R.layout.item_video_new;
    }

    @Override
    protected void onBindView(BaseViewHolder<ItemVideoNewBinding> holder, int position) {
        VideoUrlBean videoBean = mDataBean.get(position);

        JVPlayer jvPlayer = holder.getBinding().jvPlayer;
        jvPlayer.setPlayerType(JVPlayer.TYPE_NATIVE);
        TxJVPlayerController controller = new TxJVPlayerController(mContext);
        jvPlayer.setController(controller);
        //设置数据
        controller.setTitle("COMFORT放映室");
        holder.getBinding().tvTitle.setText(videoBean.getTitle());
//        holder.setText(R.id.tv_title, );
        controller.setLenght(videoBean.getLength());
        GlideBindingAdapter.setRoundImageUrl(controller.imageView(), videoBean.getImageUrl(), 5);
        jvPlayer.setUp(videoBean.getVideo_url(), null);
    }
}
