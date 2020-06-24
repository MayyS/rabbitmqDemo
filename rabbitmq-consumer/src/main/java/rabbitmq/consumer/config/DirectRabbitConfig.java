package rabbitmq.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:S
 * @Date:2020/6/24
 * 添加该配置文件，消费者也可以当作生产者，如果不考虑的话可以不要该配置
 */

@Configuration
public class DirectRabbitConfig {

    /*路由键*/
    public static final String DIRECT_ROUTING="SoDirectRouting";

    /*队列*/
    public static  final String DIRECT_QUEUE="SoDirectQueue";

    /*交换机*/
    public static  final String DIRECT_EXCHANGE="SoDirectQueue";


    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue SoDirectQueue(){
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
        return new Queue(DIRECT_QUEUE);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    DirectExchange SoDirectExchange(){
        /*
        AbstractExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
        */
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    /**
     * 绑定主题和队列
     * @return
     */
    @Bean
    Binding bindingDirect(){
        return BindingBuilder.bind(SoDirectQueue()).to(SoDirectExchange()).with(DIRECT_ROUTING);
    }
}
