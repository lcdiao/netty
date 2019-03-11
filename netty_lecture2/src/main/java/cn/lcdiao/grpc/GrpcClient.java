package cn.lcdiao.grpc;

import cn.lcdiao.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

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
//        for (int i = 0;i<11;i++){
//            myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
//
//            System.out.println(myResponse.getRealname());
//        }

        System.out.println("-----------------------------");

        Iterator<StudentResponse> iter = blockingStub.getStudentByAge(StudentRequest.newBuilder().setAge(22).build());
        while(iter.hasNext()){
            StudentResponse studentResponse = iter.next();

            System.out.println(studentResponse.getName() + "," + studentResponse.getAge() + "," + studentResponse.getCity());
        }

        System.out.println("-----------------------------");
    }
}
