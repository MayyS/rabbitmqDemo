package rabbitmq.provider.config;

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

    /**路由键*/
    public static final String ROUTING="rpcService";

    /** 队列*/
    public static  final String QUEUE="rpcQueue";

    public static  final String REPLY_QUEQUE="rpcReplyQueque";
    public static  final String REPLY_TO="amq.rabbitmq.reply-to";

    /**交换机*/
    public static  final String EXCHANGE="rpcExchange";

    @Bean
    public Queue queueRpc(){
        return new Queue(QUEUE);
    }

    @Bean
    public Queue queueReply(){
        return new Queue(REPLY_QUEQUE);
    }
    @Bean
    DirectExchange rpcDirectExchange(){
        return new DirectExchange(EXCHANGE);
    }
    @Bean
    Binding bindingRcp(){
        return BindingBuilder.bind(queueRpc()).to(rpcDirectExchange()).with(ROUTING);
    }
}
