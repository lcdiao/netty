package cn.lcdiao.netty.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


/**
 * Created by diao on 2019/2/22
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override       //一旦客户端跟服务端连接，这里就会被调用
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyServerHandler()); //最后一定要提供自己的处理器
        /*
        通常来说,使用ByteToMessageDocoder这个编码器,我们要分别解析出Header,length,body这几个字段.
        而使用LengthFieldBasedFrameDecoder,我们就可以直接接收想要的一部分,相当于在原来的基础上包上了一层,
        有了这层之后,我们可以控制我们每次只要读想读的字段,这对于自定义协议来说十分方便.
        maxFrameLength:     帧的最大长度
        lengthFieldOffset length:       字段偏移的地址
        lengthFieldLength length;字段所占的字节长
        lengthAdjustment: 修改帧数据长度字段中定义的值，可以为负数 因为有时候我们习惯把头部记入长度,若为负数,则说明要推后多少个字段
        initialBytesToStrip: 解析时候跳过多少个长度
        failFast; 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异


        StringEncoder和StringDecoder
        通常我们也习惯将编码（Encode）称为序列化（serialization），它将对象序列化为字节数组，用于网络传输、数据持久化或者其它用途。
        反之，解码（Decode）/反序列化（deserialization）把从网络、磁盘等读取的字节数组还原成原始对象（通常是原始对象的拷贝），
        以方便后续的业务逻辑操作。
         */
    }
}
