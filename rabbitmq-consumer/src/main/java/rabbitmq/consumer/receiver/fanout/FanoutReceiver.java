package rabbitmq.consumer.receiver.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.consumer.config.FanoutRabbitConfig;

import java.util.Map;

/**
 * @Auther:S
 * @Date:2020/6/24
 */

@Component
public class FanoutReceiver {

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_A)
    public void processA(Map map){
        System.out.println("FanoutReceiver-processA : "+map.toString());
    }

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_B)
    public void processB(Map map){
        System.out.println("FanoutReceiver-processB : "+map.toString());
    }
    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_C)
    public void processC(Map map){
        System.out.println("FanoutReceiver-processC : "+map.toString());
    }
}
