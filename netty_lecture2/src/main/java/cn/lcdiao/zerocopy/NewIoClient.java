package cn.lcdiao.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by diao on 2019/3/20
 */
public class NewIoClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        //阻塞
        socketChannel.configureBlocking(true);

        String fileName = "D://BaiduNetdiskDownload/50、零拷贝实例深度剖析.rar";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();
        //一行代码搞定文件传输过程(内部实现了零拷贝的特性)
        long transferTo = fileChannel.transferTo(0,fileChannel.size(),socketChannel);

        System.out.println("发送字节数: " + transferTo + ",耗时: " + (System.currentTimeMillis() - startTime));

        socketChannel.close();

    }
}
