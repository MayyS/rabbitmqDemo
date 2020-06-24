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

    /**
     * 创建queuesingleMessage队列
     * @return
     */
    @Bean
    public Queue queuesingleMessage(){

        /*
        Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
        name:队列名字
        durable:是否开启持久化，默认true
        exclusive:表明该队列只能被当前创建的连接使用，连接关闭后列队被删除
        autoDelete：如果该队列(没有生产者或消费者)不在被使用是否删除

        Queue(String name) {
		    this(name, true, false, false);
		}
        */
        return new Queue(RabbitMqTopicConfig.SINGLE_MESSAGE);
    }
    @Bean
    public Queue queuemutiplyMessage(){
        return new Queue(RabbitMqTopicConfig.MUTIPLY_MESSAGE);
    }

    /**
     * 创建交换器
     * @return
     */
    @Bean
    public TopicExchange exchange(){
        /*
        AbstractExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
        */
        return new TopicExchange(RabbitMqTopicConfig.EXCHANGE);
    }

    /**
     * 交换器和队列进行绑定
     * @param queuesingleMessage
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage(Queue queuesingleMessage, TopicExchange exchange){
        return BindingBuilder.bind(queuesingleMessage).to(exchange).with(SINGLE_KEY);
    }
    @Bean
    public Binding bindingExchangeMessages(Queue queuemutiplyMessage,TopicExchange exchange){
        return BindingBuilder.bind(queuemutiplyMessage).to(exchange).with(MUTIPLY_KEY);
    }



}
