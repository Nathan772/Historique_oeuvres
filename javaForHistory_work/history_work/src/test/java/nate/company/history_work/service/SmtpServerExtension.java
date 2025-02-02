package nate.company.history_work.service;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;

@Component
public class SmtpServerExtension implements BeforeAllCallback, AfterAllCallback {

    private int port = 3025;

    private String user = "duke";

    private String password = "springboot";

    private GreenMail smtpServer;

    @Override
    public void beforeAll(ExtensionContext context) {
        smtpServer = new GreenMail(new ServerSetup(port, null, PROTOCOL_SMTP));
        smtpServer.setUser(user, password);
        smtpServer.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        smtpServer.stop();
    }

    public MimeMessage[] getMessages() {
        return smtpServer.getReceivedMessages();
    }
}
