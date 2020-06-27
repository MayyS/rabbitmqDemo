package rabbitmq.consumer.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rabbitmq.consumer.receiver.ack.AckReceiver;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@Configuration
public class MessageListenerConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private AckReceiver ackReceiver;

    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);

        //设置监听容器

        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        //设置确认方式，默认是自动去人
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置队列
        container.setQueueNames(DirectRabbitConfig.DIRECT_QUEUE);
        //
        container.setMessageListener(ackReceiver);


        return container;
    }

    @Bean
    public SimpleMessageListenerContainer myRpcListenerContainer(){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);

        //设置监听容器

        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        //设置确认方式，默认是自动去人
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置队列
        container.setQueueNames(DirectRabbitConfig.DIRECT_QUEUE);
        return container;
    }


}
