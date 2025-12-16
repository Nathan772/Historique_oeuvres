package company.history_work.service;

//import nate.company.history_work.entity.EmailDetails;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import src.main.service.EmailServiceImpl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//
//
///**
// * This class gathers all the unit tests for the Email service. It checks that the {@link EmailServiceImpl} is running
// * accurately.
// *
// * @author Dylan DE JESUS
// * @author Nathan BILINGI
// *
// */
//
//@ExtendWith(MockitoExtension.class)
//public class EmailServiceTest {
//
//    /**
//     * The mock that will be injected into the service implementation.
//     *
//     * @see #emailService
//     */
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    /**
//     * The instance of the mail service after the injection of the mock.
//     */
//    @InjectMocks
//    private EmailServiceImpl emailService;
//
//    /**
//     * Checks that an email can be sent when proper data is given.
//     */
//    @Test
//    public void shouldSendSimpleMailTest(){
//        SimpleMailMessage simpleMail = new SimpleMailMessage();
//
//        String to = "recipient@gmail.com";
//        String subject = "Simple Mail Test";
//        String bodyMessage = "Hello World !";
//
//        simpleMail.setTo(to);
//        simpleMail.setSubject(subject);
//        simpleMail.setText(bodyMessage);
//
//        assertTrue(emailService.sendSimpleMessage(new EmailDetails()));
//        verify(javaMailSender).send(simpleMail);
//    }
//
//    /**
//     * Checks that an exception is thrown if null value is given instead of email data.
//     */
//    @Test
//    public void shouldThrowNullPointerException(){
//        String subject = "Simple Mail Test";
//        String bodyMessage = "Hello World !";
//
//        // The mail can't be sent because of the missing email data
//        var e = assertThrows(NullPointerException.class,
//                () -> emailService.sendSimpleMessage(null));
//        assertEquals("Email details is null", e.getMessage());
//    }
//}