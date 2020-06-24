package rabbit.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbit.email.common.ServerResult;
import rabbit.email.pojo.Mail;
import rabbit.email.service.MailSendService;

/**
 * @Auther:S
 * @Date:2020/6/24
 */
@RestController
public class MailController {
    @Autowired
    MailSendService mailSendService;

    @PostMapping("/send")
    public ServerResult sendMail(Mail mail, Errors errors){
        if(errors.hasErrors()){
            String msg = errors.getFieldError().getDefaultMessage();
            return ServerResult.error(msg);
        }
        return mailSendService.send(mail);
    }
}
