package company.history_work.service;

//import jakarta.mail.MessagingException;
//import nate.company.history_work.entity.EmailDetails;
//import org.junit.jupiter.api.Test;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import src.main.service.EmailServiceImpl;
//
//import javax.mail.internet.MimeMessage;
//import java.util.List;
//import java.util.stream.IntStream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
///**
// * <p>Gathers the integration tests for the email service. It checks that the {@link EmailServiceImpl} is running
// * accurately. This class runs an SMTP server to store the mails after their delivery by the mail service.
// * </p>
// * The {@link SmtpServerExtension} starts the server at the beginning of the tests and stops it after all
// * the test methods have been run. It also purges the server of any potentially stored emails after each test
// * method execution, ensuring the tests remain independent.
// *
// * @author Dylan DE JESUS
// * @author Nathan BILINGI
// *
// * @see EmailServiceImpl
// *
// */
//@ExtendWith({SpringExtension.class})
//@SpringBootTest
//public class EmailServiceIT {
//    @Autowired
//    private EmailServiceImpl emailService;
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @RegisterExtension  // Extension suits better for JUnit5 than the RunWith annotation
//    static SmtpServerExtension smtpServerExtension = new SmtpServerExtension();
//
//    /**
//     * Checks that a mail can be successfully sent. It sends it to a SMTP server that where we can check
//     * if the mail has been sent.
//     *
//     * @throws MessagingException if the subject of the message couldn't been retrieved
//     */
//    @Test
//    public void shouldSendMail() throws MessagingException {
//        // prepare
//        String toAddress = "receiver@test";
//        String subject = "sending email from test";
//        String body = "the body of our test email";
//
//        // act
//        var isMailSent = emailService.sendSimpleMessage(new EmailDetails());
//
//        // expect
//        MimeMessage[] receivedMessages = smtpServerExtension.getMessages();
//        assertTrue(isMailSent);
//        assertEquals(1, receivedMessages.length);
//        try {
//            assertEquals(subject, receivedMessages[0].getSubject());
//        } catch (javax.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Checks that the email service can send multiple mails.
//     *
//     * @throws MessagingException if the subject of the messages couldn't been retrieved
//     */
//    @Test
//    public void shouldSendMultipleMails() throws MessagingException {
//        // prepare
//        int amountOfMails = 10;
//        String recipient = "";
//        List<EmailDetails> mailsToSend = IntStream.range(0, amountOfMails)
//                .mapToObj((index) -> new EmailDetails())
//                .toList();
//
//        // act
//        boolean hasMailsBeenSent = true;
//        for(var mailDetails : mailsToSend){
//            boolean isMailSent = emailService.sendSimpleMessage(mailDetails);
//            hasMailsBeenSent = hasMailsBeenSent && isMailSent;
//        }
//
//        // expect
//        javax.mail.internet.MimeMessage[] receivedMessages = smtpServerExtension.getMessages();
//        assertTrue(hasMailsBeenSent);
//        assertEquals(amountOfMails, receivedMessages.length);
//        for(int i = 0; i < amountOfMails; i++){
//            try {
//                assertEquals(mailsToSend.get(i).getSubject(), receivedMessages[i].getSubject());
//            } catch (javax.mail.MessagingException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}