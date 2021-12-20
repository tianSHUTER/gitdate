package mq.kafka.tian.Config;

import org.springframework.kafka.annotation.KafkaListener;

public class KafakaListeners {

    @KafkaListener(topics = {"boot"})
    public void processMessage(String content) {

    }

}
