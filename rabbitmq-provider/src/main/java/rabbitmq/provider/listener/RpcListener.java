package rabbitmq.provider.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @Auther:S
 * @Date:2020/6/26
 */

@Component
public class RpcListener {

   /* @RabbitListener(queues = "amq.rabbitmq.reply-to")
    public void process(Message message, @Header Header header){
        System.out.println(header);
    }*/

}
