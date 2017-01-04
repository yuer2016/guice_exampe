package com.yicheng.netty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuer on 2017/1/4.
 */
@ToString
@Getter
@Setter
public class RpcResponse {
    private String requestId;
    private String error;
    private Object result;
}
