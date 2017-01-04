package com.yicheng.netty;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yuer on 2017/1/4.
 */
@Getter
@Setter
@ToString
public class RpcRequest {
    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
