package cn.lcdiao.protobuf;

/**
 * Created by diao on 2019/2/28
 */
public class ProtoBufTest {
    public static void main(String[] args) throws Exception{
        DataInfo.Student student = DataInfo.Student.newBuilder().
                setName("张三").setAge(20).setAddress("北京").build();
        //将对象转为字节数组(意义:字节数组可以在网络上传输)
        byte[] student2ByteArray = student.toByteArray();

        //反序列化为java对象(不同语言也可转换)
        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student2);
        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}
