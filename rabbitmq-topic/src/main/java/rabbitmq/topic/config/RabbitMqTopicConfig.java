package rabbitmq.topic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther:S
 * @Date:2020/6/23
 */

@Configuration
public class RabbitMqTopicConfig {

    /**
     *只接收一个topic
     */
    public final static String SINGLE_MESSAGE="topic.message";
    final  static String SINGLE_KEY="topic.message";
    /**
     * 可接收多个topic
     */
    public final static String MUTIPLY_MESSAGE="topic.messages";
    final  static String MUTIPLY_KEY="topic.#";

    public final static String EXCHANGE="exchange";

    @Bean
    public Queue queuesingleMessage(){
        return new Queue(RabbitMqTopicConfig.SINGLE_MESSAGE);
    }
    @Bean
    public Queue queuemutiplyMessage(){
        return new Queue(RabbitMqTopicConfig.MUTIPLY_MESSAGE);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(RabbitMqTopicConfig.EXCHANGE);
    }
    @Bean
    public Binding bindingExchangeMessage(Queue queuesingleMessage, TopicExchange exchange){
        return BindingBuilder.bind(queuesingleMessage).to(exchange).with(SINGLE_KEY);
    }
    @Bean
    public Binding bindingExchangeMessages(Queue queuemutiplyMessage,TopicExchange exchange){
        return BindingBuilder.bind(queuemutiplyMessage).to(exchange).with(MUTIPLY_KEY);
    }



}
