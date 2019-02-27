package cn.lcdiao.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by diao on 2019/2/27
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //http解码器
        pipeline.addLast(new HttpServerCodec());
        //以块的方式去写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //对http消息进行一个聚合（通常http请求之类的会被分段，聚合后形成一个完整的http请求）
        pipeline.addLast(new HttpObjectAggregator(8192));
        //websocket的服务器协议处理器
        //ws://server:port/context_path     --->    本机:ws://localhost:9999/ws   下面的/ws指的是context_path
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
