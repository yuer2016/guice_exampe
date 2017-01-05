package com.yicheng.rpc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by yuer on 2017/1/5.
 */
@Getter
@Setter
public class RpcRequest implements Serializable {
    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
