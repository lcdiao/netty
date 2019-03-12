package cn.lcdiao.nio;

import java.nio.ByteBuffer;

/**
 * Created by diao on 2019/3/12
 */
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(64);

        //ByteBuffer类型化的put和get方法(顺序要相同)
        buffer.putInt(16);
        buffer.putLong(50000000000L);
        buffer.putDouble(14.123456);
        buffer.putChar('你');
        buffer.putShort((short)2);
        buffer.putChar('我');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());

    }
}
