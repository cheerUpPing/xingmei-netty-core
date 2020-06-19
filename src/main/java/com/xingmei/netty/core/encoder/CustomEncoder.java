package com.xingmei.netty.core.encoder;

import com.xingmei.netty.core.entity.MsgEntity;
import com.xingmei.netty.core.entity.NettyConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 *
 * @Date 2019/7/25 11:04
 * @Auther cheerUpPing@163.com
 */
public class CustomEncoder extends MessageToByteEncoder<MsgEntity> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MsgEntity msgEntity, ByteBuf byteBuf) throws Exception {
        //写协议名字：custom_protocol_of_yunji
        byteBuf.writeBytes(NettyConstant.custom_protocol_name_byte);
        //写实体内容长度
        byteBuf.writeInt(msgEntity.getContentLength());
        //写实体内容
        byte[] contentByte = msgEntity.getContent().getBytes("UTF-8");
        byteBuf.writeBytes(contentByte);
    }
}
