package rabbit.email.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import rabbit.email.pojo.Mail;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@Component
@Slf4j
public class MailUtil {
    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     *
     * @param mail
     */
    public boolean send(Mail mail){
        String to=mail.getTo();
        String title=mail.getTitle();
        String content=mail.getContent();
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        try {
            mailSender.send(message);
            //log.info("email send successfully");
            System.out.println("email send successfully");
            return true;
        }catch (MailException e){
            //log.error("email send failed,to{}, title:{}",to,title,e);
            System.out.println("email send failed");
            return false;
        }
    }

}
