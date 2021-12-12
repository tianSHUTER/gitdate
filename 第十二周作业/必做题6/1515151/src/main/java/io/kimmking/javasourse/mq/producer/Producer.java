package io.kimmking.javasourse.mq.producer;


import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;

@Slf4j
@Component
public class Producer {


    @Autowired
    private JmsTemplate jmsTemplate;

    public <T extends Serializable> void send(Destination destination, T message){
        jmsTemplate.convertAndSend(destination,message);
    }

    public <T extends Serializable> void delaySend(Destination destination, T data, Long time){
        Session session=null;
        Connection connection=null;
        MessageProducer producer=null;
        ConnectionFactory connectionFactory = jmsTemplate.getConnectionFactory();
        try{
             connection = connectionFactory.createConnection();
            connection.start();
            connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue());
            ObjectMessage message = session.createObjectMessage(data);
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,time);

            producer.send(message);

            session.commit();
            log.info("[ 延时消息 ] >> 发送完毕 data:{}", data);

        } catch (JMSException e) {
            log.error("[ 发送延时消息 ] 异常 >> data:{}", data, e);
        }finally {
            try {
                if (producer != null) {
                    producer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                log.error("[ 发送延时消息 ] 关闭资源异常 >> data{}", data, e);
            }
        }
    }




    }
