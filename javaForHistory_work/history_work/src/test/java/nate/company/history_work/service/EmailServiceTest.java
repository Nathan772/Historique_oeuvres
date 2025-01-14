package nate.company.history_work.service;

import nate.company.history_work.entity.EmailDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void shouldSendSimpleMailTest(){
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        //when(new SimpleMailMessage()).thenReturn(simpleMail);

        String to = "recipient@gmail.com";
        String subject = "Simple Mail Test";
        String bodyMessage = "Hello World !";

        simpleMail.setTo(to);
        simpleMail.setSubject(subject);
        simpleMail.setText(bodyMessage);

        assertTrue(emailService.sendSimpleMessage(new EmailDetails(to, subject, bodyMessage)));

        verify(javaMailSender).send(simpleMail);
    }

}