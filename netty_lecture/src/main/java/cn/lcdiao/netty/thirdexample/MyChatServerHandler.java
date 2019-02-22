package cn.lcdiao.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by diao on 2019/2/22
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //保存所有已经与服务端建立连接的channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();

        channelGroup.forEach(ch -> {
            if(channel != ch){
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息: " + s + "\n");
            } else {
                ch.writeAndFlush("【自己】" + s + "\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //每次客户端与服务端建立好连接就代表有一个Channel对象创建
        Channel channel = ctx.channel();

        //向所有Channel对象发送消息
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + " 加入\n");

        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //获取断开连接的Channel对象
        Channel channel = ctx.channel();

        //向所有Channel对象发送消息
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + " 离开\n");

        //使用与否都一样，netty会自动调用该方法，移除断开连接的Channel对象
        //channelGroup.remove(channel);
        System.out.println(channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
