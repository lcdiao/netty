package cn.lcdiao.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by diao on 2019/2/26
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //空闲状态检测处理器：在一定时间内没有进行读(5秒)、写（7秒）、读写（3秒）操作就会触发（心跳检测）
        pipeline.addLast(new IdleStateHandler(5,7,3, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}