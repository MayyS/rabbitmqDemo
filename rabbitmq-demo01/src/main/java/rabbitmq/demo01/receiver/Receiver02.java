package rabbitmq.demo01.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.demo01.queueConfig.RabbitMqConfig;

/**
 * @Auther:S
 * @Date:2020/6/23
 */

@Component
@RabbitListener(queues = {RabbitMqConfig.queue01,RabbitMqConfig.queue02})
public class Receiver02 {

    @RabbitHandler
    public void receiver(String msg){
        System.out.println("Receiver02 : "+msg);
    }
}
