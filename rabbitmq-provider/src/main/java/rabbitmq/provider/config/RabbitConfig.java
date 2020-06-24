package rabbitmq.provider.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(factory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback -- "+"correlationData ："+correlationData);
                System.out.println("ConfirmCallback -- "+"ack ："+ack);
                System.out.println("ConfirmCallback -- "+"cause ："+cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback--message : "+message);
                System.out.println("ReturnCallback--replyCode : "+replyCode);
                System.out.println("ReturnCallback--replyText : "+replyText);
                System.out.println("ReturnCallback--exchange : "+exchange);
                System.out.println("ReturnCallback--routingKey : "+routingKey);
            }
        });


        return rabbitTemplate;
    }


}
