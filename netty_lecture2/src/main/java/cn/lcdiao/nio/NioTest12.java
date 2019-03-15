package cn.lcdiao.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Created by diao on 2019/3/15
 */
public class NioTest12 {

    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        //获取Selector对象


        Selector selector = Selector.open();
    }
}
