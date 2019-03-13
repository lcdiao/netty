package cn.lcdiao.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by diao on 2019/3/13
 */
public class NioTest10 {
    public static void main(String[] args) throws Exception{
        //文件加锁
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        //加锁(共享锁和排他锁)       从3开始锁6个长度,true:共享锁，false:排他锁
        FileLock fileLock = fileChannel.lock(3,6,true);

        System.out.println("valid: " + fileLock.isValid());
        System.out.println("lock type: " + fileLock.isShared());

        fileLock.release();

        randomAccessFile.close();
    }
}
