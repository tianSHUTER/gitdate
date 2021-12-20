package kafka.demo.tian.Listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

public class lis{
    @KafkaListener(topics = {"${spring.kafka.consumer.topic}"},containerFactory = "KafkaListenerContainerFactory")
    public void getCarAlarmFromKafka(ConsumerRecord record){
        Optional<Object> value = Optional.ofNullable(record.value());
        if (value.isPresent()){
            Object o = value.get();
            System.out.println(o);
        }

    }
}
