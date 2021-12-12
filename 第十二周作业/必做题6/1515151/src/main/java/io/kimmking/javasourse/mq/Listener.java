package io.kimmking.javasourse.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Listener {
    @JmsListener(destination = "springboot.queue")
    public void receiveMsg(String message){
        Thread thread = Thread.currentThread();
        log.info("[ 普通消息消费 ] >> 线程ID:{},线程名称:{},消息内容:{}", new Object[]{thread.getId(), thread.getName(), message});
    }
}
