package com.pigeon.post.mail.receiver;

import com.pigeon.post.Services.IMAPService;
import com.pigeon.post.mailBuilder.MailReceiverService;
import com.pigeon.post.models.IMAPInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.MailReceiver;
import org.springframework.integration.mail.MailReceivingMessageSource;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;


@Slf4j
@EnableAsync
@EnableIntegration
@Configuration()
public class MailReceiverConfiguration {
    private final MailReceiverService receiveMailService;
    private final IMAPService imapService;
    private IMAPInfo imapInfo;
    @Value("${mail.imap.username}")
    private String userName;

    @Value("${mail.imap.password}")
    private String password;

    @Value("${mail.imap.host}")
    private String host;

    @Value("${mail.imap.port}")
    private String port;

    public MailReceiverConfiguration(MailReceiverService receiveMailService, IMAPService imapService) {
        this.receiveMailService = receiveMailService;
        this.imapService = imapService;
    }

    @ServiceActivator(inputChannel = "receiveEmailChannel")
    public void receive(Message<?> message) {
        receiveMailService.handleReceivedMail((MimeMessage) message.getPayload());
    }
    @Bean
    public List<IMAPInfo> imapInfoFlux(){
       return this.imapService.listAllIMap().collectList().block();
    }
    @Bean("receiveEmailChannel")
    public DirectChannel defaultChannel() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setDatatypes(javax.mail.internet.MimeMessage.class);
        return directChannel;
    }

    @Bean()
    @InboundChannelAdapter(
            channel = "receiveEmailChannel",
            poller = @Poller(fixedDelay = "50000", taskExecutor = "asyncTaskExecutor"))
    public MailReceivingMessageSource mailMessageSource(MailReceiver mailReceiver) {
        return new MailReceivingMessageSource(mailReceiver);
    }

    @Bean
    public MailReceiver imapMailReceiver() {
                 String storeUrl =
                    "imaps://"
                            + URLEncoder.encode(userName, StandardCharsets.UTF_8)
                            + ":"
                            + password
                            + "@"
                            + host
                            + ":"
                            + port
                            + "/INBOX";

            log.info("IMAP connection url: {}", storeUrl);

            ImapMailReceiver imapMailReceiver = new ImapMailReceiver(storeUrl);
            imapMailReceiver.setShouldMarkMessagesAsRead(true);
            imapMailReceiver.setShouldDeleteMessages(false);
            imapMailReceiver.setMaxFetchSize(10);

            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailProperties.put("mail.imap.socketFactory.fallback", false);
            javaMailProperties.put("mail.store.protocol", "imaps");
            javaMailProperties.put("mail.imap.user", userName);
            javaMailProperties.put("mail.imap.host", host);

            javaMailProperties.put("mail.imap.port", port);
            javaMailProperties.put("mail.imap.ssl.enable", true);
            javaMailProperties.put("mail.debug", true);

            imapMailReceiver.setJavaMailProperties(javaMailProperties);
            Authenticator authenticator =
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    };
            imapMailReceiver.setJavaMailAuthenticator(authenticator);

        return imapMailReceiver;

    }
}
