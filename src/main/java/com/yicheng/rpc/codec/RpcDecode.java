package com.yicheng.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by yuer on 2017/1/5.
 */
public class RpcDecode extends LengthFieldBasedFrameDecoder {
    private Class<?> clazz;
    private RpcCodec  rpcCodec;
    public RpcDecode(Class<?> clazz) {
        super(65536, 0, 4,0,4);
        this.clazz = clazz;
        rpcCodec = RpcCodec.INSTNACE;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        if (decode == null){
            return null;
        }
        int bytesLength = decode.readableBytes();
        byte[] bytes = new byte[bytesLength];
        decode.readBytes(bytes);
        return rpcCodec.decode(bytes,clazz);
    }

}
