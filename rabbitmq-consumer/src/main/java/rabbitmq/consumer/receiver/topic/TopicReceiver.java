package rabbitmq.consumer.receiver.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.consumer.config.TopicRabbitConfig;

import java.util.Map;

/**
 * @Auther:S
 * @Date:2020/6/24
 */

@Component
public class TopicReceiver {

    @RabbitListener(queues = TopicRabbitConfig.QUEUE_ALL)
    public void all(Map map){
        System.out.println("TopicReceiver-ALL : "+map.toString());
    }

    @RabbitListener(queues = TopicRabbitConfig.QUEUE_SOME)
    public void some(Map map){
        System.out.println("TopicReceiver-some : "+map.toString());
    }
}
