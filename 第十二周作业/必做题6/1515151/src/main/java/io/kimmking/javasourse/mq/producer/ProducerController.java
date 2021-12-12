package io.kimmking.javasourse.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.command.ActiveMQTopic;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.Queue;

@Slf4j
@RestController
public class ProducerController {
    @Autowired
    private Producer producer;

    @Autowired
    private Queue queue;

    @Autowired
    private Queue delayqueue;

    @Autowired
    private ActiveMQTopic topic;

    @GetMapping("/queue")
    public void sendQueueMsg(String msg) {
        log.info("[ 普通消息发送 ] >> msg:{}, 发送时间：{}", msg, DateUtil.now());
        producer.send(queue, msg);
    }

    /**
     * 发送延时类型消息
     *
     * @param msg
     */
    @GetMapping("/delayQueue")
    public void sendDelayQueueMsg(String msg) {
        log.info("[ 延时消息发送 ] >> msg:{}, 发送时间：{}", msg, DateUtil.now());
        producer.delaySend(delayqueue, msg, 1000L * 10);
    }

    /**
     * 发送topic类型消息
     *
     * @param msg
     */
    @GetMapping("/topic")
    public void sendTopicMsg(String msg) {
        log.info("[ topic消息发送 ] >> msg:{}, 发送时间：{}", msg, DateUtil.now());
        producer.send((Destination) topic, msg);
    }

}
