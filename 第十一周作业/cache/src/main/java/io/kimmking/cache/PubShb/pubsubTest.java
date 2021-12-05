package io.kimmking.cache.PubShb;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class pubsubTest {
    public static void main(String[] args) throws InterruptedException {
//        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
//
//        Publisher publisher = new Publisher(jedisPool);
//        publisher.start();
//
//        Subscriber subscriber = new Subscriber(jedisPool);
//        subscriber.start();
//    }


        String host = "127.0.0.1";

        int port = 6379;

        String clientId = "myclient";

        Publisher pubClient = new Publisher(host, port);

        final String channel = "mychannel";

        final Subscriber subClient = new Subscriber(host, port, clientId);

        Thread subThread = new Thread(new Runnable() {

            public void run() {

                System.out.println("------------sub----start------------");

                subClient.sub(channel);

                System.out.println("------------sub----end------------");

            }

        });

        subThread.setDaemon(true);

        subThread.start();

        int i = 0;

        while (i < 20) {

            String message = "客户端订单完成" + i;

            pubClient.pub(channel, message);

            i++;

            Thread.sleep(100);

        }

        subClient.unsubscribe(channel);

    }

}


