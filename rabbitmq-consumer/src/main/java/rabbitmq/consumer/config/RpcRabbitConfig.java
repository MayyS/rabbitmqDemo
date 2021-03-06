package rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:S
 * @Date:2020/6/26
 */
@Configuration
public class RpcRabbitConfig {

    /**
     * 路由键
     */
    public static final String ROUTING="rpcService";

    /**
     *队列
     */
    public static  final String QUEUE="rpcQueue";

    /**
     * 交换机
     */

    public static  final String EXCHANGE="rpcExchange";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }
    @Bean
    DirectExchange rpcDirectExchange(){
        return new DirectExchange(EXCHANGE);
    }
    @Bean
    Binding bindingRcp(){
        return BindingBuilder.bind(queue()).to(rpcDirectExchange()).with(ROUTING);
    }
}
