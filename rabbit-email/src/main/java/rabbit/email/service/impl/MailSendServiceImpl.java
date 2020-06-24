package rabbit.email.service.impl;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;
import rabbit.email.common.ServerResult;
import rabbit.email.config.RabbitmqConfig;
import rabbit.email.helper.MessageHelper;
import rabbit.email.mapper.MsgLogMapper;
import rabbit.email.pojo.Mail;
import rabbit.email.pojo.MsgLog;
import rabbit.email.service.MailSendService;

import java.util.UUID;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@Service
public class MailSendServiceImpl implements MailSendService {

    @Autowired
    private MsgLogMapper msgLogMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ServerResult send(Mail mail) {
        String msgId= UUID.randomUUID().toString().replaceAll("-", "");
        mail.setMsgId(msgId);

        MsgLog msgLog=new MsgLog(msgId,mail, RabbitmqConfig.MAIL_EXCHANGE_NAME,RabbitmqConfig.MAIL_ROUTING_KEY_NAME);
        msgLogMapper.insert(msgLog);

        CorrelationData correlationData=new CorrelationData(msgId);

        //MAIL_EXCHANGE_NAME不存在，回调ConfirmCallback函数提示失败
        //rabbitTemplate.convertAndSend("hoho",RabbitmqConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail),correlationData);
        //如果MAIL_ROUTING_KEY_NAME不存在，则会调用ReturnCallback（312）、ConfirmCallback(成功)
        //rabbitTemplate.convertAndSend(RabbitmqConfig.MAIL_EXCHANGE_NAME,hoho, MessageHelper.objToMsg(mail),correlationData);


        rabbitTemplate.convertAndSend(RabbitmqConfig.MAIL_EXCHANGE_NAME,RabbitmqConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail),correlationData);

        return ServerResult.success();

    }

}
