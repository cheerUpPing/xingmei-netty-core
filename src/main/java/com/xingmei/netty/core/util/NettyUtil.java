package com.xingmei.netty.core.util;

import com.xingmei.netty.core.entity.*;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.UUID;

/**
 * netty服务端和客户端之间通信
 *
 * @Date 2019/10/12 15:35
 * @Auther cheerUpPing@163.com
 */
public class NettyUtil {

    /**
     * 服务端向客户端发送命令  需要等客户端返回响应  带有对象锁
     *
     * @param hostInnerIp
     * @param nettyCommand
     * @return {@link ResponseEntity} 服务端向客户端发送命令后，等待客户端的响应
     */
    public static ResponseEntity sendServerCommand(String hostInnerIp, NettyCommand nettyCommand) {
        return NettyUtil.sendServerCommand(hostInnerIp, nettyCommand, null);
    }

    /**
     * 服务端向客户端发送命令  需要等客户端返回响应  带有对象锁
     *
     * @param hostInnerIp
     * @param nettyCommand
     * @return {@link ResponseEntity} 服务端向客户端发送命令后，等待客户端的响应
     */
    public static ResponseEntity sendServerCommand(String hostInnerIp, NettyCommand nettyCommand, Map<String, Object> param) {
        Channel channel = NettyConstant.clientChannel.get(hostInnerIp);
        ResponseEntity contentResponseEntity = null;
        if (channel != null && channel.isActive()) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            RequestEntity contentRequestEntity = new RequestEntity();
            contentRequestEntity.setCommand(nettyCommand.getCommand());
            contentRequestEntity.setRequestId(uuid);
            contentRequestEntity.setClientIp(hostInnerIp);
            contentRequestEntity.setParam(param);
            Object obj = new Object();
            NettyConstant.nettyCommunicationObjLock.put(uuid, obj);
            channel.writeAndFlush(new MsgEntity(contentRequestEntity));
            //20秒超时
            synchronized (obj) {
                try {
                    obj.wait(20 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //超时/客户端响应之后，移除
            NettyConstant.nettyCommunicationObjLock.remove(uuid);
            //获取netty客户端返回的数据
            contentResponseEntity = NettyConstant.nettyCommunicationBackMsg.get(uuid);
            //因为超时而执行的
            if (contentResponseEntity == null) {
                contentResponseEntity = new ResponseEntity();
                contentResponseEntity.setCode(-1);
                contentResponseEntity.setCommand(nettyCommand.getCommand());
                contentResponseEntity.setRequestId(uuid);
                contentResponseEntity.setClientIp(hostInnerIp);
                contentResponseEntity.setErrMsg("请求超时请稍候重试");
            } else {//客户端正常响应而执行
                //删除响应数据
                NettyConstant.nettyCommunicationBackMsg.remove(uuid);
            }
        } else {
            //还没有发生netty通信
            contentResponseEntity = new ResponseEntity();
            contentResponseEntity.setCode(-1);
            contentResponseEntity.setRequestId(null);
            contentResponseEntity.setClientIp(hostInnerIp);
            contentResponseEntity.setErrMsg("服务端和客户端通道不存在或者已经失效，请确认代理端是否启动，代理服务器ip:" + hostInnerIp);
        }
        return contentResponseEntity;
    }

    /**
     * 客户端向服务端发送回复数据，告知服务端请求的处理情况
     *
     * @param contentResponseEntity
     */
    public static void sendClientResponse(ResponseEntity contentResponseEntity) {
        if (NettyConstant.serverChannel != null && NettyConstant.serverChannel.isActive()) {
            NettyConstant.serverChannel.writeAndFlush(new MsgEntity(contentResponseEntity));
        } else {
            System.out.println("客户端和服务端通道已经失效");
        }
    }
}
