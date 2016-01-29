package com.guinea.nio.netty5;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/***
 * 
 * @author： shiky
 * 
 * @Describe: 2016年1月26日
 */
public class NServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		/***
		 * 个地方的 必须和服务端对应上。否则无法正常解码和编码 设置包长，解决，粘包问题
		 */
		pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8));
		pipeline.addLast(new LengthFieldPrepender(8));
		// 当 read 的时候
		pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
		// 当 send 的时候
		pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
		// 服务端逻辑
		pipeline.addLast(new NServerHandler());
	}

}
