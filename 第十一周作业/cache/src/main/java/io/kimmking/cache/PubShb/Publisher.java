package io.kimmking.cache.PubShb;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class Publisher extends  Thread{
//    private final JedisPool jedisPool;
//
//    public Publisher(JedisPool jedisPool) {
//        this.jedisPool = jedisPool;
//    }
//    @Override
//    public void run() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        Jedis jedis = jedisPool.getResource();   //连接池中取出一个连接
//        while (true) {
//            String line;
//            try {
//                line = reader.readLine();
//                if (!"quit".equals(line)) {
//                    jedis.publish("mychannel", line);   //从通过mychannel 频道发布消息
//                    System.out.println(String.format("发布消息成功！channel： %s, message： %s", "mychannel", line));
//                } else {
//                    break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }


    private Jedis jedis;

    private String CONSTANT_CLIENTSET = "clientSet";

    public Publisher(String host, int port){

        jedis = new Jedis(host,port);

    }

    private void put(String message){

        Set<String> subClients = jedis.smembers("CONSTANT");

        for(String clientKey:subClients){

            jedis.rpush(clientKey, message);

        }

    }

    public void pub(String channel,String message){

        Long txid = jedis.incr("MAXID");

        String content = txid+"/"+message;

        this.put(content);

        jedis.publish(channel, message);

    }

    public void close(String channel){

        jedis.publish(channel, "quit");

        jedis.del(channel);

    }

}
