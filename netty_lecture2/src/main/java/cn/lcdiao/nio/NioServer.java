package cn.lcdiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by diao on 2019/3/18
 */
public class NioServer {

    //保存所有客户端的连接信息
    private static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        //创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //配置成非阻塞
        serverSocketChannel.configureBlocking(false);
        //从serverSocketChannel获取到服务器端的socket对象(ServerSocket)
        ServerSocket serverSocket = serverSocketChannel.socket();
        //绑定端口8899
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        //将serverSocketChannel注册到selector上，关注着连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                //阻塞的，会返回所关注的事件的数量！
                selector.select();
                //select()返回之后，获得所注册的所有的已经发生的事件的selectedKeys集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {

                    final SocketChannel client;

                    try {
                        //判断是否客户端连接事件
                        if (selectionKey.isAcceptable()) {
                            //强转为ServerSocketChannel（只注册ServerSocketChannel的连接事件到selector上）
                            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                            //返回与之连接的SocketChannel对象（获得客户端的SocketChannel）
                            client = server.accept();
                            //配置成非阻塞
                            client.configureBlocking(false);
                            //将client注册到selector上，关注着读取事件
                            client.register(selector,SelectionKey.OP_READ);

                            String key = "[" + UUID.randomUUID().toString() +"]";
                            //保存到map
                            clientMap.put(key,client);

                            //selectionKeys.remove(selectionKey);

                          //判断是否读取事件
                        } else if (selectionKey.isReadable()) {
                            //获取client，需要强转为SocketChannel（只注册SocketChannel的读取事件到selector上）
                            client = (SocketChannel)selectionKey.channel();
                            //将数据保存到buffer
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readBuffer);
                            //如果真的有数据读到buffer里
                            if (count > 0) {
                                //翻转
                                readBuffer.flip();
                                //以utf-8编码转换数据
                                Charset charset = Charset.forName("utf-8");
                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());

                                System.out.println(client + ":" + receiveMessage);

                                String senderKey = null;
                                //获取到发送者的key
                                for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()) {
                                    if (client == entry.getValue()) {
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }

                                //得到每一个已经连接的SocketChannel对象并发送信息
                                for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()) {
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

                                    writeBuffer.put((senderKey + ":" + receiveMessage).getBytes());
                                    writeBuffer.flip();

                                    value.write(writeBuffer);
                                }
                            }

                            //selectionKeys.remove(selectionKey);
                        }
                    } catch (Exception e) {
                        System.out.println("强制关闭连接");
                        selectionKey.cancel();
                        e.printStackTrace();
                    }

                });

                //处理完特定的selection后一定要删除掉
                selectionKeys.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
