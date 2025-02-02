package nate.company.history_work.service;

import nate.company.history_work.entity.EmailDetails;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class EmailServiceIT {
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @RegisterExtension
    static SmtpServerExtension smtpServerExtension = new SmtpServerExtension();


    @Test
    public void shouldSendMail() throws MessagingException {
        // prepare
        String toAddress = "receiver@test";
        String subject = "sending email from test";
        String body = "the body of our test email";

        // act
        var isMailSent = emailService.sendSimpleMessage(new EmailDetails(toAddress, subject, body));

        // expect
        MimeMessage[] receivedMessages = smtpServerExtension.getMessages();
        assertTrue(isMailSent);
        assertEquals(1, receivedMessages.length);
        assertEquals(subject, receivedMessages[0].getSubject());
    }

    @Test
    public void shouldSendMultipleMails() throws MessagingException {
        // prepare
        int amountOfMails = 10;
        String recipient = "";
        List<EmailDetails> mailsToSend = IntStream.range(0, amountOfMails)
                .mapToObj((index) -> new EmailDetails("receiver@test",
                        "Mail " + index,
                        "You've just received the mail " + index + "."))
                .toList();

        // act
        boolean hasMailsBeenSent = true;
        for(var mailDetails : mailsToSend){
            boolean isMailSent = emailService.sendSimpleMessage(mailDetails);
            hasMailsBeenSent = hasMailsBeenSent && isMailSent;
        }

        // expect
        MimeMessage[] receivedMessages = smtpServerExtension.getMessages();
        assertTrue(hasMailsBeenSent);
        assertEquals(amountOfMails, receivedMessages.length);
        for(int i = 0; i < amountOfMails; i++){
            assertEquals(mailsToSend.get(i).getSubject(), receivedMessages[i].getSubject());
        }
    }
}