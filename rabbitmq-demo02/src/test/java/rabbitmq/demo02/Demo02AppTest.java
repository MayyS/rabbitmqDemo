package rabbitmq.demo02;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rabbitmq.demo02.bean.Book;

/**
 * @Auther:S
 * @Date:2020/6/23
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo02AppTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String DIRECT="exchange.direct";
    private static final String FANOUT="exchange.fanout";
    private static final String TOPIC="exchange.topic";

    private static final String KEY_DEMO="demo";
    private static final String KEY_EMPS="demo.emps";
    private static final String KEY_NEWS="demo.news";
    private static final String KEY_NEAS="demo.neas";

    @Test
    public void contextLoad(){

        String content="hello,世界";
        /*使用message传输
        byte[]bytes=content.getBytes();
        //建立消息属性
        MessageProperties messageProperties=new MessageProperties();
        //自定义Message
        Message message=new Message(bytes,messageProperties);
        rabbitTemplate.send(DIRECT,KEY_DEMO,message);
        */
        Book book=new Book("我","你");
        //使用messageConverter

        //广播
        rabbitTemplate.convertAndSend(FANOUT,KEY_DEMO,book);


        //单播
        book.setBookName(TOPIC+KEY_DEMO);
        rabbitTemplate.convertAndSend(TOPIC,KEY_DEMO,book);
        book.setBookName(TOPIC+KEY_EMPS);
        rabbitTemplate.convertAndSend(TOPIC,KEY_EMPS,book);

    }

    @Test
    public void receive(){
        Object obj=rabbitTemplate.receiveAndConvert(KEY_NEWS);
        if(obj==null){
            return;
        }
        System.out.println(obj.getClass());
        //String txt=new String((byte[]) obj);
        //System.out.println(txt);
    }

}