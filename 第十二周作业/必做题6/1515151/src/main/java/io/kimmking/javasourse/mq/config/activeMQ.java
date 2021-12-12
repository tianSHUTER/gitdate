package io.kimmking.javasourse.mq.config;


import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class activeMQ {
    /**
     * 声明普通队列
     */
    @Bean(name = "queue")
    public ActiveMQQueue queue() {
        return new ActiveMQQueue("springboot.queue");
    }

    /**
     * 声明延时队列
     */
    @Bean(name = "delayqueue")
    public ActiveMQQueue delayQueue() {
        return new ActiveMQQueue("springboot.delay.queue");
    }

    /**
     * 声明订阅模式-广播队列
     * 开启topic时使用
     */
    @Bean(name = "topic")
    public ActiveMQTopic topic() {
        return new ActiveMQTopic("springboot.topic");
    }
}


