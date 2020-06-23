package rabbitmq.demo01.cmdlineRunner;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rabbitmq.demo01.queueConfig.RabbitMqConfig;

import java.time.LocalTime;
import java.util.Date;

/**
 * @Auther:S
 * @Date:2020/6/23
 */

@Component
public class Example01Test implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        String content=null;
        for (int i=0; i<10; i++){
            content=LocalTime.now() +" from Example01Test";
            Thread.sleep(1000);
            rabbitTemplate.convertAndSend(RabbitMqConfig.queue01,content);
        }
    }
}
