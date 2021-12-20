package io.kimmking.kmq.core;

import com.sun.tools.javac.util.GraphUtils;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;

public class tianQueue<KmqMessage> extends AbstractQueue<KmqMessage> implements java.io.Serializable{

    private static final long serialVersionUID = -6903933977591709194L;



    transient int head=0;

    /**
     * Tail of linked list.
     * Invariant: offset.next == null
     */
    private transient int offset=0;
    
    private ArrayList<KmqMessage> arrayList;

    public tianQueue(int leng){
        arrayList = new ArrayList<>(leng);
    }
    
    void insert(KmqMessage message){
        arrayList.add(message);
        offset++;
    }
    KmqMessage reduce (KmqMessage message){
        KmqMessage kmqMessage = arrayList.get(head);
        head++;
        return kmqMessage;
    }

    @Override
    public Iterator<KmqMessage> iterator() {
        return null;
    }

    @Override
    public int size() {
        return offset-head;
    }

    @Override
    public boolean offer(KmqMessage KmqMessage) {
        return false;
    }

    @Override
    public KmqMessage poll() {
        KmqMessage kmqMessage = arrayList.get(head);
        return kmqMessage;
    }

    public KmqMessage poll(int i) {
        KmqMessage kmqMessage = arrayList.get(i);
        return kmqMessage;
    }

    @Override
    public KmqMessage peek() {
        return arrayList.get(head);
    }
    public KmqMessage peek(int i ) {
        return arrayList.get(i);
    }
}
