package cn.lcdiao.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by diao on 2019/3/12
 */
public class NioTest3 {
    /**
     * 先写再读
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();

        //不管读还是写，Buffer对象必须要有
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        byte[] messages = "hello world ,nihao".getBytes();

        //byteBuffer.put(messages);
        for (int i = 0; i < messages.length; ++i){
            byteBuffer.put(messages[i]);
        }

        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }


}
