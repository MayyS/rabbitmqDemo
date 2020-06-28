package rabbitmq.consumer.receiver;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Auther:S
 * @Date:2020/6/28
 */
public class SimpleConsumer extends DefaultConsumer {

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public SimpleConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("handleDelivery  start ---->"+consumerTag);
        String content=new String(body);
        System.out.println("SimpleConsumer : "+content);
        //rpc数据交互的两个参数
        String reply=properties.getReplyTo();
        String id=properties.getCorrelationId();
        System.out.println("header :  "+properties.getHeaders().toString());

        AMQP.BasicProperties props=new AMQP.BasicProperties.Builder().correlationId(id).build();
        String rpy="msg from SimpleConsumer";
        this.getChannel().basicPublish("",reply,props,rpy.getBytes());
        System.out.println("handleDelivery  end ---->");
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        super.handleCancel(consumerTag);
        System.out.println("handleCancel ---->"+consumerTag);
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        super.handleConsumeOk(consumerTag);
        System.out.println("handleConsumeOk ---->"+consumerTag);
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        super.handleCancelOk(consumerTag);
        System.out.println("handleCancelOk ---->");
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        super.handleRecoverOk(consumerTag);
        System.out.println("handleRecoverOk ---->");
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        super.handleShutdownSignal(consumerTag, sig);
        System.out.println("handleShutdownSignal ---->");
    }
}
