/**
 * This service will be used by the authentication service.
 *
 * This implementation of the EmailService class
 */

package src.main.java.nate.company.history_work.service;

import nate.company.history_work.entity.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.logging.Level;

import static src.main.java.nate.company.history_work.logger.LoggerInfo.LOGGER;


/**
 * This service is based on the {@link JavaMailSender} implementation.
 *
 * From a content, it creates a mail and send it.
 *
 * @author Dylan DE JESUS
 * @author Nathan BILINGI
 * @see EmailService
 */
@Service
public class EmailServiceImpl implements nate.company.history_work.service.EmailService {

    /**
     * The instance of the spring class that sends mails through
     * Java API
     * @see #sendSimpleMessage(EmailDetails)
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * The username of the mail account that will
     * be used to send mails
     * 
     * @see #sendSimpleMessage(EmailDetails)
     */
    @Value("${HOST_EMAIL_USERNAME}")   // Takes the value inside the application.properties file
    private String sender;

    /**
     * Sends a simple mail. The message is sent from the sender (defined in the configuration) to a remote recipient
     * address with a subject and a simple String body content.
     *
     * @param details the data used to send the mail (recipient, subject, body content).
     * @return true if the mail has been sent successfully, false otherwise.
     */
    @Override
    public boolean sendSimpleMessage(EmailDetails details) {
        Objects.requireNonNull(details, "Email details is null");
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setSubject(details.getSubject());
            mailMessage.setText(details.getBodyContent());
            javaMailSender.send(mailMessage);
            return true;
        }catch(MailException exception){
            LOGGER.log(Level.WARNING, "Could not send following simple mail : "
                        + details + "\n" + exception.getCause());
            return false;
        }
    }
}