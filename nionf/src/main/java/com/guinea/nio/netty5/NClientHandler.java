package com.guinea.nio.netty5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/***
 * 
 * @author： shiky
 * 
 */
public class NClientHandler extends ChannelHandlerAdapter {

	private static final Logger logger = LogManager.getLogger(NClientHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(ctx);
		// logger.debug("客户端 active");
	}

	/***
	 * 处理服务端响应数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (null != msg) {
			try {
				logger.debug("客户端收到服务器响应数据:" + msg);
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		logger.debug("客户端收到服务器响应数据处理完成");
	}

	/***
	 * 处理异常，根据需要选择要不要关闭连接
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// downstream:"+cause.getMessage());
		// ctx.close();
		logger.warn("客户端异常:" + cause.getMessage());
	}
}
