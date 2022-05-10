package by.matsuk.shop.util.mail;

import by.matsuk.shop.exception.UtilException;
import jakarta.mail.Transport;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private MimeMessage message;
    private String sendToMail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    /**
     * Instantiates a new Mail sender.
     *
     * @param sendToMail  send to mail
     * @param mailSubject the mail subject
     * @param mailText    the mail text
     * @param properties  the properties
     */
    public MailSender(String sendToMail, String mailSubject, String mailText, Properties properties) {
        this.sendToMail = sendToMail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = properties;
    }

    /**
     * Send mail method.
     */
    public void send() {
        try {
            initMessage();
            Transport.send(message);
            logger.info("Success");
        } catch (AddressException e) {
            logger.error("Invalid address: " + sendToMail + " " + e);
        } catch (UtilException | MessagingException e) {
            logger.error("Error generating or sending message:" + e);
        }
    }

    private void initMessage() throws UtilException {
        Session mailSession = SessionFactory.createSession(properties);
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            message.setSubject(mailSubject);
            message.setText(mailText);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToMail));
        } catch (MessagingException e) {
            throw new UtilException("Message exception in set methods: initMessage()");
        }
    }
}
