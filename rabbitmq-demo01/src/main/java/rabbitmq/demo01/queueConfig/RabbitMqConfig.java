package rabbitmq.demo01.queueConfig;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther:S
 * @Date:2020/6/23
 */
@Configuration
public class RabbitMqConfig {
    public static final String queue01="demo01-queue1";
    public static final String queue02="demo01-queue2";

    @Bean
    public Queue queue01(){
        return new Queue(queue01);
    }

    @Bean
    public Queue queue02(){
        return new Queue(queue02);
    }
}
