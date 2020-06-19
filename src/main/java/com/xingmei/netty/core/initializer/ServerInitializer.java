package com.xingmei.netty.core.initializer;

import com.xingmei.netty.core.decoder.CustomDecoder;
import com.xingmei.netty.core.encoder.CustomEncoder;
import com.xingmei.netty.core.handler.ServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务端初始化器
 *
 * @Date 2019/7/25 11:44
 * @Auther cheerUpPing@163.com
 */
@Component
public class ServerInitializer extends ChannelInitializer {

    @Autowired
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 添加自定义协议的编解码工具
        ch.pipeline().addLast(new CustomEncoder());
        ch.pipeline().addLast(new CustomDecoder());
        // 处理网络IO
        ch.pipeline().addLast(serverHandler);
    }
}
