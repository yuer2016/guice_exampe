package com.yicheng.protos;

import com.yicheng.rpc.RpcResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by yuer on 2017/12/6.
 */
public class GrpcFuture<V> implements Future<V> {
    private volatile RpcResponse response;
    private volatile Exception exception;
    private volatile boolean done;
    private volatile int waiters;

    private boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        long millis = unit.toMillis(timeout);
        long endTime = System.currentTimeMillis() + millis;
        synchronized (this) {
            if (done) return done;
            if (millis <= 0) return done;
            waiters++;
            try {
                while (!done) {
                    wait(millis);
                    if (endTime < System.currentTimeMillis() && !done) {
                        exception = new TimeoutException("request time out");
                        break;
                    }
                }
            } finally {
                waiters--;
            }
        }
        return done;
    }


    Exception getException() {
        return exception;
    }

    RpcResponse getResponse() throws IOException, TimeoutException {
        Exception error = getException();
        if (error != null) {
            if (error instanceof IOException) {
                throw (IOException) error;
            }else if (error instanceof TimeoutException) {
                throw (TimeoutException) error;
            }
        }
        return response;
    }

    void setException(Exception exception) {
        synchronized (this) {
            if (!done) {
                this.exception = exception;
                done = true;
                if (waiters > 0) {
                    notifyAll();
                }
            }

        }
    }

    void setResponse(RpcResponse response) {
        synchronized (this) {
            if (!done) {
                this.response = response;
                done = true;
                if (waiters > 0) {
                    notifyAll();
                }
            }

        }
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        try {
            return get(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            throw new InterruptedException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        await(timeout, unit);
        try {
            RpcResponse rsp = getResponse();
            return (V) rsp.getResult();
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }


}
