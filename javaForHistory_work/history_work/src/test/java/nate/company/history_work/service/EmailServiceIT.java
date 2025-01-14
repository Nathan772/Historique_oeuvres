package nate.company.history_work.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.validation.constraints.Email;
import nate.company.history_work.entity.EmailDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class EmailServiceIT {
    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private JavaMailSender javaMailSender;

//    @Value("${smtp.port}")
//    private static int port;
    @RegisterExtension
    static SmtpServerExtension smtpServerExtension = new SmtpServerExtension();


//    @BeforeAll
//    public static void setUpSMTPServer(){
//        ServerSetup serverSetup = new ServerSetup(port, null, PROTOCOL_SMTP);
//        smtpServer = new GreenMail(serverSetup);
//        smtpServer.start();
//    }
//
//    @AfterAll
//    public static void shutdownSMTPServer(){
//        smtpServer.stop();
//    }
//
//    @Test
//    public void shouldSendSimpleMail() throws MessagingException, IOException, InterruptedException {
//        var details = new EmailDetails("receiver@test", "Test", "Hello World!");
//
//        emailService.sendSimpleMessage(details);
//
//        assertThat(smtpServer.waitForIncomingEmail(1500, 1)).isTrue();
//
//
//        assertEquals(1, smtpServer.getReceivedMessages().length);
//    }




    @Test
    public void emailShouldBeSent() throws MessagingException, IOException {

        // prepare
        String toAddress = "receiver@test";
        String subject = "sending email from test";
        String body = "the body of our test email";
        // act
       emailService.sendSimpleMessage(new EmailDetails(toAddress, subject, body));

        // expect
        MimeMessage[] receivedMessages = smtpServerExtension.getMessages();
        assertEquals(1, receivedMessages.length);
        System.out.println("SUBJECT IS {" + receivedMessages[0].getSubject() + "}");
        // test other aspects of the message ...
    }
}



