package com.yicheng.rpc.codec;

import java.io.IOException;

/**
 * Created by yuer on 2017/1/5.
 */
public interface Serializer {

    <T> byte[] serialize(T obj) throws IOException;

    <T>  T deserialize(byte[] bytes,Class<T> clazz) throws IOException;
}
