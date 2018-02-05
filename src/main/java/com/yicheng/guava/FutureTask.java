package com.yicheng.guava;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Executors;

/**
 * Created by yuer on 2017/6/26.
 */
public class FutureTask {
    private final static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    public static void main(String[] args) {
        ListenableFuture<Boolean> submit = service.submit(() -> true);
        final Boolean[] x;
        x = new Boolean[]{false};
        Futures.addCallback(submit, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                x[0] = result;
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });


    }
}
