package com.yicheng.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.Serializable;

/**
 * Created by yuer on 2017/1/5.
 */
public class RpcEncoder extends MessageToByteEncoder<Serializable> {
    private RpcCodec codec;

    public RpcEncoder() {
        this.codec = RpcCodec.INSTNACE;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf) throws Exception {
        byte[] bytes = codec.encode(serializable);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
