package com.guinea.communications.netty.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title
 * @author: shiky
 * @describe:
 * @date: 2016/7/7
 */
public class PlanTaskNettyServer {

    private static Logger logger = LoggerFactory.getLogger(PlanTaskNettyServer.class);

    private int portNumber;

    public  PlanTaskNettyServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new PlanTaskNettyInitialzer());
            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            logger.debug("服务端开启等待客户端连接 ... ...");
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            logger.debug("任务推送服务关闭!!!");
        }
    }

    public static void main(String[] args) {
        new PlanTaskNettyServer(8787).run();
    }
}
