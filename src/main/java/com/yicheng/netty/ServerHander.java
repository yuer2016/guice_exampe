package com.yicheng.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by yuer on 2017/6/26.
 */
@Slf4j
public class ServerHander extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        int type = in.getByte(0);
        switch (type) {
            case 48:
                ctx.pipeline().addLast(new ServerAHander());
                ChannelFuture future = ctx.writeAndFlush("this is B protocols");
                future.addListener((ChannelFutureListener) f -> {
                    assert f == future;
                    ctx.close();
                });
                break;
            case 49:
                ChannelFuture channelFuture = ctx.writeAndFlush("this is B protocols");
                channelFuture.addListener((ChannelFutureListener) f ->{
                    assert f == channelFuture;
                    ctx.close();
                });
                break;

            default:
                log.error("not support this protocols");
        }

    }
}
