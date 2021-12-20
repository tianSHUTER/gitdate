package mq.kafka.tian.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class kafkaController {
    @Autowired
    KafkaTemplate kafkaTemplate;


    @RequestMapping("/Kafka")
    @ResponseBody
    public void testkafka() throws Exception {

    }

}
