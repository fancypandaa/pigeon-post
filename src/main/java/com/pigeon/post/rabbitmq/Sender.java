package com.pigeon.post.rabbitmq;

import com.pigeon.post.models._MailMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private Queue queue;
    private static Logger logger = LogManager.getLogger(Sender.class.toString());
    public void send(_MailMessage mailMessage){
        rabbitTemplate.convertAndSend(queue.getName(),mailMessage);
        logger.info("Sending Message to the Queue : "+mailMessage.getMessageId());
    }
}
