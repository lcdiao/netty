package cn.lcdiao.netty.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * Created by diao on 2019/3/1
 */
public class ThriftClient {
    public static void main(String[] args) {
        //与服务端的arg.transportFactory(new TFramedTransport.Factory());对应
        TTransport transport = new TFramedTransport(new TSocket("localhost",8899),600);

        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            //打开socket
            transport.open();
            //向服务端发送请求
            Person person = client.getPersionByUsername("张三");

            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("---------------------");

            Person person2 = new Person();
            person2.setUsername("李四");
            person2.setAge(30);
            person2.setMarried(true);

            client.savePerson(person2);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage(),ex);
        }finally {
            transport.close();
        }
    }
}
