package rabbitmq.consumer.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import rabbitmq.consumer.config.DirectRabbitConfig;
import rabbitmq.consumer.config.RpcRabbitConfig;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@Component
public class DirectReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String rabbitExchange="rabbitExchange";
    private String rabbitQueue="rabbitQueue";
    private String rabbitRouting="rabbitRouting";

    @RabbitListener(queues = DirectRabbitConfig.DIRECT_QUEUE)
    public void process(Map msg){
        System.out.println("DirectReceiver process:"+msg.toString());
    }

    @RabbitListener
    public void defaultProcess(MessageProperties messageProperties){
        System.out.println("default process: "+messageProperties.toString());
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(name ="rabbitQueue",durable = "false"),
            exchange = @Exchange(name = "rabbitExchange", durable = "false"),
                    key = "rabbitRouting"
            )
    })
    public void prcoessRabbitListener(Map map){
        System.out.println("prcoessRabbitListener : "+map.toString());
    }

    @RabbitListener(queues = RpcRabbitConfig.QUEUE, ackMode = "MANUAL" /* concurrency = "5" */)
    public void processRpc01(@Payload  Map data, @Headers Map<String,Object>map, Channel channel,Message message) throws IOException {
        //耗时比较长的操作应该比耗时比较短的少获得任务，而不是轮询派发
        //channel.basicQos(1,true);
        //System.out.println("processRpc___01 begin: ");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //@Payload
        System.out.println(data);

        //headers
        long deliverTag= (long) map.get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("deliverTag : "+deliverTag);
        //delivermode
        String delivermode=map.get(AmqpHeaders.DELIVERY_MODE)==null ? "" : map.get(AmqpHeaders.DELIVERY_MODE).toString();
        //rely-to
        //String relpy_to=map.get(AmqpHeaders.REPLY_TO).toString();
        //String correlationId = map.get(AmqpHeaders.CORRELATION_ID).toString();
        String correlationId = map.get("spring_returned_message_correlation").toString();
        //System.out.println("correlationId : "+correlationId);



        boolean multiply=false;
        boolean requeue=true;

        System.out.println("processRpc___01 end : ");
        String reply="processRpc___01 OK...";
        rabbitTemplate.convertAndSend("rpcReplyExchange","rpcReply",reply);
       // channel.basicNack(deliverTag,multiply,requeue);
        channel.basicAck(deliverTag,multiply);
    }
    //
    //@RabbitListener(queues = RpcRabbitConfig.QUEUE, ackMode = "MANUAL")
    //public void processRpc02(@Payload  Map data, @Headers Map<String,Object>map, Channel channel) throws IOException {
    //    channel.basicQos(1,true);
    //    //System.out.println("processRpc__02 begin : ");
    //    try {
    //        TimeUnit.SECONDS.sleep(2);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    //@Payload
    //    System.out.println(data);
    //
    //    //headers
    //    long deliverTag= (long) map.get(AmqpHeaders.DELIVERY_TAG);
    //    System.out.println("deliverTag : "+deliverTag);
    //
    //    //String correlationId = map.get(AmqpHeaders.CORRELATION_ID).toString();
    //    String correlationId = map.get("spring_returned_message_correlation").toString();
    //    //System.out.println("correlationId : "+correlationId);
    //    boolean multiply=false;
    //    boolean requeue=true;
    //
    //    //channel.basicNack(deliverTag,multiply,requeue);
    //    channel.basicAck(deliverTag,multiply);
    //    System.out.println("processRpc__02 end : ");
    //}

}
