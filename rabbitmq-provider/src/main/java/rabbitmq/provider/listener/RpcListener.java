package rabbitmq.provider.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther:S
 * @Date:2020/6/26
 */

@Component
public class RpcListener {

    /**
     * 使用指定通道来获得rpc放回值
     * @param reply
     * @param map
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "rpcReplyQueue",durable = "fasle"),
                    exchange = @Exchange(name = "rpcReplyExchange",durable = "false"),
                    key = "rpcReply"
            )
    })
    public void process(@Payload String reply, @Headers Map<String,Object> map, Channel channel,Message message) throws IOException {
        System.out.println("RpcListener-->");
        System.out.println("reply : "+reply);
        MessageProperties messageProperties= message.getMessageProperties();
        System.out.println("-->end");
        long tag = (long) map.get(AmqpHeaders.DELIVERY_TAG);
        //确认
        channel.basicAck(tag,true);

    }

}
