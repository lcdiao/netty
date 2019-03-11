package cn.lcdiao.grpc;

import cn.lcdiao.proto.*;
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

    @Override
    public void getStudentByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端信息:" + request.getAge());

        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("广州").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(20).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(20).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("赵六").setAge(20).setCity("深圳").build());

        responseObserver.onCompleted();
    }

    //客户端以流式向服务器发送请求
    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("onNext:" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse = StudentResponse.newBuilder().setName("张三").setAge(22).setCity("西安").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setName("李四").setAge(21).setCity("汕头").build();

                StudentResponseList studentResponseList = StudentResponseList.newBuilder()
                        .addStudentResponse(studentResponse).addStudentResponse(studentResponse2).build();

                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }
}
