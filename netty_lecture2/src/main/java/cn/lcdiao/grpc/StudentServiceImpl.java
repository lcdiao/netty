package cn.lcdiao.grpc;

import cn.lcdiao.proto.MyRequest;
import cn.lcdiao.proto.MyResponse;
import cn.lcdiao.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by diao on 2019/3/11
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端信息:" + request.getUsername());

        //通过responseObserver传递信息
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }
}
