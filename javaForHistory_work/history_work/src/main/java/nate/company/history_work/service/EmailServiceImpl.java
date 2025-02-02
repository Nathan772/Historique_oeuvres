package nate.company.history_work.service;

import nate.company.history_work.entity.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

import static nate.company.history_work.logger.LoggerInfo.LOGGER;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public boolean sendSimpleMessage(EmailDetails details) {
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
