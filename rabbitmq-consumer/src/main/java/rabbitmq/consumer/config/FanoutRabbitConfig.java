package rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:S
 * @Date:2020/6/24
 */

@Configuration
public class FanoutRabbitConfig {

    /*路由键:扇型交换机, 路由键无需配置,配置也不起作用*/
    /*队列*/
    public static  final String QUEUE_A="fanout.a";
    public static final String QUEUE_B="fanout.b";
    public static final String QUEUE_C="fanout.c";

    /*交换机*/
    public static  final String EXCHANGE="SoFanoutExchange";


    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue queueA(){
        return new Queue(QUEUE_A);
    }
    @Bean
    public Queue queueB(){
        return new Queue(QUEUE_B);
    }
    @Bean
    public Queue queueC(){
        return new Queue(QUEUE_C);
    }
    /**
     * 创建交换机
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE);
    }

    /**
     * 绑定主题和队列
     * @return
     */
    @Bean
    Binding bindingExchangeA(){
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeB(){
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }
    @Bean
    Binding bindingExchangeC(){
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }
}
