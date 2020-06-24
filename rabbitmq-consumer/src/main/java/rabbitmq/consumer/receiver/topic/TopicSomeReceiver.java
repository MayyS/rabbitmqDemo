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
@RabbitListener(queues = TopicRabbitConfig.QUEUE_SOME)
public class TopicSomeReceiver {

    @RabbitHandler
    public void process(Map map){
        System.out.println("TopicSomeReceiver : "+map.toString());
    }
}
