package io.kimmking.cache.PubShb;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class Subscriber extends Thread {

//    private final JedisPool jedisPool;
//    private final MsgListener msgListener = new MsgListener();
//
//    private final String channel = "mychannel";
//
//    public Subscriber(JedisPool jedisPool) {
//        super("Subscriber");
//        this.jedisPool = jedisPool;
//    }
//
//    @Override
//    public void run() {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();   //取出一个连接
//            jedis.subscribe(msgListener, channel);    //通过subscribe的api去订阅，参数是订阅者和频道名
//
//            //注意：subscribe是一个阻塞的方法，在取消订阅该频道前，会一直阻塞在这，无法执行后续的代码
//            //这里在msgListener的onMessage方法里面收到消息后，调用了this.unsubscribe();来取消订阅，才会继续执行
//            System.out.println("继续执行后续代码。。。");
//
//        } catch (Exception e) {
//            System.out.println(String.format("subsrcibe channel error, %s", e));
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }


    private Jedis jedis;

    private JedisPubSub listener;

    private String CONSTANT_CLIENTSET="clientSet";

    public Subscriber(String host, int port, String clientId){

        jedis = new Jedis(host,port);

        listener = new MsgListener(clientId,new Jedis(host,port));

        jedis.sadd(CONSTANT_CLIENTSET, clientId);

    }

    public void sub(String channel){

        jedis.subscribe(listener, channel);

    }

    public void unsubscribe(String channel){

        listener.unsubscribe(channel);

    }


}
