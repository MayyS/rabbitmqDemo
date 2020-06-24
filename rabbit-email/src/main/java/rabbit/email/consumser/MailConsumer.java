package rabbit.email.consumser;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbit.email.common.Constant;
import rabbit.email.config.RabbitmqConfig;
import rabbit.email.helper.MessageHelper;
import rabbit.email.pojo.Mail;
import rabbit.email.pojo.MsgLog;
import rabbit.email.service.MsgLogService;
import rabbit.email.util.MailUtil;

import java.io.IOException;

/**
 * @Auther:S
 * @Date:2020/6/24
 */

@Component
public class MailConsumer {
    @Autowired
    private MsgLogService msgLogService;
    @Autowired
    private MailUtil mailUtil;

    @RabbitListener(queues = RabbitmqConfig.MAIL_QUEUE_NAME)
    public void consumer(Message msg, Channel channel) throws IOException {
        Mail mail= MessageHelper.msgToObj(msg,Mail.class);
        System.out.println("receiver msg : "+mail.toString());

        String msgId=mail.getMsgId();

        MsgLog msgLog=msgLogService.selectByMsgId(msgId);

        //保证信息幂等
        if(null==msgLog||msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)){
            System.out.println("had receiver before, don't repeat!!!");
            return;
        }

        MessageProperties properties=msg.getMessageProperties();
        long tag=properties.getDeliveryTag();
        boolean success=mailUtil.send(mail);
        if(success){
            msgLogService.updateStatus(msgId,Constant.MsgLogStatus.CONSUMED_SUCCESS);
            //第二个参数表示仅仅对当前的消息进行确认
            //如果注释掉，消息会被持久化
            channel.basicAck(tag,false);
        }else{
            //第三个参数表示将该信息插回到队列中去。
            channel.basicNack(tag,false,true);
        }
    }
}
