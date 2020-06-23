package rabbitmq.topic.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.topic.config.RabbitMqTopicConfig;

/**
 * @Auther:S
 * @Date:2020/6/23
 */
@Component
@RabbitListener(queues = RabbitMqTopicConfig.MUTIPLY_MESSAGE)
public class Receiver02 {

    @RabbitHandler
    public void process(String msg){
        System.out.println("Topic-Receiver02 : "+msg);
    }
}
