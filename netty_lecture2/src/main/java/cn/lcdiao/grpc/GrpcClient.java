package cn.lcdiao.grpc;

import cn.lcdiao.proto.MyRequest;
import cn.lcdiao.proto.MyResponse;
import cn.lcdiao.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by diao on 2019/3/11
 */
public class GrpcClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8899)
                .usePlaintext().build();
        //跟服务端交互的真正对象
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);

        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());

        System.out.println(myResponse.getRealname());
        for (int i = 0;i<11;i++){
            myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());

            System.out.println(myResponse.getRealname());
        }


    }
}
