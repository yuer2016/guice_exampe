package com.yicheng.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by yuer on 2017/1/4.
 */
public class RPCProxy {
    public static <T> T create( Object target) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setRequestId(UUID.randomUUID().toString());
                request.setClassName(target.getClass().getName());
                request.setMethodName(method.getName());
                request.setParameters(args);
                request.setParameterTypes(method.getParameterTypes());
                ClientHandler clientHandler = new ClientHandler();
                EventLoopGroup workGroup = new NioEventLoopGroup();
                try {
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(workGroup)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline()
                                            .addLast(new RpcEncoder(RpcRequest.class))
                                            .addLast(new LengthFieldBasedFrameDecoder(65536, 0,
                                                    4, 0, 0))
                                            .addLast(new RpcDecoder(RpcResponse.class))
                                            .addLast(clientHandler);
                                }
                            });
                    ChannelFuture connect = bootstrap.connect("127.0.0.1", 8080).sync();
                    connect.channel().writeAndFlush(request);
                    connect.channel().closeFuture().sync();
                } finally {
                    workGroup.shutdownGracefully();
                }

                return clientHandler.getResponse();
            }
        });
    }
}
