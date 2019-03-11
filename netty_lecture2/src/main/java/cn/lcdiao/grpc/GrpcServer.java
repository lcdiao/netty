package cn.lcdiao.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by diao on 2019/3/11
 */
public class GrpcServer {

    private Server server;

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();

        System.out.println("server started!");

        //添加回调钩子,非阻塞的
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("关闭jvm");
            GrpcServer.this.stop();
        }));

        System.out.println("执行到这里");
    }

    private void stop() {
        if(null != this.server) {
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if(null != this.server) {
            //等待3秒，然后退出
            //this.server.awaitTermination(3000, TimeUnit.MILLISECONDS);
            //一直处于等待状态
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();

        server.start();
        //如果不加这行代码，服务器就不会等待，直接退出
        server.awaitTermination();
    }
}
