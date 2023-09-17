package com.pigeon.post.rabbitmq;

import com.pigeon.post.models._MailMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;
@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class Receiver {
    private CountDownLatch latch=new CountDownLatch(1);
    private static Logger logger = LogManager.getLogger(Receiver.class.toString());
    @RabbitHandler
    public void receiveMessage(_MailMessage message){
        logger.info("Mail listener invoked - Consuming Message: " + message.getMessageId());
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
