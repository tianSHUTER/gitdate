package io.tianger.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import io.tianger.filter.HeaderHttpResponseFilter;
import io.tianger.filter.HttpResponseFilter;
import io.tianger.outbound.netty4.NettyHttpClientOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpinboundHanderd extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpinboundHanderd.class);
    private final List<String> proxyServer;
    private NettyHttpClientOutboundHandler handler;
    private HttpResponseFilter filter = new HeaderHttpResponseFilter();

    public HttpinboundHanderd(List<String> proxyServers) {
        this.proxyServer=proxyServers;
        this.handler=new NettyHttpClientOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            FullHttpRequest fullHttpRequest=(FullHttpRequest) msg;
            handler.handle(fullHttpRequest,ctx,filter);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();

    }

}
