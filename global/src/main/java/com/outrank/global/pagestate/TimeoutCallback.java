package com.outrank.global.pagestate;

import com.kingja.loadsir.callback.Callback;
import com.outrank.global.R;

/**
 * Created by Gabriel on 2020/5/31
 * Email 17600284843@163.com
 * Description: Timeout布局回调
 */
public class TimeoutCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_timeout;
    }
}
