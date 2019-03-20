package cn.lcdiao.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by diao on 2019/3/20
 */
public class OldServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);

        //等待socket连接
        while (true) {
            Socket socket = serverSocket.accept();
            //获取socket的输入流，读取二进制数据
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try {
                byte[] bytesArray = new byte[4096];
                //源源不断获取到对端发送过来的数据
                while (true) {
                    int readCount = dataInputStream.read(bytesArray,0,bytesArray.length);
                    if (-1 == readCount) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
