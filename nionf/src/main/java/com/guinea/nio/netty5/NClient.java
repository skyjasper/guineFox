package com.guinea.nio.netty5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/****
 * 
 * @author shiky
 * @Describe 客户端
 */
public class NClient {

	private static final Logger logger = LogManager.getLogger(NClient.class);
	private final static String host = "127.0.0.1";
	private final static Integer port = 8898;

	public void testClient() {
		
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// 客户端辅助启动类 对客户端配置
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.handler(new NClinetlInitializer());
			// 异步链接服务器 同步等待链接
			Channel ch = bootstrap.connect(host, port).sync().channel();
			ch.writeAndFlush("客户端消息:初吻的那个季节不是已经哭过了吗?");
			ChannelFuture channelFuture = ch.closeFuture().sync();
			// 监听服务器关闭监听(应用程序等待直到channel关闭)
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
			logger.debug("客户端的释放了线程资源...");
		}
	}

}
