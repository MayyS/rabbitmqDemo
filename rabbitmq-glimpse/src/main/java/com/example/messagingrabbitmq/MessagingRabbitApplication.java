package com.example.messagingrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Auther:S
 * @Date:2020/6/23
 */
@SpringBootApplication
public class MessagingRabbitApplication {

    static final String topicExchangeNmae="spring-boot-exchange";

    static final String queuqeName="spring-boot";

    /**
     * 创建一个AMQP队列
     * @return
     */
    @Bean
    Queue queue(){
        //列队名称，是否开启持久化
        return new Queue(queuqeName,false);
    }

    /**
     * 创建主题交换
     * @return
     */
    @Bean
    TopicExchange exchange(){
        return new TopicExchange(topicExchangeNmae);
    }

    /**
     * 绑定主题和队列
     * @param queue
     * @param topicExchange
     * @return
     */
    @Bean
    Binding binding(Queue queue,TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(exchange()).with("foo.bar.#");
    }

    /**
     * 消息侦听器容器
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //监听spring-boot队列中的消息
        container.setQueueNames(queuqeName);
        //设置监听器
        container.setMessageListener(listenerAdapter);
        return container;
    }
    /**
     * 消息侦听器
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }


    public static void main(String[]args) {
        SpringApplication.run(MessagingRabbitApplication.class,args);
    }
}
