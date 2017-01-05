package com.yicheng.rpc.codec;

import java.io.IOException;

/**
 * Created by yuer on 2017/1/5.
 */
public enum RpcCodec {
    INSTNACE;
    private Serializer  serialization;

    RpcCodec(){
        serialization =  new JdkSerializer();
    }

    byte[] encode(Object obj) throws IOException {

        return serialization.serialize(obj);
    }

    <T> T decode(byte[] bytes,Class<T> clazz) throws IOException{

        return serialization.deserialize(bytes,clazz);
    }
}


