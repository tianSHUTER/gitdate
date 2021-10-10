package io.tianger.inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.List;

public class HttpinboundHander {
    private int port;
    private List<String> proxyServers;
    public HttpinboundHander(int port,List<String> proxyServers){
        this.port=port;
        this.proxyServers=proxyServers;
    }
    public void run () throws  Exception
    {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(16);
        try{
            ServerBootstrap bstrap = new ServerBootstrap();
            bstrap.option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bstrap.group(bossGroup,workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.DEBUG))
            .childHandler(new HttpinboundHanderInitializer(this.proxyServers));

            Channel sync = bstrap.bind(port).sync().channel();
            sync.closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class HttpinboundHanderInitializer extends ChannelInitializer<SocketChannel> {
       private List<String> proxyServers;
        public HttpinboundHanderInitializer(List<String> proxyServers) {
            this.proxyServers=proxyServers;
        }

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new HttpObjectAggregator(1024*1024));
            pipeline.addLast(new HttpinboundHanderd(this.proxyServers));
        }
    }
}
