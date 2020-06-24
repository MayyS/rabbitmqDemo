package rabbit.email.service;

import rabbit.email.common.ServerResult;
import rabbit.email.pojo.Mail;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
public interface MailSendService {
    ServerResult send(Mail mail);
}
