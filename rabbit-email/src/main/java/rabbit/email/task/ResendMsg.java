package rabbit.email.task;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rabbit.email.common.Constant;
import rabbit.email.helper.MessageHelper;
import rabbit.email.pojo.MsgLog;
import rabbit.email.service.MsgLogService;

import java.util.List;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@Component
public class ResendMsg {
    @Autowired
    private MsgLogService msgLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 最大的投递次数
     */
    private static final int MAX_TRY_COUNT=3;

    @Scheduled(cron="0/30 * * * * ?")
    public void resend(){
        System.out.println("Scheduled Tasked staring...");
        List<MsgLog>msgLogs=msgLogService.selectTimeoutMsg();
        msgLogs.forEach(msgLog -> {
            String msgId=msgLog.getMsgId();
            if(msgLog.getTryCount()>=MAX_TRY_COUNT){
                msgLogService.updateStatus(msgId, Constant.MsgLogStatus.DELIVER_FAIL);
                System.out.println("try limit...");
            }else{

                //???
                msgLogService.updateTryCount(msgId,msgLog.getNextTryTime());
                CorrelationData correlationData=new CorrelationData(msgId);
                //所有的消息都公用这个定时任务
                rabbitTemplate.convertAndSend(msgLog.getExchange(),msgLog.getRoutingKey(), MessageHelper.objToMsg(msgLog.getMsg()),correlationData);
                System.out.println("no."+msgLog.getTryCount()+1+"try->");

            }
        });
        System.out.println("Scheduled Tasked ending...");
    }
}
