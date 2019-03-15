package cn.lcdiao.nio;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by diao on 2019/3/13
 * 关于Buffer的Scattering和Gathering:处理Buffer数组,数据的分门别类
 */
public class NioTest11 {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];

        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int bytesRead = 0;
            //如果实际读到的字节数小于总的字节数
            while (bytesRead < messageLength) {
                long r = socketChannel.read(buffers);
                bytesRead += r;

                System.out.println("bytesRead: " + bytesRead);

                Arrays.asList(buffers).stream()
                        .map(buffer -> "position: " + buffer.position() + ",limit: " + buffer.limit())
                        .forEach(System.out::println);
            }

            //当三个Buffer都被读满了之后
            Arrays.asList(buffers).forEach(buffer -> {
                //翻转
                buffer.flip();
            });

            long bytesWritten = 0;
            while(bytesWritten < messageLength) {
                long r = socketChannel.write(buffers);

                bytesWritten += r;
            }

            Arrays.asList(buffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("bytesRead: " + bytesRead + ",bytesWritten: " + bytesWritten + ",messageLength: " + messageLength);
        }

    }
}
