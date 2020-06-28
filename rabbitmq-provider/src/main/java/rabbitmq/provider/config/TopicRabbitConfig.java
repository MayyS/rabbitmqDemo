package rabbitmq.provider.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:S
 * @Date:2020/6/24
 */

@Configuration
public class TopicRabbitConfig {

    /**路由键*/
    public static final String ROUTING_ALL="topic.#";
    public static final String ROUTING_SOME="topic.SOME";

    /**队列*/
    public static  final String QUEUE_ALL="topic.ALL";
    public static final String QUEUE_SOME="topic.SOME";

    /**交换机*/
    public static  final String Topic_EXCHANGE="SoTopicExchange";


    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue allQueue(){
        return new Queue(QUEUE_ALL);
    }
    @Bean
    public Queue someQueue(){
        return new Queue(QUEUE_SOME);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    TopicExchange soTopicExchange(){
        return new TopicExchange(Topic_EXCHANGE);
    }

    /**
     * 绑定主题和队列
     * 只要是消息携带的路由键是topic.开头,就会分发到该队列
     * @return
     */
    @Bean
    Binding bindingTopicAll(){
        return BindingBuilder.bind(allQueue()).to(soTopicExchange()).with(ROUTING_ALL);
    }

    /**
     * 绑定主题和队列
     * 只有是消息携带的路由键是topic.SOME,才会分发到该队列
     * @return
     */
    @Bean
    Binding bindingTopicSome(){
        return BindingBuilder.bind(someQueue()).to(soTopicExchange()).with(ROUTING_SOME);
    }

}
