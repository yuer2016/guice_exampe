package com.yicheng.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by yuer on 2017/1/4.
 */
public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readLength = byteBuf.readInt();
        byte[] bytes = new byte[readLength];
        byteBuf.readBytes(bytes);
        Object obj = SerializationUtil.deserialize(bytes, genericClass);
        list.add(obj);
    }
}
