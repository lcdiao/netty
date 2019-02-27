package cn.lcdiao.netty.fifthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * Created by diao on 2019/2/27
 */                                                     //泛型是真正要处理的对象的类型    TextWebSocketFrame:处理文本帧的类
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("收到消息: "+textWebSocketFrame.text());
        //普通字符串是发不出去的
        channelHandlerContext.write(new TextWebSocketFrame("服务器时间: " + LocalDateTime.now()));
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame("服务器地址: " + channelHandlerContext.channel().remoteAddress()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded： " + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved: " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        ctx.close();
    }
}
