package rabbitmq.consumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.consumer.config.DirectRabbitConfig;

import java.util.Map;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@Component
@RabbitListener(queues = DirectRabbitConfig.DIRECT_QUEUE)
public class DirectReceiver {

    @RabbitHandler
    public void process(Map msg){
        System.out.println("DirectReceiver :"+msg.toString());
    }
}
