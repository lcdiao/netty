package cn.lcdiao.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * Created by diao on 2019/2/28
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage msg) throws Exception {

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接处于活跃状态时发送消息


        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        //模拟传递不确定消息
        if(0 == randomInt){
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.PersionType)
                    .setPerson(MyDataInfo.Person.newBuilder().setName("张三").setAge(22).setAddress("广东").build())
                    .build();
        }else if (1 == randomInt){
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder().setName("汪汪").setAge(4).build())
                    .build();
        }else{
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder().setName("喵喵").setCity("广州").build())
                    .build();
        }

        ctx.channel().writeAndFlush(myMessage);
    }
}
