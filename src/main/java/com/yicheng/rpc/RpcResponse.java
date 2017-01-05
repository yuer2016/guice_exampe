package com.yicheng.rpc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by yuer on 2017/1/5.
 */
@Getter
@Setter
public class RpcResponse implements Serializable{
    private String requestId;
    private Throwable error;
    private Object result;
}
