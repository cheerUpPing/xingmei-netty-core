package com.xingmei.netty.core.start;

import com.xingmei.netty.core.initializer.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 启动服务端
 *
 * @Date 2019/7/25 11:58
 * @Auther cheerUpPing@163.com
 */
@Component
public class ServerStart {

    @Value("${netty.server.port:0}")
    private int port;

    @Autowired
    private ServerInitializer serverInitializer;

    @PostConstruct
    public void startServer(){
        //配置了服务器的有效端口
        if (port > 0){
            new Thread(){
                @Override
                public void run() {
                    ServerStart.this.start();
                }
            }.start();
        }
    }

    private void start() {
        System.out.println("准备启动netty服务端,端口：" + port);
        // 配置NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 服务器辅助启动类配置
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(serverInitializer)//
                    .option(ChannelOption.SO_BACKLOG, 1024) // 设置tcp缓冲区
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口 同步等待绑定成功
            ChannelFuture f = b.bind(port).sync();
            // 等到服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅释放线程资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
