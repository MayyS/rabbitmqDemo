package rabbit.email.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Auther:S
 * @Date:2020/6/24
 */


public class Mail {
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String to;

    @NotBlank(message = "标题不能为空")
    private String content;
    @NotBlank(message = "正文不能为空")
    private String title;

    private String msgId;

    public Mail(@Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确") String to, @NotBlank(message = "标题不能为空") String content, @NotBlank(message = "正文不能为空") String title, String msgId) {
        this.to = to;
        this.content = content;
        this.title = title;
        this.msgId = msgId;
    }

    public Mail() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "to='" + to + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
