package com.guinea.communications.netty.webSocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @title
 * @author: shiky
 * @describe:
 * @date: 2016/7/7
 */
public class PlanTaskNettyInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // e.pipeline().addLast("frameDecoder", new
        // LengthFieldBasedFrameDecoder(64, 0, 8,0,8));
        // e.pipeline().addLast("frameEncoder", new LengthFieldPrepender(8));
        // e.pipeline().addLast("frameDecoder", new
        // DelimiterBasedFrameDecoder(1000, false,
        // new ByteBuf[]{Unpooled.wrappedBuffer(new byte[]{'#'}),}));
        // 用于解析http报文的handler
        pipeline.addLast("decoder", new HttpRequestDecoder());
        // 用于将解析出来的数据封装成http对象，httprequest什么的
        pipeline.addLast("aggregator", new HttpObjectAggregator(256));
        // 用于将response编码成httpresponse报文发送
        pipeline.addLast("encoder", new HttpResponseEncoder());
        /***
         * HttpServerCodec它会将HTTP客户端请求转成HttpRequest对象，
         * 将HttpResponse对象编码成HTTP响应发送给客户端
         * (用了HttpRequestDecoder，HttpResponseEncoder就不用此方法)
         */
        // e.pipeline().addLast("http-codec", new HttpServerCodec());
        /***
         * ChunkedWriteHandler 这个handler主要用于处理大数据流,
         * 比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的,增加ChunkedWriteHandler
         * 这个handler我们就不用考虑这个问题了.
         */
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        /***
         * cp.addLast("writeTimeout", new WriteTimeoutHandler(new
         * HashedWheelTimer(),10));
         */
        // e.pipeline().addLast("writeTimeout", new WriteTimeoutHandler(10));
        /***
         * 自定义hander
         */
        pipeline.addLast("handler", new PlanTaskNettyServerHandler());
    }
}
