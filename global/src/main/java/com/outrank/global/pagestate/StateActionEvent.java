package com.outrank.global.pagestate;

/**
 * Created by Gabriel on 2020/5/31
 * Email 17600284843@163.com
 * Description: 布局回调状态
 */
public interface StateActionEvent {

    interface LoadState {
        String INSTANCE = "load_state";
    }

    interface ErrorState {
        String INSTANCE = "error_state";
    }

    interface TimeoutState {
        String INSTANCE = "timeout_state";
    }

    interface SuccessState {
        String INSTANCE = "success_state";
    }

    interface EmptyState {
        String INSTANCE = "empty_state";
    }
}
