package io.tianger.outbound.netty4;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.tianger.filter.HeaderHttpResponseFilter;
import io.tianger.filter.HttpResponseFilter;
import io.tianger.router.HttpEndpointRouter;
import io.tianger.router.RandomHttpEndpointRouter;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {


    private ExecutorService proxyService;
    private List<String> backendUrls;

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router =new RandomHttpEndpointRouter();

    public NettyHttpClientOutboundHandler(List<String> backends){
       this.backendUrls=backends.stream().map(this::formatUrl).collect(Collectors.toList());
        int cores = Runtime.getRuntime().availableProcessors();
        long keepliveTime=1000;
        int queueSize=2048;
        //创建线程池
       RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        this.proxyService = new ThreadPoolExecutor(cores,
                cores, keepliveTime,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"),
                handler);

        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();



    }

    public void handle (final FullHttpRequest fullHttpRequest,
                        final ChannelHandlerContext ctx,
                        HttpResponseFilter filter){
        //将传入的网址进行汇总
        String route = router.route(this.backendUrls);
        final String url=route+fullHttpRequest.uri();
        filter.filter(fullHttpRequest,ctx);
        proxyService.submit(()->fetchGet(fullHttpRequest,ctx,url));
    }

    private void fetchGet(final FullHttpRequest inbound,
                          final ChannelHandlerContext tx,
                          final  String url ){

    }


    private String  formatUrl(String backends) {
        return backends.endsWith("/")?backends.substring(0,backends.length()-1):backends;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        
        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        String request = new String(data, "utf-8");
        System.out.println("Server"+request);

        String response="我是反馈的信息";
        ctx.writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));
    }
}