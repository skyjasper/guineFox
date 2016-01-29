package com.guinea.nio.netty5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/***
 * 
 * @author： shiky
 * 
 * @Describe: 2016年1月26日
 */
public class NServer {

	private static final Logger logger = LogManager.getLogger(NServer.class);

	private final static String host = "127.0.0.1";
	private final static Integer port = 8898;

	public void testServer() {
		/***
		 * ·NioEventLoopGroup 实际上是个连接池，NioEventLoopGroup在后台启动了n个NioEventLoop
		 * 来处理Channel事件，每个NioEventLoop负责m个Channel
		 * ·NioEventLoopGroup从NioEventLoop数组集中挨个取出NioEventLoop用以处理Channel
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// NIO服务器端的辅助启动类
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup);
			// 设置 nio 类型的 channel
			serverBootstrap.channel(NioServerSocketChannel.class);
			/***
			 * 此处在写一个TCP/IP 的服务端，因此我们被允许设置 socket 的参数选项比如tcpNoDelay 和 keepAlive。
			 */
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			/***
			 * option() 是提供NioServerSocketChannel用来接收进来的连接。 childOption()
			 * 是提供父管道ServerChannel接收到的 连接(此例是 NioServerSocketChannel)。
			 */
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			/***
			 * 绑定I/O事件的处理类 处理网络IO事件
			 */
			serverBootstrap.childHandler(new NServerInitializer());
			/***
			 * 服务器启动后 绑定监听端口 同步等待成功 ，异步操作的通知回调 回调处理用的 ChildChannelHandler
			 * 
			 */
			ChannelFuture channelFuture = serverBootstrap.bind(host, port).sync();
			logger.debug("NServer启动");
			// 监听服务器关闭监听(应用程序等待直到channel关闭)
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 关闭EventLoopGroup释放资源包
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			logger.debug("服务端释放了线程资源...");
		}
	}
}
