package cn.lcdiao.netty.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFileTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.generated.PersonService;

/**
 * Created by diao on 2019/3/1
 */
public class ThriftServer {
    public static void main(String[] args) throws Exception{
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<PersonServiceImpl>(new PersonServiceImpl());

        //数据传输协议工厂  压缩格式（速度最快）
        arg.protocolFactory(new TCompactProtocol.Factory());
        //二进制格式
        //arg.protocolFactory(new TBinaryProtocol.Factory());
        //json格式
        //arg.protocolFactory(new TJSONProtocol.Factory());
        //提供json只写协议，生成的文件很容易通过脚本语言解析
        //arg.protocolFactory(new TSimpleJSONProtocol.Factory());
        //使用易懂的可读的文本格式，以便于debug
        //arg.protocolFactory(new TJSONProtocol.Factory());

        //数据传输方式工厂
        //TFramedTransport - 以frame为单位进行传输，非阻塞式服务中使用
        //TSocket - 阻塞式socket
        //TFileTransport - 以文件形式进行传输
        //TMemortTransport - 将内存用于I/O，java实现时内部实际使用了简单的ByteArrayOutputStream
        //TZlibTransport - 使用zlib进行压缩，与其他传输方式联合使用。当前无java实现(0.10.0版本)
        arg.transportFactory(new TFramedTransport.Factory());

        arg.processorFactory(new TProcessorFactory(processor));

        //服务模型
        //TSimpleServer - 简单的单线程服务模型，常用于测试
        //TThreadPoolServer - 多线程服务模型，使用标准的阻塞式IO
        //TNonblockingServer - 多线程服务模型，使用非阻塞式IO（需使用TFramedTransport数据传输方式）
        //THsHaServer - THsHa引入了线程池去处理，其模型把读写任务放在线程池去处理；Half-synv/Half-async的处理模式，
                    //  Half-aysnc是在处理IO时间上(accept/read/write io)，Half-sync用于handler对RPC的同步处理
        TServer server = new THsHaServer(arg);

        System.out.println("Thrift Server Started!");

        //异步非阻塞的死循环
        server.serve();
    }
}
