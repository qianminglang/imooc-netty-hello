package com.asiainfo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: 实现客户端发送一个请求，服务端会返回 hello netty
 * @author: qml
 * @create: 2019-07-01 21:27
 */
public class HelloServer {
    public static void main(String[] args) throws Exception {
        //定义一对线程组
        //主线程组，用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //从线程组，老板线程组会把任务丢给他，让手下线程组去做任务
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //netty服务器创建，ServerBootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)   //设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置nio的双向通道
                    .childHandler(new HelloServerInitializer());//子处理器，用于处理workGroup

            //启动server，并设置8088为启动的端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }
}
