package com.asiainfo.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * @description:
 * @author: qml
 * @create: 2019-07-03 10:41
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所以客户端的channel
    private static ChannelGroup clients=
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传来的消息
        String content = msg.text();
        System.out.println("接收的数据："+content);

//        for(Channel channel:clients){
//            channel.writeAndFlush(new TextWebSocketFrame(
//                    "[服务器在]" + System.currentTimeMillis()
//						+ "接受到消息, 消息为：" + content
//            ));
//        }

        //下面的这个方法，和上面的for循环一致
        clients.writeAndFlush(new TextWebSocketFrame(
                "[服务器在]"+System.currentTimeMillis()
                +"接收消息，消息为："+content
        ));
    }


    /**
     * 当客户端连接服务端之后（打开链接）
     * 获取客户端的额channle，并且放到channelGroup中去进行管理
     *
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved，channelGroup会自动移除对应客户端的channel
        clients.remove(ctx.channel());
        System.out.println("客户端端口，channel对应的长id为："+ctx.channel().id().asLongText());
        System.out.println("客户端端口，channel对应的短id为："+ctx.channel().id().asShortText());
    }


}
