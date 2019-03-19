package cn.lcdiao.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by diao on 2019/3/19
 */
public class NioTest13 {
    public static void main(String[] args) throws Exception{
        String inputFile = "NioTest13_In.txt";
        String outputFile = "NioTest13_Out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");

        //获取输入文件的长度
        long inputLength = new File(inputFile).length();

        //获取到输入和输出的channel
        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        //通过内存映射文件，把整个文件全给映射上，这样直接修改内存里面的内容会反映到文件上面
        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY,0,inputLength);


        System.out.println("===================================");

        //列出所有字符集
        Charset.availableCharsets().forEach((k,v) -> {
            System.out.println(k + "," + v);
        });

        System.out.println("===================================");


        //Charset charset = Charset.forName("utf-8");
        Charset charset = Charset.forName("iso-8859-1");
        //decoder是把字节数组转成字符串
        CharsetDecoder decoder = charset.newDecoder();
        //encoder是把字符串转成字节数组
        CharsetEncoder encoder = charset.newEncoder();

        //解码成CharBuffer
        CharBuffer charBuffer = decoder.decode(inputData);

        //编码成ByteBuffer
        ByteBuffer outputData = encoder.encode(charBuffer);

        //输出到文件通道里
        outputFileChannel.write(outputData);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();
    }
}
