package rabbitmq.consumer.receiver.ack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther:S
 * @Date:2020/6/24
 */

@Component
public class AckReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties msgProperties=message.getMessageProperties();

        long deliverTag=msgProperties.getDeliveryTag();
        try {
            String msg=message.toString();
            String[]msgArray=msg.split("'");
            Map<String,String>msgMap=mapStringToMap(msgArray[1].trim());
            String msgId=msgMap.get("messageId");
            String msgData=msgMap.get("messageData");
            String createTime=msgMap.get("createTime");
            System.out.println("AckReceiver : "+msgMap.toString());
            channel.basicAck(deliverTag,true);
        }catch (Exception e){
            //channel.basicReject(deliveryTag, true);//为true会重新放回队列
            channel.basicReject(deliverTag,false);
            e.printStackTrace();
        }

    }

    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
