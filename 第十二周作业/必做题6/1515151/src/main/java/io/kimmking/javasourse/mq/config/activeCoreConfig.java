package io.kimmking.javasourse.mq.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class activeCoreConfig {


    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;


    @Value("${spring.activemq.user}")
    private String name;

    @Value("${spring.activemq.password}")
    private String password;

    //消息重发策略
    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在重新发送失败后，增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重新发送次数 默认为6次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重新发时间间隔单位毫秒，默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1000L);
        //重新失败后等待时间为上一次两倍
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1
        redeliveryPolicy.setInitialRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    @Bean("ConnectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory(@Qualifier("redeliveryPolicy")RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerUrl, name, password);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate(@Qualifier("ConnectionFactory")ActiveMQConnectionFactory activeMQConnectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        //进行持久化配置 1 表示非持久化  2 表示持久化
        jmsTemplate.setDeliveryMode(2);
        //客户端签收模式
        return jmsTemplate;
    }
//在使用topic时开启
//    @Bean(name = "jmsTopicListener")
//    public JmsListenerContainerFactory<?> jmsTopicListen(@Qualifier("ConnectionFactory")ActiveMQConnectionFactory activeMQConnectionFactory){
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(activeMQConnectionFactory);
//        factory.setPubSubDomain(true);
//        factory.setRecoveryInterval(1000L);
//        return factory;
//    }


}
