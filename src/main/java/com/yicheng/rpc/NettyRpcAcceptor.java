package com.yicheng.rpc;

import com.yicheng.rpc.codec.Exporter;
import com.yicheng.rpc.codec.RpcDecode;
import com.yicheng.rpc.codec.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * Created by yuer on 2017/1/5.
 */
public class NettyRpcAcceptor implements RpcAcceptor{
    private String host;
    private int port;
    @Getter
    @Setter
    private Exporter exporter;
    private NioEventLoopGroup workLoopGroup;
    private NioEventLoopGroup bossLoopGroup;
    @SneakyThrows
    private void init(){
        workLoopGroup = new NioEventLoopGroup();
        bossLoopGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossLoopGroup,workLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new RpcEncoder(),
                                new RpcDecode(RpcResponse.class),
                                new AcceptorHandler(NettyRpcAcceptor.this)
                        );
                    }
                });
        bootstrap.bind(host,port).sync();
    }
    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setport(int port) {
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        this.init();
    }

    @Override
    public void stop() throws IOException {
        bossLoopGroup.shutdownGracefully();
        workLoopGroup.shutdownGracefully();
    }
}
