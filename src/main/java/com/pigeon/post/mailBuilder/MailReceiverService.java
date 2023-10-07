package com.pigeon.post.mailBuilder;

import com.pigeon.post.Services.MailMessageService;
import com.pigeon.post.models._MailMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.util.MimeMessageParser;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
@Service
@Slf4j
public class MailReceiverService implements MailReceiverBuilder {
    private static final String DOWNLOAD_FOLDER = "data";
    private static final String DOWNLOAD_MAIL_FOLDER ="DOWNLOADED";
    private final MailMessageService mailMessageService;

    public MailReceiverService(MailMessageService mailMessageService) {
        this.mailMessageService = mailMessageService;
    }

    @Override
    public void handleReceivedMail(MimeMessage receivedMessage) {
        try {
            log.info("Handle Receive Mails......");

            Folder folder = receivedMessage.getFolder();
            folder.open(Folder.READ_WRITE);
            Message[] messages= folder.getMessages();

            fetchMessagesInFolder(folder,messages);
            Arrays.asList(messages).stream().filter(message -> {
                MimeMessage currentMessage = (MimeMessage) message;
                try {
                    return currentMessage.getMessageID().equalsIgnoreCase(receivedMessage.getMessageID());
                } catch (MessagingException e) {
                    log.error("Error occurred during process message", e);
                    return false;
                }
            }).forEach(this::extractMail);
            copyMailToDownloadFolder(receivedMessage,folder);
            parseMimeMessageToMailMessage(receivedMessage);
            System.out.println(receivedMessage.getMessageID()+"sssssssssssss");

            folder.close();
        }catch (Exception ex){
            log.error("Error Handle Mail"+ex);
        }
    }
    private void fetchMessagesInFolder(Folder folder,Message[] messages)throws MessagingException{
        FetchProfile contentsProfile = new FetchProfile();
        contentsProfile.add(FetchProfile.Item.ENVELOPE);
        contentsProfile.add(FetchProfile.Item.CONTENT_INFO);
        contentsProfile.add(FetchProfile.Item.FLAGS);
        contentsProfile.add(FetchProfile.Item.SIZE);
        folder.fetch(messages, contentsProfile);
    }

    private void copyMailToDownloadFolder(MimeMessage mimeMessage,Folder folder) throws MessagingException {
        Store store =folder.getStore();
        Folder downloadedMailFolder = store.getFolder(DOWNLOAD_MAIL_FOLDER);
        if (downloadedMailFolder.exists()) {
            downloadedMailFolder.open(Folder.READ_WRITE);
            downloadedMailFolder.appendMessages(new MimeMessage[]{mimeMessage});
            downloadedMailFolder.close();
        }
    }
    private void showMailContent(MimeMessageParser mimeMessageParser) throws Exception {
        log.debug("From: {} to: {} | Subject: {}", mimeMessageParser.getFrom(), mimeMessageParser.getTo(), mimeMessageParser.getSubject());
        log.debug("Mail content: {}", mimeMessageParser.getPlainContent());
    }
    private void extractMail(Message message) {
        try {
            final MimeMessage messageToExtract = (MimeMessage) message;
            final MimeMessageParser mimeMessageParser = new MimeMessageParser(messageToExtract).parse();

            showMailContent(mimeMessageParser);
            downloadAttachmentFiles(mimeMessageParser);
            // To delete downloaded email
            messageToExtract.setFlag(Flags.Flag.DELETED, true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    private void downloadAttachmentFiles(MimeMessageParser mimeMessageParser){
        log.debug("Email has {} attachment files", mimeMessageParser.getAttachmentList().size());
        mimeMessageParser.getAttachmentList()
                .forEach(
                        dataSource -> {
                            if(StringUtils.isNotBlank(dataSource.getName())){
                                String rootDirectoryPath = new FileSystemResource("").getFile().getAbsolutePath();
                                String dataFolderPath = rootDirectoryPath + File.separator +DOWNLOAD_FOLDER;
                                createDirectoryIfNotExists(dataFolderPath);
                                String downloadAttachmentFilePath = rootDirectoryPath
                                        +File.separator
                                        +DOWNLOAD_FOLDER
                                        +File.separator
                                        +dataSource.getName();
                                File downloadedAttachmentFile = new File(downloadAttachmentFilePath);
                                log.info("Save attachment file to: {}", downloadAttachmentFilePath);
                                try(OutputStream out =new FileOutputStream(downloadedAttachmentFile)){
                                    InputStream in = dataSource.getInputStream();
                                    IOUtils.copy(in,out);
                                }
                                catch (IOException ex){
                                    log.error("failed to save file",ex);
                                }
                            }
                        }
                );
    }
    private void createDirectoryIfNotExists(String directoryPath) {
        if (!Files.exists(Paths.get(directoryPath))) {
            try {
                Files.createDirectories(Paths.get(directoryPath));
            } catch (IOException e) {
                log.error("An error occurred during create folder: {}", directoryPath, e);
            }
        }
    }

    private void parseMimeMessageToMailMessage(MimeMessage mimeMessage){
        try {
            _MailMessage mailMessage= new _MailMessage();
            mailMessage.setMessageId(mimeMessage.getMessageID());
            mailMessage.setMessage(mimeMessage.getDescription());
            mailMessage.setContentType(mimeMessage.getContentType());
            mailMessage.setFrom(Arrays.toString(mimeMessage.getFrom()));
            mailMessage.setSubject(mimeMessage.getSubject());
            this.mailMessageService.createNewMail(mailMessage).block();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
