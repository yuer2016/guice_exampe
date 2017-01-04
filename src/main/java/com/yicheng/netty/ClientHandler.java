package com.yicheng.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuer on 2017/1/4.
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private RpcResponse response;

    public RpcResponse getResponse() {
        return response;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
      log.info(rpcResponse.getRequestId()+":{}",rpcResponse.getResult());
      response = rpcResponse;
    }

}
