package com.outrank.global.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Gabriel on 2019/12/9.
 * Email 17600284843@163.com
 * Description: 遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
 */
public class RetryWithDelayUtils implements
        Function<Observable<Throwable>, ObservableSource<?>> {

    public final String TAG = this.getClass().getSimpleName();
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelayUtils(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
                .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (++retryCount <= maxRetries) {
                        // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                        Log.d(TAG, "Observable get error, it will try after " + retryDelaySecond
                                + " second, retry count " + retryCount);
                        return Observable.timer(retryDelaySecond,
                                TimeUnit.SECONDS);
                    }
                    // Max retries hit. Just pass the error along.
                    return Observable.error(throwable);
                });
    }
}
