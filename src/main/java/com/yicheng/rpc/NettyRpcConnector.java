package com.yicheng.rpc;

import com.yicheng.rpc.codec.ConnectorHandler;
import com.yicheng.rpc.codec.RpcDecode;
import com.yicheng.rpc.codec.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by yuer on 2017/1/5.
 */
@Slf4j
public class NettyRpcConnector implements RpcConnector {
    private String host;
    private int prot;
    private Channel channel;
    private EventLoopGroup eventLoopGroup;
    private ConnectorHandler handler;
    @SneakyThrows
    private void init() {
        eventLoopGroup = new NioEventLoopGroup();
        handler = new ConnectorHandler();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new RpcEncoder(),
                                new RpcDecode(RpcResponse.class),
                                handler
                        );
                    }
                });
        ChannelFuture sync = bootstrap.connect(host, prot).sync();
        channel = sync.channel();
    }

    @SneakyThrows
    @Override
    public RpcResponse invoke(RpcRequest requst) throws IOException {
        channel.writeAndFlush(requst);
        return handler.getResponse();
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setport(int port) {
        this.prot = port;
    }

    @Override
    public void start() throws IOException {
        init();
    }

    @Override
    public void stop() throws IOException {
        eventLoopGroup.shutdownGracefully();
    }
}
