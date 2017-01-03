package com.yicheng.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Created by yuer on 2017/1/3.
 */
@Slf4j
public class Service {
    private final int port;

    public Service(int port){
        this.port = port;
    }
    @SneakyThrows
    public void start(){
        EventLoopGroup serviceEventGroup = new NioEventLoopGroup();
        EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(serviceEventGroup,workEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServiceHandler());
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind().sync();
            sync.channel().closeFuture().sync();
        } finally {
          serviceEventGroup.shutdownGracefully().sync();
          workEventLoopGroup.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) {
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 8080;
        }
        new Service(port).start();
    }

}
