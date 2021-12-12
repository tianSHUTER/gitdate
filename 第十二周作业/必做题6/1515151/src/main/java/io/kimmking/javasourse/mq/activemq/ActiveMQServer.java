package io.kimmking.javasourse.mq.activemq;

import org.apache.activemq.broker.BrokerService;

public class ActiveMQServer {

    public static void main(String[] args) {
        // 尝试用java代码启动一个ActiveMQ broker server
        BrokerService brokerService = new BrokerService();
        try
        {
            brokerService.setUseJmx(true);
            brokerService.addConnector("tcp://localhost:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 然后用前面的测试demo代码，连接这个嵌入式的server
    }
}
