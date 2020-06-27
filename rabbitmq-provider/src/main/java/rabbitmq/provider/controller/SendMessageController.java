package rabbitmq.provider.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbitmq.provider.config.DirectRabbitConfig;
import rabbitmq.provider.config.FanoutRabbitConfig;
import rabbitmq.provider.config.RpcRabbitConfig;
import rabbitmq.provider.config.TopicRabbitConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther:S
 * @Date:2020/6/24
 */

@RestController
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(){
        //创建map消息
        String msgId=String.valueOf(UUID.randomUUID());
        String msgData="So-Direct-Message";
        String createTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object>map=new HashMap<>();
        map.put("messageId",msgId);
        map.put("messageData",msgData);
        map.put("createTime",createTime);
        //将消息携带绑定键值
        rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE,DirectRabbitConfig.DIRECT_ROUTING,map);
        return "ok";
    }

    @GetMapping("/sendTopicMessageAll")
    public String sendTopicMessageAll(){
        //创建map消息
        String msgId=String.valueOf(UUID.randomUUID());
        String msgData="So-Topic-ALL-Message";
        String createTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object>map=new HashMap<>();
        map.put("messageId",msgId);
        map.put("messageData",msgData);
        map.put("createTime",createTime);
        //将消息携带绑定键值
        rabbitTemplate.convertAndSend(TopicRabbitConfig.Topic_EXCHANGE,TopicRabbitConfig.ROUTING_ALL,map);
        return "ok";
    }

    @GetMapping("/sendTopicMessageSome")
    public String sendTopicMessageSome(){
        //创建map消息
        Map<String,Object>map=getMessage();
        //将消息携带绑定键值
        rabbitTemplate.convertAndSend(TopicRabbitConfig.Topic_EXCHANGE,TopicRabbitConfig.ROUTING_SOME,map);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage(){
        //创建map消息
        Map<String,Object>map=getMessage();
        //将消息携带绑定键值
        rabbitTemplate.convertAndSend(FanoutRabbitConfig.EXCHANGE,null,map);
        return "ok";
    }

    /*向不存在的交换机发送消息，触发ConfirmCallback*/
    @GetMapping("/sendToExchangeNonExist")
    public String sendToExchangeNonExist(){
        String exchange="non-exist";
        //创建map消息
        Map<String,Object>map=getMessage();
        //将消息携带绑定键值
        rabbitTemplate.convertAndSend(exchange,null,map);
        return "ok";
    }
    /*
    存在交换机，但是不存在队列，ReturnCallback，ConfirmCallback
    ConfirmCallback -- correlationData ：null
    ConfirmCallback -- ack ：true
    ConfirmCallback -- cause ：null
    ---------
    ReturnCallback--replyCode : 312
    ReturnCallback--replyText : NO_ROUTE
    */
    @GetMapping("/sendToQueueNonExist")
    public String sendToQueueNonExist(){
        String routing="non-queque";
        //创建map消息
        Map<String,Object>map=getMessage();
        //将消息携带绑定键值
        rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_LONELY_EXCHANGE,routing,map);
        return "ok";
    }

    @GetMapping("/sendRpc")
    //public Message sendRpc(){
    public String sendRpc(){
        //创建map消息
        Map<String,Object>map=getMessage();
        //设置发送到exchange到回调函数

        //一个rabbitTemplate只能设置一个ConfirmCallback 如果重复设置会报IllegalStateException
        /*RabbitTemplate.ConfirmCallback confirmCallback=new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("Rpc confirmCallback: "+correlationData+"\n ack: "+ack+" \n case:"+cause);
            }
        };
        rabbitTemplate.setConfirmCallback(confirmCallback);*/
        //同上
        RabbitTemplate.ReturnCallback returnCallback=new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Rpc returnCallback : \n message:"+message+" \n replyCode"+replyCode+" \n exchange"+exchange );
            }
        };
        //rabbitTemplate.setReturnCallback(returnCallback);
        //封装消息属性，broker回调时标识
        CorrelationData correlationData=new CorrelationData();
        String id="123456";
        correlationData.setId(id);
        //exchange used
        String usedExchange=rabbitTemplate.getExchange();
        //reply
        String replyAdr="amq.rabbitmq.reply-to";
        rabbitTemplate.setReplyAddress(replyAdr);
        rabbitTemplate.convertAndSend(RpcRabbitConfig.EXCHANGE,RpcRabbitConfig.ROUTING,map,correlationData);
        return "oka";

        //利用同步的方式来接受消息
        //Message message=getMessage(map);
        //Message rplyStr = rabbitTemplate.sendAndReceive(RpcRabbitConfig.EXCHANGE, RpcRabbitConfig.ROUTING, message);
        //return rplyStr;
    }

    private Message getMessage(Map map){

        String str=map.toString();
        byte[]body=str.getBytes();
        String mid="123456";
        String replyAdr="amq.rabbitmq.reply-to";

        MessageProperties properties=new MessageProperties();
        properties.setMessageId(mid);
        properties.setReplyTo(replyAdr);

        Message message=new Message(body,properties);
        return message;

    }


    private Map<String,Object> getMessage(){
        //创建map消息
        String msgId=String.valueOf(UUID.randomUUID());
        String msgData="So-Topic-SOME-Message";
        String createTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object>map=new HashMap<>();
        map.put("messageId",msgId);
        map.put("messageData",msgData);
        map.put("createTime",createTime);
        return map;
    }



}
