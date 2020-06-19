package com.xingmei.netty.core.handler;

import com.alibaba.fastjson.JSON;
import com.xingmei.netty.core.entity.ResponseEntity;
import com.xingmei.netty.core.entity.MsgEntity;
import com.xingmei.netty.core.entity.NettyConstant;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 服务端处理器
 *
 * @Date 2019/7/25 11:47
 * @Auther cheerUpPing@163.com
 */
@Component
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MsgEntity) {
            System.out.println("服务端接收到消息：" + ((MsgEntity) msg).getContent());
            ResponseEntity contentResponseEntity = JSON.parseObject(((MsgEntity) msg).getContent(), ResponseEntity.class);
            //保存客户端响应的数据
            NettyConstant.nettyCommunicationBackMsg.put(contentResponseEntity.getRequestId(), contentResponseEntity);
            Object obj = NettyConstant.nettyCommunicationObjLock.get(contentResponseEntity.getRequestId());
            if (obj != null) {
                //得到客户端响应后通知等待线程 继续向下执行
                synchronized (obj){
                    obj.notifyAll();
                }
            }
        } else {
            System.out.println("收到的消息不符合自定义协议要求的格式");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        NettyConstant.clientChannel.put(clientIP, ctx.channel());
        System.out.println("客户端ip地址:" + clientIP);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
