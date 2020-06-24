package rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther:S
 * @Date:2020/6/24
 */


@SpringBootApplication
public class ConsumerApp {
    public static void main(String[]args){
        SpringApplication.run(ConsumerApp.class,args);
    }
}
