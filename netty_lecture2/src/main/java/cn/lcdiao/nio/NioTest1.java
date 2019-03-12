package cn.lcdiao.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by diao on 2019/3/12
 */
public class NioTest1 {
    public static void main(String[] args) {
        //建立一个大小为10的缓冲区
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i< 5; ++i) {
            //生成随机数
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        System.out.println("before flip limit: " + buffer.limit());

        //翻转，写改为读
        buffer.flip();

        System.out.println("agter flip limit: " + buffer.limit());

        System.out.println("enter while loop");

        while (buffer.hasRemaining()) {
//            System.out.println("position: " + buffer.position());
//            System.out.println("limit: " + buffer.limit());
//            System.out.println("capacity: " + buffer.capacity());
            System.out.println(buffer);

            System.out.println(buffer.get());
        }
    }
}
