package com.xingmei.netty.core.decoder;

import com.xingmei.netty.core.entity.MsgEntity;
import com.xingmei.netty.core.entity.NettyConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义解码器
 *
 * @Date 2019/7/25 11:12
 * @Auther cheerUpPing@163.com
 */
public class CustomDecoder extends ByteToMessageDecoder {

    // 最少的字节数：协议名字节数 + 实体长度
    private final int BASE_LENGTH = NettyConstant.custom_protocol_name_length + 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> list) throws Exception {
        //1. 首先确认可读长度大于基本长度
        if (buffer.readableBytes() > BASE_LENGTH) {

            while (true) {
                // 标记包头开始的index
                buffer.markReaderIndex();
                //如果读到了数据包的协议开头(协议名)，那么就结束循环
                byte[] customProtocolNameByte = new byte[NettyConstant.custom_protocol_name_length];
                buffer.readBytes(customProtocolNameByte);
                String customProtocolName = new String(customProtocolNameByte, "UTF-8");
                if (customProtocolName.equals(NettyConstant.custom_protocol_name)) {
                    break;
                }
                //没读到协议开头，退回到标记
                buffer.resetReaderIndex();
                //跳过一个字节
                buffer.readByte();

                //如果可读长度小于基本长度
                if (buffer.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }
            //获取消息的长度
            int contentLength = buffer.readInt();

            //判断请求数据包是否到齐
            if (buffer.readableBytes() < contentLength) {
                buffer.resetReaderIndex();
                return;
            }
            byte[] content = new byte[contentLength];
            buffer.readBytes(content);
            MsgEntity msgEntity = new MsgEntity(contentLength, new String(content, "UTF-8"));
            list.add(msgEntity);
        }
    }
}
