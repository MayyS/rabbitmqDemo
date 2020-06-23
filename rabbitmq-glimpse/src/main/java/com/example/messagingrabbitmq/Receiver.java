package com.example.messagingrabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther:S
 * @Date:2020/6/23
 */

@Component
public class Receiver {

    private CountDownLatch latch=new CountDownLatch(1);

    public void receiveMessage(String message){
        System.out.println("Received<"+message+">");
       // latch.countDown();
    }

    public CountDownLatch getLatch(){
        return latch;
    }
}
