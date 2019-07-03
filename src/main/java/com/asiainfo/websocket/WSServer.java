package com.asiainfo.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: websocket netty
 * @author: qml
 * @create: 2019-07-03 10:09
 */
public class WSServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup,subGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new WebServerInitializer());
            ChannelFuture future = server.bind(8088).sync();
            future.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
