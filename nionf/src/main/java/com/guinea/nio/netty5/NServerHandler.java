package com.guinea.nio.netty5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/***
 * server端网络IO事件处理
 * 
 * @author shiky
 *
 */
public class NServerHandler extends ChannelHandlerAdapter {

	private static final Logger logger = LogManager.getLogger(NServerHandler.class);

	/***
	 * 读取客户消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.debug("服务器读取到客户端请求...");
		if (null != msg) {
			try {
					StringBuffer sbf = new StringBuffer("收到客户端->");
					sbf.append(ctx.channel().remoteAddress());
					sbf.append("的消息：");
					sbf.append(msg);
					logger.debug(sbf);
					// 服务端响应消息
					ctx.writeAndFlush("ok");
					ctx.close();
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}
	}

	/***
	 * 服务端监听到客户端活动
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("channelActive>>>> Client:" + ctx.channel().remoteAddress() + "在线");
		ctx.fireChannelActive();
	}

	/****
	 * 响应处理
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.debug("服务端readComplete 响应完成");
		ctx.fireChannelReadComplete();
	}

	/***
	 * 监听客户端掉线
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// Client:"+ctx.channel().remoteAddress()+"掉线");
		super.channelInactive(ctx);
	}

	/***
	 * 异常信息 (根据需要，选择是否关闭)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.debug("服务端异常" + cause.getMessage());
		ctx.fireExceptionCaught(cause);
		// ctx.close();
	}
}
