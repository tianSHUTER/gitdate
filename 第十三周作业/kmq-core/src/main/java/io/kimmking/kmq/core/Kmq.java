package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class Kmq {

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new tianQueue(capacity);
    }

    private String topic;

    private int capacity;

    private tianQueue<KmqMessage> queue;

    public boolean send(KmqMessage message) {
        return queue.offer(message);
    }

    public KmqMessage poll() {
        return queue.poll();
    }

    @SneakyThrows
    public KmqMessage poll(int leng) {
        return queue.poll(leng);
    }

}
