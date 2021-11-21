package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.Exception.rpcfxException;
import io.kimmking.rpcfx.api.*;

import io.netty.handler.codec.http.FullHttpRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.util.IOUtils.close;

public final class Rpcfx extends ClassLoader{

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance, Filter filter) throws ClassNotFoundException {

        // 加filte之一

        // curator Provider list from zk
        List<String> invokers = new ArrayList<>();
        // 1. 简单：从zk拿到服务提供的列表
        // 2. 挑战：监听zk的临时节点，根据事件更新这个list（注意，需要做个全局map保持每个服务的提供者List）

        List<String> urls = router.route(invokers);

        String url = loadBalance.select(urls); // router, loadbalance

        return (T) create(serviceClass, url, filter);

    }
//生成动态代理类

    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) throws ClassNotFoundException {

        //实现字节码增强模式
        //        String resourcesPath="RpcfxInvocationHandler";
//        InputStream resourceAsStream = this.getClass().getClass().getClassLoader().getResourceAsStream(resourcesPath);
//        try{
//            int available = resourceAsStream.available();//找不到xlass会报出空指针异常  这样显示不符合逻辑
//
//            byte[] bytes=new byte[available];
//            System.out.println(bytes.toString());
//            resourceAsStream.read(bytes);
//            //无法根据文件创建出一个可读取的内容时会报错
//            // java.lang.ClassFormatError: Incompatible magic value 2291900044 in class file Hello
//            return (T) defineClass(resourcesPath,bytes,0,available);  }
//        catch (IOException e){
//            throw new ClassNotFoundException(resourcesPath,e);
//        }finally {
//            close(resourceAsStream);
//        }

        // 0. 替换动态代理 -> 字节码生成   //完成
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(),
                new Class[]{serviceClass},
                new RpcfxInvocationHandler(serviceClass, url, filters));

    }

    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url, Filter... filters) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

            // 加filter地方之二
            // mock == true, new Student("hubao");

            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

            if (null!=filters) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        return null;
                    }
                }
            }

            RpcfxResponse response = post(request, url);
            try {
                if (!response.isStatus()) throw new rpcfxException();
            } catch (rpcfxException e) {
                e.printStackTrace();
            }
    return JSON.parse(response.getResult().toString());


            // 加filter地方之三
            // Student.setTeacher("cuijing");

            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException


        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: " + reqJson);

            // 1.可以复用client
            // 2.尝试使用httpclient或者netty client
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: "+respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
            //nettyclient方式

        }}
}
