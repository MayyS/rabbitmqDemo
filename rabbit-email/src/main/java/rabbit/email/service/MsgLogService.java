package rabbit.email.service;

import rabbit.email.pojo.MsgLog;

import java.util.Date;
import java.util.List;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
public interface MsgLogService {

    void updateStatus(String msgId, Integer status);

    MsgLog selectByMsgId(String msgId);

    List<MsgLog> selectTimeoutMsg();

    void updateTryCount(String msgId, Date tryTime);

}
