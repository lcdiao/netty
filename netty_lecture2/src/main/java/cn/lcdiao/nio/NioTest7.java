package cn.lcdiao.nio;

import java.nio.ByteBuffer;

/**
 * Created by diao on 2019/3/12
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //class java.nio.HeapByteBuffer
        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        //只读Buffer,我们可以随时将一个普通的Buffer调用asReadOnlyBuffer方法返回一个只读Buffer
        //但不能将一个只读Buffer转换为读写Buffer
        ByteBuffer readonlyBuffer = buffer.asReadOnlyBuffer();
        //class java.nio.HeapByteBufferR
        System.out.println(readonlyBuffer.getClass());



    }
}
