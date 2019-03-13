package cn.lcdiao.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by diao on 2019/3/13
 */
public class NioTest9 {
    public static void main(String[] args) throws Exception{
        //内存映射文件
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        //从0开始映射5个大小,将内存映射到文件中
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);
        //将文件在内存中修改`
        mappedByteBuffer.put(0,(byte)'a');
        mappedByteBuffer.put(3,(byte)'b');
        //本地的txt文件上的内容已经被改变了！！！

        randomAccessFile.close();
    }
}
