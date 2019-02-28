package cn.lcdiao.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by diao on 2019/2/28
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage msg) throws Exception {
        //对收到的消息进行处理

        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();

        if(dataType == MyDataInfo.MyMessage.DataType.PersionType){
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAddress());

        } else if(dataType == MyDataInfo.MyMessage.DataType.DogType){
            MyDataInfo.Dog dog = msg.getDog();

            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        } else{
            MyDataInfo.Cat cat = msg.getCat();

            System.out.println(cat.getName());
            System.out.println(cat.getCity());
        }


    }
}
