package com.xingmei.netty.core.handler;

import com.alibaba.fastjson.JSON;
import com.xingmei.netty.core.decoder.CustomDecoder;
import com.xingmei.netty.core.encoder.CustomEncoder;
import com.xingmei.netty.core.entity.RequestEntity;
import com.xingmei.netty.core.entity.MsgEntity;
import com.xingmei.netty.core.entity.NettyConstant;
import com.xingmei.netty.core.start.ClientStart;
import com.xingmei.netty.core.util.SpringContextUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 客户端处理器
 *
 * @Date 2019/7/25 11:53
 * @Auther cheerUpPing@163.com
 */
@Component
@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private SpringContextUtil springContextUtil;
    @Autowired
    private ClientStart clientStart;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MsgEntity) {
            System.out.println("客户端接收到消息：" + ((MsgEntity) msg).getContent());
            RequestEntity contentRequestEntity = JSON.parseObject(((MsgEntity) msg).getContent(), RequestEntity.class);
            BaseProcess baseProcess = (BaseProcess) springContextUtil.getBean(contentRequestEntity.getCommand());
            if (baseProcess != null) {
                baseProcess.process(contentRequestEntity);
            } else {
                System.out.println("服务端命令:" + contentRequestEntity.getCommand() + "对应的客户端处理器不存在");
            }
        } else {
            System.out.println("收到的消息不符合自定义协议要求的格式");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyConstant.serverChannel = ctx.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("==================客户端发生异常");
        cause.printStackTrace();
        Channel channel = ctx.channel();
        try {
            if (channel != null){
                channel.pipeline().remove(CustomEncoder.class);
                channel.pipeline().remove(CustomDecoder.class);
                channel.pipeline().remove(ClientHandler.class);
                channel.close();
                channel.eventLoop().shutdownGracefully();
            }
        }catch (Exception e){

        }finally {
            //重连
            clientStart.startClient();
        }
    }
}
