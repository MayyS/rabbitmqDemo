package rabbitmq.topic.cmdlineRunner;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rabbitmq.topic.config.RabbitMqTopicConfig;

/**
 * @Auther:S
 * @Date:2020/6/23
 */

@Component
public class Topic01Test implements CommandLineRunner {
    @Autowired
    private RabbitTemplate template;

    @Override
    public void run(String... args) throws Exception {
        String content="hello";
        template.convertAndSend(RabbitMqTopicConfig.EXCHANGE,RabbitMqTopicConfig.SINGLE_MESSAGE,content);
        content="world";
        template.convertAndSend(RabbitMqTopicConfig.EXCHANGE,RabbitMqTopicConfig.MUTIPLY_MESSAGE,content);

    }
}
