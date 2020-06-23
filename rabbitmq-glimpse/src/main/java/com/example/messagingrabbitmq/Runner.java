package com.example.messagingrabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Auther:S
 * @Date:2020/6/23
 */

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    private final Receiver receiver;

    public Runner(Receiver receiver,RabbitTemplate rabbitTemplate) {
        this.receiver=receiver;
        this.rabbitTemplate=rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        //模板使用foo.bar.baz与绑定匹配的路由键路由消息到交换机
        rabbitTemplate.convertAndSend(MessagingRabbitApplication.topicExchangeNmae,"foo.bar.baz","Hell from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
