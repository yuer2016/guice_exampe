package com.yicheng.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Created by yuer on 2017/1/5.
 */
@Slf4j
public class AcceptorHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private NettyRpcAcceptor acceptor;

    public AcceptorHandler(NettyRpcAcceptor acceptor) {
        this.acceptor = acceptor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        Object service = acceptor.getExporter().findService(rpcRequest.getClassName());
        if(service != null){
            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setRequestId(rpcRequest.getRequestId());
            try{
                Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(service, rpcRequest.getParameters());
                rpcResponse.setResult(result);
            }catch (Exception e){
                rpcResponse.setError(e);
                log.error(e.getMessage());
            }
            channelHandlerContext.writeAndFlush(rpcResponse);
        }
    }
}
