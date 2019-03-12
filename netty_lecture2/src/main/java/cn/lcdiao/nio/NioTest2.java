package cn.lcdiao.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by diao on 2019/3/12
 */
public class NioTest2 {
    /**
     * 先读再写
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        //不管读还是写，Buffer对象必须要有
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        fileChannel.read(byteBuffer);

        //操作反转
        byteBuffer.flip();

        //剩余数大于0
        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            System.out.println("Character: " + (char)b);
        }

        fileInputStream.close();
    }


}
