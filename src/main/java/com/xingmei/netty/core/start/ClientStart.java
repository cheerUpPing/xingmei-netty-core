package com.xingmei.netty.core.start;

import com.xingmei.netty.core.initializer.ClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 启动客户端
 *
 * @Date 2019/7/25 14:13
 * @Auther cheerUpPing@163.com
 */
@Component
public class ClientStart {

    @Value("${remote.server.ip:}")
    private String serverIp;

    @Value("${remote.server.port:0}")
    private int serverPort;

    @Autowired
    private ClientInitializer clientInitializer;

    @PostConstruct
    public void startClient(){
        if (serverIp != null && !serverIp.trim().equals("") && serverPort > 0){
            new Thread(){
                @Override
                public void run() {
                    ClientStart.this.start();
                }
            }.start();
        }
    }

    private void start() {
        System.out.println("准备启动netty客户端,远程服务器ip:" + serverIp + " 远程服务器端口：" + serverPort);
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端辅助启动类 对客户端配置
            Bootstrap b = new Bootstrap();
            b.group(group)//
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(clientInitializer);
            // 异步链接服务器 同步等待链接成功
            ChannelFuture f = b.connect(serverIp, serverPort).sync();

            // 等待链接关闭
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
