package com.xingmei.netty.core.entity;

import io.netty.channel.Channel;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date 2019/7/25 11:00
 * @Auther cheerUpPing@163.com
 */
public class NettyConstant {

    /**
     * 客户端保存指向服务端的channel对象
     */
    public static Channel serverChannel;

    /**
     * 服务端保存指向客户端的channel对象  key:服务端ip val：channel对象
     */
    public static final Map<String, Channel> clientChannel = new ConcurrentHashMap<String, Channel>();

    /**
     * 云集自定义协议，用作netty通讯的协议包开头
     */
    public static final String custom_protocol_name = "custom_protocol_of_yunji";

    /**
     * 自定义协议头字节
     */
    public static byte[] custom_protocol_name_byte;

    static {
        try {
            custom_protocol_name_byte = custom_protocol_name.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义协议头长度
     */
    public static final int custom_protocol_name_length = custom_protocol_name_byte.length;

    /**
     * 服务端向客户端发送数据，
     * key:    uuid
     * value:  锁对象
     */
    public static final Map<String, Object> nettyCommunicationObjLock = new ConcurrentHashMap<String, Object>();

    /**
     * 客户端想服务端发送响应，保存的
     * key:    uuid
     * value:  {@link ResponseEntity}
     */
    public static final Map<String, ResponseEntity> nettyCommunicationBackMsg = new ConcurrentHashMap<String, ResponseEntity>();

    /**
     * 系统分隔符
     */
    public static final String fileSeparator = System.getProperty("file.separator");

    /**
     * 系统换行符号
     */
    public static final String lineSeparator = System.getProperty("line.separator");
}
